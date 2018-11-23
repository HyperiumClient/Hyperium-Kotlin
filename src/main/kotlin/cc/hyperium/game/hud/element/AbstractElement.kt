package cc.hyperium.game.hud.element

abstract class AbstractElement(
    override val name: String,
    override val description: String,
    override val height: Int = 0,
    override val width: Int = 0
) : Element