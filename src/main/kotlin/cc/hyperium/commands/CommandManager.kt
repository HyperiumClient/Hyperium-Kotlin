package cc.hyperium.commands

import cc.hyperium.Hyperium
import cc.hyperium.commands.api.ICommand
import cc.hyperium.commands.engine.AnnotationCommandLoader
import cc.hyperium.commands.engine.CommandData
import cc.hyperium.commands.engine.CommandParser
import cc.hyperium.events.SendChatEvent
import com.google.common.eventbus.Subscribe

object CommandManager {
    private val commandLoaders = listOf(AnnotationCommandLoader)

    private val commands = mutableListOf<CommandData>()

    init {
        loadCommands()
        Hyperium.EVENT_BUS.register(this)
        println("REGISTERED!")
    }

    @Subscribe
    fun onSendChat(event: SendChatEvent) {
        println("said ${event.message}")
        execute(event.message)
    }

    fun execute(longCommand: String) {
        val command = if (longCommand[0] == '/') longCommand.substring(1) else longCommand

        if (command.isEmpty()) return

        val split = command.split(" ")

        val commandData = commands.firstOrNull {
            it.name == split[0]
        } ?: return

        CommandParser.parseAndCallFunction(split.drop(1), commandData)
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

    fun loadCommands() {
        commandLoaders.forEach {
            commands.addAll(it.loadCommands())
        }
    }

    fun getGenericErrorMessage(): String {
        return "&cAn error has occurred while processing that command."
    }
}