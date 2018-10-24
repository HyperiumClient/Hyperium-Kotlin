package cc.hyperium.processes.services.game.mod

import cc.hyperium.processes.Process
import cc.hyperium.processes.services.commands.CommandManager
import me.kbrewster.blazeapi.EVENT_BUS
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger


open class Mod(val register: Boolean = true) : Process {

    private val LOGGER: Logger = LogManager.getLogger()

    override val childProcesses: MutableList<Process>
        get() = mutableListOf()


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