package cc.hyperium.processes.services

import cc.hyperium.processes.Process
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
 * Provides a base implementation of [IService]
 */
abstract class AbstractService : Process {

    override val childProcesses = mutableListOf<Process>()

    private val LOGGER = LogManager.getLogger()

    /**
     * Dummy method, so we don't force any classes into implementing it
     */
    override fun initialize() {
        super.initialize()
    }

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
        super.initialize()
        this.job = Job()
    }

    override fun kill(): Boolean {
        super.kill()
        this.job.cancel()
        return true
    }
}