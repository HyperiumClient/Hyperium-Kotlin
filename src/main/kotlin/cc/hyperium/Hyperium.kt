package cc.hyperium

import cc.hyperium.network.NetworkManager
import cc.hyperium.services.ServiceRegistry
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import me.kbrewster.blazeapi.events.InitializationEvent
import me.kbrewster.blazeapi.events.ShutdownEvent
import me.kbrewster.config.ConfigFactory
import me.kbrewster.eventbus.Subscribe
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.reflections.Reflections
import org.reflections.scanners.MethodAnnotationsScanner
import org.reflections.scanners.SubTypesScanner
import org.reflections.scanners.TypeAnnotationsScanner

object Hyperium {
    val LOGGER: Logger = LogManager.getLogger()

    val REFLECTIONS = Reflections("cc.hyperium", "com.chattriggers.ctjs", MethodAnnotationsScanner(), TypeAnnotationsScanner(), SubTypesScanner())

    val RUNNING_SERVICES = ServiceRegistry()

    val config = ConfigFactory.createFileConfig("config-test.json", "json")

    @Subscribe
    fun onInit(event: InitializationEvent) {
        LOGGER.info("Starting Hyperium....")

        // Start all of the services of the client!

        // Asynchronously start the network connection.
        // We're using coroutines here because kotlin has them and they are cool!
        val networkJob = GlobalScope.launch {
            try {
                NetworkManager.bootstrapClient()
                LOGGER.info("The connection to the Hyperium Server succeeded!")
            } catch (e: Exception) {
                LOGGER.error("The connection to the Hyperium Server could not be completed.")
            }
        }

        // First off, load the config. It will be used by almost everything
        // in the client, so it seems like a good thing to load immediately.
        //TODO: THIS BE BROKE @KEVIN
        //this.config.load()

        // Load all of the services provided by the client.
        // This includes the command system, and other vital
        // client services.
        RUNNING_SERVICES.bootstrap(REFLECTIONS)

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

    @Subscribe
    fun onShutdown(event: ShutdownEvent) {
        LOGGER.info("Shutting down Hyperium...")
        //this.config.save()
    }
}