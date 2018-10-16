package cc.hyperium.utils

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

private var cachedSR: ScaledResolution = ScaledResolution(mc)

val scaledRes: ScaledResolution
    get() = cachedSR

val mouseX: Int
    get() = Mouse.getX() * scaledRes.scaledWidth / mc.displayWidth
val mouseY: Int
    get() = scaledRes.scaledHeight - Mouse.getY() * scaledRes.scaledHeight / mc.displayHeight - 1