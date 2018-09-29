package cc.hyperium.services

import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Dispatchers
import me.kbrewster.blazeapi.EVENT_BUS
import kotlin.coroutines.experimental.CoroutineContext

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
}

/**
 * Provides a base implementation of [IService] as well as [CoroutineScope], as well as registers
 * the services on the event bus.
 *
 * By extending this class you get access to kotlin's coroutines.
 */
abstract class AbstractService : IService, CoroutineScope {
    override var coroutineContext: CoroutineContext = Dispatchers.Default

    override fun initialize() {
        EVENT_BUS.register(this)
    }
}