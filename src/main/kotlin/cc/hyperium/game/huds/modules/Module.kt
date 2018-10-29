package cc.hyperium.game.huds.modules

import cc.hyperium.game.huds.ColorScheme


interface Module {

    val name: String

    val description: String

    var length: Int

    var width: Int

    fun render(scheme: ColorScheme, x: Int, y: Int)

}