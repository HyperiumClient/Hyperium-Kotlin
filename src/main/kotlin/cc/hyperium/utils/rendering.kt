package cc.hyperium.utils

import cc.hyperium.processes.services.utilities.RegisterEvents
import me.kbrewster.blazeapi.client.mc
import me.kbrewster.blazeapi.events.ClientTickEvent
import me.kbrewster.eventbus.Subscribe
import net.minecraft.client.gui.ScaledResolution
import org.lwjgl.input.Mouse

@RegisterEvents
class Rendering {
    @Subscribe
    fun onTick(event: ClientTickEvent) {
        scaledResolution = ScaledResolution(mc)
    }
}

var scaledResolution = ScaledResolution(mc)

val mouseX: Int
    get() = Mouse.getX() * scaledResolution.scaledWidth / mc.displayWidth

val mouseY: Int
    get() = scaledResolution.scaledHeight - Mouse.getY() * scaledResolution.scaledHeight / mc.displayHeight - 1
