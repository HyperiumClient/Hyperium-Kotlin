package cc.hyperium.game.huds.modules

import cc.hyperium.game.huds.ColorScheme
import me.kbrewster.blazeapi.client.fontRenderer

class TextModule(name: String, description: String, width: Int, length: Int = 0) :
        StandardModule(name, description, length, width) {

    var shadow: Boolean = false

    override fun render(scheme: ColorScheme, x: Int, y: Int) {
        if (shadow) {
            fontRenderer.drawStringWithShadow(
                    TODO("Text formatting needs to be implemented!"),
                    x.toFloat(),
                    y.toFloat(),
                    0xFFFFFF
            )
        } else {
            fontRenderer.drawString(
                    TODO("Text formatting needs to be implemented!"),
                    x,
                    y,
                    0xFFFFFF
            )
        }
    }
}