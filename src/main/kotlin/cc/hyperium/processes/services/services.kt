package cc.hyperium.processes.services

import cc.hyperium.processes.Process
import kotlinx.coroutines.Job
import org.kodein.di.KodeinAware

/**
 * Indicates that this Service should be loaded at startup.
 *
 * Anything annotated with this must be an object and implement [AbstractService].
 * It also must accept a kodein instance.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Service(val priority: Priority = Priority.NORMAL)

/**
 * Represents the priority of a Service
 *
 * Services with higher priorities are loaded first.
 */
enum class Priority {
    LOWEST,
    LOW,
    NORMAL,
    HIGH,
    HIGHEST
}

/**
 * Provides a base Service
 */
abstract class AbstractService : Process, KodeinAware {
    override lateinit var job: Job

    override val childProcesses = mutableListOf<Process>()
}