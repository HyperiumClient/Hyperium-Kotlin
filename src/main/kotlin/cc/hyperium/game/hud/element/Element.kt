package cc.hyperium.game.hud.element

import cc.hyperium.game.hud.ColorScheme

interface Element {

    val name: String

    val description: String

    val height: Int

    val width: Int

    fun render(scheme: ColorScheme, x: Int, y: Int)
}