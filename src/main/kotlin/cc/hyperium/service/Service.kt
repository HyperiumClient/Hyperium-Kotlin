package cc.hyperium.service

/**
 * Indicates that this Service should be loaded at startup.
 *
 * Anything annotated with this must be an object and extend [IService].
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Service

interface IService {
    fun initialize()
}