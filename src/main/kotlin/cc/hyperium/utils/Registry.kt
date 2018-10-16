package cc.hyperium.utils

open class Registry<T> : ArrayList<T>() {

    fun register(element: T): Boolean {
        return this.add(element)
    }

    fun register(index: Int, element: T) {
        this.add(index, element)
    }

    fun unregister(obj: T) {
        this.remove(obj)
    }

    fun getRegistry(): List<T> {
        return this
    }

}