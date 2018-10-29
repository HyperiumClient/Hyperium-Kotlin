package cc.hyperium.game.huds

import cc.hyperium.game.huds.modules.Module
import cc.hyperium.utils.Registry

class HUD(private val name: String,
          private val x: Int, private val y: Int,
          private val scheme: ColorScheme) : Registry<Module>() {

    fun render() {
        this.forEach { module -> module.render(scheme, x, y) }
    }

}
