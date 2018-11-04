package cc.hyperium.utils

import org.kodein.di.TTOf
import org.kodein.di.TypeToken

open class Registry<T : Any> : ArrayList<T>() {

    fun register(element: T): Boolean {
        return this.add(element)
    }

    fun register(index: Int, element: T) {
        this.add(index, element)
    }

    fun unregister(obj: T) {
        this.remove(obj)
    }

    @Suppress("UNCHECKED_CAST")
    fun <A> getInstanceOfClass(token: TypeToken<A>): A {
        return this.firstOrNull {
            token.isAssignableFrom(TTOf(it))
        } as A
    }
}