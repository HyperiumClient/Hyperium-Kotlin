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

fun Float.map(in_min: Float, in_max: Float, out_min: Float, out_max: Float): Float {
    val num = (this - in_min) * (out_max - out_min) / (in_max - in_min) + out_min
    return num.clamp(out_min, out_max)
}