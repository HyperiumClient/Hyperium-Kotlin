package cc.hyperium.services.mods

import cc.hyperium.services.commands.CommandManager
import me.kbrewster.blazeapi.EVENT_BUS
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

/**
 * Mods, while they seem to be similar to Services, are fundamentally different
 * due to the fact that they actually change the game.
 *
 * Mods must have at least one no-arg constructor so they can be instantiated.
 */
interface Mod {
    fun initialize()

    fun destroy()
}

abstract class AbstractMod : Mod {
    val LOGGER: Logger = LogManager.getLogger()

    override fun initialize() {
        LOGGER.info("Initializing Mod ${this::class.simpleName}")
        EVENT_BUS.register(this)
        CommandManager.regiserCommandClass(this)
    }

    override fun destroy() {
        LOGGER.info("Destroying Mod ${this::class.simpleName}")
    }
}