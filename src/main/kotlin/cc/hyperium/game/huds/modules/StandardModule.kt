package cc.hyperium.game.huds.modules

import cc.hyperium.game.huds.ColorScheme

open class StandardModule(override val name: String,
                          override val description: String,
                          override var length: Int = 1,
                          override var width: Int = 1) : Module {

    override fun render(scheme: ColorScheme, x: Int, y: Int) {}


}