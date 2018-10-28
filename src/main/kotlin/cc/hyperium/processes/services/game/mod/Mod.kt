package cc.hyperium.processes.services.game.mod

import cc.hyperium.processes.Process
import cc.hyperium.processes.services.commands.CommandManager
import kotlinx.coroutines.Job
import me.kbrewster.blazeapi.EVENT_BUS
import org.apache.logging.log4j.Logger
import org.kodein.di.KodeinAware
import org.kodein.di.direct
import org.kodein.di.generic.instance


abstract class Mod(private val register: Boolean = true) : Process, KodeinAware {
    override lateinit var job: Job

    override val childProcesses: MutableList<Process>
        get() = mutableListOf()


    override fun initialize() {
        kodein.direct.instance<Logger>().info("Initializing Mod ${this::class.simpleName}")

        if (register) EVENT_BUS.register(this)
        kodein.direct.instance<CommandManager>().registerCommandClass(this)

        super.initialize()
    }

    override fun kill(): Boolean {
        kodein.direct.instance<Logger>().info("Destroying Mod ${this::class.simpleName}")

        return super.kill()
    }
}