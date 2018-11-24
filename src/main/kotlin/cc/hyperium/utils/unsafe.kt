@file:Suppress("TooGenericExceptionCaught")

package cc.hyperium.utils

import java.util.*

inline fun ignoreException(body: () -> Unit) {
    try {
        body.invoke()
    } catch (ignored: Exception) {
    }
}

inline fun printException(body: () -> Unit) {
    try {
        body.invoke()
    } catch (e: Exception) {
        println(e)
    }
}

inline fun exception(body: () -> Unit, action: (Exception) -> Unit) {
    try {
        body.invoke()
    } catch (e: Exception) {
        action.invoke(e)
    }
}

inline fun <A : Any> optional(body: () -> A?): Optional<A> {
    return try {
        Optional.ofNullable(body.invoke())
    } catch (e: Exception) {
        Optional.empty()
    }
}
