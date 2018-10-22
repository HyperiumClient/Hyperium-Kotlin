package cc.hyperium.services.utilities

import cc.hyperium.Hyperium
import cc.hyperium.services.AbstractService
import cc.hyperium.services.Service
import me.kbrewster.blazeapi.EVENT_BUS
import kotlin.reflect.full.companionObjectInstance

/**
 * This allows us to register classes to the EventBus on startup
 */
@Service
object RegisterService : AbstractService() {
    override fun initialize() {
        super.initialize()

        Hyperium.REFLECTIONS.getTypesAnnotatedWith(RegisterEvents::class.java).forEach {
            val objectInstance = it.kotlin.objectInstance ?: it.kotlin.companionObjectInstance ?: return@forEach

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