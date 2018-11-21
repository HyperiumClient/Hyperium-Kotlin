package cc.hyperium.utils

import org.kodein.di.TTOf
import org.kodein.di.TypeToken

open class Registry<T : Any> : ArrayList<T>() {

    open operator fun plusAssign(item: T) {
        add(item)
    }

    @Suppress("UNCHECKED_CAST")
    fun <A> getInstanceOfClass(token: TypeToken<A>): A {
        return this.firstOrNull {
            token.isAssignableFrom(TTOf(it))
        } as A
    }

    inline fun <reified A> getInstanceOfClass(): A {
        return this.first { it is A } as A
    }
}

/**
 * Marks this class to be created and managed
 * by the [cc.hyperium.processes.services.RegistryManager].
 */
annotation class PublishedRegistry