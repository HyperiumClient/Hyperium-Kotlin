package cc.hyperium.game.hud.elements

import cc.hyperium.game.hud.ColorScheme


interface Element {

    val name: String

    val description: String

    var length: Int

    var width: Int

    fun render(scheme: ColorScheme, x: Int, y: Int)

}