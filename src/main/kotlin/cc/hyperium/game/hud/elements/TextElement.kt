package cc.hyperium.game.hud.elements

import cc.hyperium.game.hud.ColorScheme
import me.kbrewster.blazeapi.client.fontRenderer

class TextElement(name: String, description: String, width: Int, length: Int = 0) :
        AbstractElement(name, description, length, width) {

    var shadow: Boolean = false

    override fun render(scheme: ColorScheme, x: Int, y: Int) {
        fontRenderer.drawString(
                TODO("Text formatting needs to be implemented!"),
                x.toFloat(),
                y.toFloat(),
                0xFFFFFF,
                shadow
        )
    }

}