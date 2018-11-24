package cc.hyperium.game.hud.element.elements

import cc.hyperium.game.hud.element.TextElement
import net.minecraft.client.Minecraft

object FPSElement : TextElement("FPS", "Displays Frames per second") {

    override val value: Any
        get() = Minecraft.getDebugFPS()
}
