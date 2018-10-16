package cc.hyperium.utils

fun clamp(number: Float, min: Float, max: Float): Float {
    return if (number < min) min else if (number > max) max else number
}

fun easeOut(current: Float, goal: Float, jump: Float, speed: Float): Float {
    return if (Math.floor((Math.abs(goal - current) / jump).toDouble()) > 0)
        current + (goal - current) / speed
    else
        goal
}

fun map(x: Float, in_min: Float, in_max: Float, out_min: Float, out_max: Float): Float {
    return clamp((x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min, out_min, out_max)
}