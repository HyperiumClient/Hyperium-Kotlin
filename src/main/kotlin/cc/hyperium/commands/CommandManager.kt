package cc.hyperium.commands

import cc.hyperium.commands.api.ICommand
import cc.hyperium.commands.engine.AnnotationCommandLoader
import cc.hyperium.commands.engine.CommandData
import cc.hyperium.commands.engine.CommandParser
import cc.hyperium.service.IService
import cc.hyperium.service.Service
import me.kbrewster.blazeapi.EVENT_BUS
import me.kbrewster.blazeapi.events.ChatSentEvent
import me.kbrewster.eventbus.Subscribe

@Service
object CommandManager : IService {
    private val commandLoaders = listOf(AnnotationCommandLoader)
    private val commands = mutableListOf<CommandData>()

    override fun initialize() {
        EVENT_BUS.register(this)
        loadCommands()
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

    private fun loadCommands() {
        commandLoaders.forEach {
            commands.addAll(it.loadCommands())
        }
    }

    fun getGenericErrorMessage(): String {
        return "&cAn error has occurred while processing that command."
    }
}