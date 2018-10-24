package cc.hyperium.processes.services

import cc.hyperium.processes.Process
import kotlinx.coroutines.Job
import org.apache.logging.log4j.LogManager

/**
 * Indicates that this Service should be loaded at startup.
 *
 * Anything annotated with this must be an object and implement [AbstractService].
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Service

/**
 * Provides a base Service
 */
abstract class AbstractService : Process {
    override lateinit var job: Job

    override val childProcesses = mutableListOf<Process>()

    private val LOGGER = LogManager.getLogger()
}