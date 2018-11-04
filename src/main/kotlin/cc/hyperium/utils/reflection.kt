package cc.hyperium.utils

import com.sun.beans.finder.ConstructorFinder.findConstructor

@Throws(Exception::class)
fun <T : Any> Class<*>.instance(vararg constructorParams: Any): T {
    return (this.kotlin.objectInstance
        ?: findConstructor(this, *constructorParams.map { it::class.java }.toTypedArray()).newInstance(*constructorParams)
    ) as T
}