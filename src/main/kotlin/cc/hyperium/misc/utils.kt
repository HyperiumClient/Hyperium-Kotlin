package cc.hyperium.misc

import cc.hyperium.services.AbstractService
import me.kbrewster.blazeapi.client.mc
import me.kbrewster.blazeapi.events.RenderEvent
import me.kbrewster.eventbus.Subscribe
import net.minecraft.client.gui.ScaledResolution
import org.lwjgl.input.Mouse

object UtilService : AbstractService() {
    @Subscribe
    fun render(event: RenderEvent) {
        cachedSR = ScaledResolution(mc)
    }
}

/**
 * TODO: move out into a more sensible place
 */

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

private var cachedSR: ScaledResolution = ScaledResolution(mc)

val scaledRes: ScaledResolution
    get() = cachedSR

val mouseX: Int
    get() = Mouse.getX() * scaledRes.scaledWidth / mc.displayWidth
val mouseY: Int
    get() = scaledRes.scaledHeight - Mouse.getY() * scaledRes.scaledHeight / mc.displayHeight - 1