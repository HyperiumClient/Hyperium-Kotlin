package cc.hyperium.game.hud

import cc.hyperium.game.hud.element.Element
import cc.hyperium.utils.Registry

class HUD(
    private val name: String,
    private val x: Int,
    private val y: Int,
    private val scheme: ColorScheme
) : Registry<Element>() {

    fun render() {
        this.forEach { module -> module.render(scheme, x, y) }
    }
}
