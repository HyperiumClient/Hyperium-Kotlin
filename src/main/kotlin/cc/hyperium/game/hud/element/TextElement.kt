package cc.hyperium.game.hud.element

import cc.hyperium.game.hud.ColorScheme
import cc.hyperium.game.hud.element.format.Prefix
import me.kbrewster.blazeapi.client.fontRenderer

abstract class TextElement(name: String, description: String) :
        AbstractElement(name, description) {

    abstract val value: Any

    override val height: Int
        get() = fontRenderer.FONT_HEIGHT

    override val width: Int
        get() = fontRenderer.getStringWidth(value.toString())

    var prefix: Prefix = Prefix.BRACKETS

    var shadow: Boolean = false


    override fun render(scheme: ColorScheme, x: Int, y: Int) {
        fontRenderer.drawString(
                prefix.format(name, value, scheme),
                x.toFloat(),
                y.toFloat(),
                0xFFFFFF,
                shadow
        )
    }


}