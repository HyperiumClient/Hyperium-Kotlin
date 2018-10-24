package cc.hyperium.processes.services.commands

import cc.hyperium.processes.services.AbstractService
import cc.hyperium.processes.services.Service
import cc.hyperium.processes.services.commands.api.ICommand
import cc.hyperium.processes.services.commands.engine.AnnotationCommandLoader
import cc.hyperium.processes.services.commands.engine.CommandData
import cc.hyperium.processes.services.commands.engine.CommandParser
import me.kbrewster.blazeapi.EVENT_BUS
import me.kbrewster.blazeapi.events.ChatSentEvent
import me.kbrewster.eventbus.Subscribe

@Service
object CommandManager : AbstractService() {
    private val commandLoaders = listOf(AnnotationCommandLoader)
    private val commands = mutableListOf<CommandData>()

    override fun initialize() {
        super.initialize()
        loadCommands()
        EVENT_BUS.register(this)
    }

    override fun kill(): Boolean {
        EVENT_BUS.unregister(this)
        return super.kill()
    }

    fun addCommandFromClass(commandClass: ICommand) {
        val execFun = commandClass::execute
        val usageFun = commandClass::getUsage

        commands.add(CommandData(
                commandClass.getName(),
                execFun.parameters,
                execFun,
                usageFun,
                commandClass
        ))
    }

    @Subscribe
    fun onSendChat(event: ChatSentEvent) {
        if (execute(event.message)) event.cancelled = true
    }

    private fun execute(longCommand: String): Boolean {
        val command = if (longCommand[0] == '/') longCommand.substring(1) else longCommand

        if (command.isEmpty()) return false

        val split = command.split(" ")

        val commandData = commands.firstOrNull {
            it.name == split[0]
        } ?: return false

        CommandParser.parseAndCallFunction(split.drop(1), commandData)

        return true
    }

    fun registerCommandClass(instance: Any) {
        commandLoaders.forEach {
            commands.addAll(it.loadCommands(instance))
        }
    }

    private fun loadCommands() {
        commandLoaders.forEach {
            commands.addAll(it.loadCommands())
        }
    }

    fun getGenericErrorMessage(): String {
        return "&cAn error has occurred while processing that command."
    }
}