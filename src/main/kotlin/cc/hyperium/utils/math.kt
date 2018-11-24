package cc.hyperium.utils

fun Float.clamp(min: Float, max: Float): Float {
    return if (this < min) min else if (this > max) max else this
}

fun Float.easeOut(goal: Float, jump: Float, speed: Float): Float {
    return if (Math.floor((Math.abs(goal - this) / jump).toDouble()) > 0)
        this + (goal - this) / speed
    else
        goal
}

fun Float.map(inMin: Float, inMax: Float, outMin: Float, outMax: Float): Float {
    val num = (this - inMin) * (outMax - outMin) / (inMax - inMin) + outMin
    return num.clamp(outMin, outMax)
}
