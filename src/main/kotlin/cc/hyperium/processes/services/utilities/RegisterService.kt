package cc.hyperium.processes.services.utilities

import cc.hyperium.processes.services.AbstractService
import cc.hyperium.processes.services.Service
import me.kbrewster.blazeapi.EVENT_BUS
import org.kodein.di.Kodein
import org.kodein.di.generic.instance
import org.reflections.Reflections

/**
 * This allows us to register classes to the EventBus on startup
 */
@Service
class RegisterService(override val kodein: Kodein) : AbstractService() {
    private val reflections: Reflections by kodein.instance()

    override fun initialize() {
        super.initialize()

        reflections
            .getTypesAnnotatedWith(RegisterEvents::class.java)
            .forEach {
                val objectInstance = it.kotlin.objectInstance ?: it.newInstance() ?: return@forEach

                EVENT_BUS.register(objectInstance)
            }
    }
}

/**
 * Registers an object on the BlazeAPI Event Bus
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class RegisterEvents