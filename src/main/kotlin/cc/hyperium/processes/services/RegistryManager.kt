package cc.hyperium.processes.services

import cc.hyperium.game.server.ServerRegistry
import cc.hyperium.utils.PublishedRegistry
import cc.hyperium.utils.Registry
import me.kbrewster.blazeapi.EVENT_BUS
import org.kodein.di.Kodein
import org.kodein.di.direct
import org.kodein.di.generic.instance
import org.reflections.Reflections

@Service
class RegistryManager(override val kodein: Kodein) : AbstractService() {
    private val ref: Reflections by kodein.instance()

    val registries = mutableListOf<Registry<*>>()

    override fun initialize() {
        super.initialize()

        ref.getTypesAnnotatedWith(PublishedRegistry::class.java)
            .forEach {
                val objectInstance = it.kotlin.objectInstance ?: it.newInstance() ?: return@forEach

                EVENT_BUS.register(objectInstance)
                registries.add(objectInstance as Registry<*>)
            }


        // Now for all of the registries that need special attention,
        // we'll bootstrap them here.
        val dk = kodein.direct

        getRegistry<ServerRegistry>()
            .bootstrap(dk.instance())
    }

    override fun kill(): Boolean {
        registries.clear()

        return super.kill()
    }

    inline fun <reified T> getRegistry(): T {
        return registries.first { it is T } as T
    }
}