package cc.hyperium.services

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import me.kbrewster.blazeapi.EVENT_BUS
import org.apache.logging.log4j.LogManager
import kotlin.coroutines.CoroutineContext

/**
 * Indicates that this Service should be loaded at startup.
 *
 * Anything annotated with this must be an object and implement [IService].
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Service

interface IService {
    /**
     * Called when this function is booting up.
     * Here you should bootstrap everything being used
     * by this service.
     */
    fun initialize()

    /**
     * This function is called at the end of this service's lifecycle.
     * This is where you should close anything being used by this service,
     * and prepare for shutdown.
     *
     * If this function returns true, then the service has been destroyed correctly,
     * and will be removed. If it returns false, then the service has failed to destroy,
     * and will not be removed.
     *
     * Keep in mind that this is not necessarily the end of the game.
     * Services should be able to shutdown and reboot throughout
     * the duration of the game without any issues.
     */
    fun destroy(): Boolean
}

/**
 * Provides a base implementation of [IService] and [CoroutineScope],
 * and also registers the service on the event bus.
 *
 * By extending this class you get access to kotlin's coroutines.
 */
abstract class AbstractService : IService, CoroutineScope {
    lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + job

    val LOGGER = LogManager.getLogger()

    override fun initialize() {
        this.job = Job()
        EVENT_BUS.register(this)
    }

    override fun destroy(): Boolean {
        this.job.cancel()
        return true
    }
}