package cc.hyperium.services

import cc.hyperium.Hyperium
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Dispatchers
import me.kbrewster.blazeapi.EVENT_BUS
import net.minecraft.client.resources.I18n
import org.apache.logging.log4j.LogManager
import org.reflections.Reflections
import kotlin.coroutines.experimental.CoroutineContext

fun bootstrapServices(ref: Reflections) {
    fun onError(serviceName: String): IService? {
        Hyperium.LOGGER.error(I18n.format("error.loading.service", serviceName))
        return null
    }

    Hyperium.RUNNING_SERVICES += ref.getTypesAnnotatedWith(Service::class.java)
            .asSequence()
            .map { it.kotlin.objectInstance as? IService ?: onError(it.name) }
            .filterNotNull()
            .toList()
}

/**
 * Indicates that this Service should be loaded at startup.
 *
 * Anything annotated with this must be an object and implement [IService].
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Service

interface IService {
    fun initialize()

    fun destroy(): Boolean
}

/**
 * Provides a base implementation of [IService] as well as [CoroutineScope], as well as registers
 * the services on the event bus.
 *
 * By extending this class you get access to kotlin's coroutines.
 */
abstract class AbstractService : IService, CoroutineScope {

    override var coroutineContext: CoroutineContext = Dispatchers.Default

    val LOGGER = LogManager.getLogger()

    override fun initialize() {
        EVENT_BUS.register(this)
    }
}