package cc.hyperium.services

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
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

/**
 * Basic Service Interface
 *
 * We can think of a service as a task on
 * the windows command manager, we can kill services
 * and if a service gets killed, all the subservices get
 * killed along with it.
 */
interface IService {

    /**
     * List of all the sub services
     */
    val subServices: MutableList<IService>

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
     * and will be removed. If it returns false, then the service has failed to kill,
     * and will not be removed.
     *
     * Keep in mind that this is not necessarily the end of the game.
     * Services should be able to shutdown and reboot throughout
     * the duration of the game without any issues.
     */
    fun kill(): Boolean {
        this.subServices.forEach { service -> service.kill() }
        return true
    }
}

/**
 * Provides a base implementation of [IService]
 */
abstract class AbstractService : IService {

    override val subServices = mutableListOf<IService>()

    private val LOGGER = LogManager.getLogger()

    /**
     * Dummy method, so we don't force any classes into implementing it
     */
    override fun initialize() {}

    /**
     * Dummy method, so we don't force any classes into implementing it
     */
    override fun kill(): Boolean {
        super.kill()
        return true
    }
}

/**
 * Provides implementation of a service and
 * allows for [CoroutineScope] and async possibilities
 */
abstract class AsyncService : AbstractService(), CoroutineScope {

    lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + job


    override fun initialize() {
        this.job = Job()
    }

    override fun kill(): Boolean {
        this.job.cancel()
        return true
    }
}