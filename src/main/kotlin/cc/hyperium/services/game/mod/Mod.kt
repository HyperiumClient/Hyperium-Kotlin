package cc.hyperium.services.game.mod

import cc.hyperium.services.AbstractService
import cc.hyperium.services.commands.CommandManager
import me.kbrewster.blazeapi.EVENT_BUS
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger


open class Mod(val register: Boolean = true) : AbstractService() {

    private val LOGGER: Logger = LogManager.getLogger()

    override fun initialize() {
        LOGGER.info("Initializing Mod ${this::class.simpleName}")
        if (register) EVENT_BUS.register(this)
        CommandManager.registerCommandClass(this)
    }

    override fun kill(): Boolean {
        LOGGER.info("Destroying Mod ${this::class.simpleName}")
        return true
    }
}