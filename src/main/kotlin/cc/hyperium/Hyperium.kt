package cc.hyperium

import cc.hyperium.network.NetworkManager
import cc.hyperium.processes.services.AbstractService
import cc.hyperium.processes.services.ServiceRegistry
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import me.kbrewster.blazeapi.events.InitializationEvent
import me.kbrewster.blazeapi.events.ShutdownEvent
import me.kbrewster.config.Config
import me.kbrewster.config.ConfigFactory
import me.kbrewster.eventbus.Subscribe
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.kodein.di.Kodein
import org.kodein.di.bindings.subTypes
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import org.kodein.di.generic.with
import org.reflections.Reflections
import org.reflections.scanners.MethodAnnotationsScanner
import org.reflections.scanners.SubTypesScanner
import org.reflections.scanners.TypeAnnotationsScanner

object Hyperium {
    private val reflections = Reflections(
        "cc.hyperium",
        "com.chattriggers.ctjs",
        MethodAnnotationsScanner(),
        TypeAnnotationsScanner(),
        SubTypesScanner()
    )
    private val logger: Logger = LogManager.getLogger()
    private val runningServices = ServiceRegistry()
    private val config = ConfigFactory.createFileConfig("config-test.json", "json")
    private lateinit var network: NetworkManager

    lateinit var kodein: Kodein
    val dkodein get() = kodein.direct

    @Subscribe
    fun onInit(event: InitializationEvent) {
        logger.info("Starting Hyperium....")

        // Time to start the client!


        // Asynchronously start the network connection.
        // We're using coroutines here because kotlin has them and they are cool!
        val networkJob = GlobalScope.launch {
            try {
                network = NetworkManager()
                network.bootstrapClient()
                logger.info("The connection to the Hyperium Server succeeded!")
            } catch (e: Exception) {
                logger.error("The connection to the Hyperium Server could not be completed.")
            }
        }

        // Set up our Kodein instance
        // This provides all of the dependencies for injection
        kodein = constructKodein()

        // Load all of the services provided by the client.
        // This includes the command system, and other vital
        // client services.
        runningServices.bootstrap(reflections, kodein)

        // However, by the time we are starting the client, we want to be registered.
        // To confirm that this has happened, we will join the network job thread,
        // Which will block until the operation is complete, or if it already has,
        // it will return immediately. We do need to wrap the call to join in another
        // coroutine scope however, as the join method is marked with 'suspend.'
        // The runBlocking call simply merges the divide between blocking code and coroutines.
        runBlocking {
            networkJob.join()
        }
    }

    private fun constructKodein() = Kodein {
        bind<AbstractService>().subTypes() with { type ->
            provider { runningServices.getInstanceOfClass(type) }
        }

        bind<Logger>() with singleton { logger }

        bind<Reflections>() with singleton { reflections }

        bind<Config>() with singleton { config }

        bind<NetworkManager>() with singleton { network }
    }

    @Subscribe
    fun onShutdown(event: ShutdownEvent) {
        logger.info("Shutting down Hyperium...")
        this.config.save()
    }
}