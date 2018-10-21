package cc.hyperium.utils

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