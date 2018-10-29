package cc.hyperium.game.hud.elements

import cc.hyperium.game.hud.ColorScheme

abstract class AbstractElement(override val name: String,
                               override val description: String,
                               override var length: Int = 1,
                               override var width: Int = 1) : Element {

    override fun render(scheme: ColorScheme, x: Int, y: Int) {}


}