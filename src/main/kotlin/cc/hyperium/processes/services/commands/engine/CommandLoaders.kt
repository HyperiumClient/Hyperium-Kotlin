package cc.hyperium.processes.services.commands.engine

import cc.hyperium.Hyperium
import cc.hyperium.processes.services.commands.CommandManager
import cc.hyperium.processes.services.commands.api.Command
import org.kodein.di.generic.instance
import org.reflections.Reflections
import kotlin.reflect.KFunction
import kotlin.reflect.full.companionObjectInstance
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberFunctions
import kotlin.reflect.jvm.javaMethod
import kotlin.reflect.jvm.kotlinFunction

interface CommandLoader {
    fun loadCommands(): List<CommandData>

    fun loadCommands(instance: Any): List<CommandData>
}

object AnnotationCommandLoader : CommandLoader {
    private val ref: Reflections = Hyperium.dkodein.instance()

    override fun loadCommands(): List<CommandData> {
        return ref.getMethodsAnnotatedWith(Command::class.java).asSequence().map {
            it.kotlinFunction!!
        }.map { mapToData(it) }.filterNotNull().toList()
    }

    override fun loadCommands(instance: Any): List<CommandData> {
        return instance::class.memberFunctions
            .filter { it.findAnnotation<Command>() != null }
            .map { mapToData(it, instance)!! }
    }

    private fun mapToData(func: KFunction<*>, instance: Any? = null): CommandData? {
        val kotlinClass = func.javaMethod!!.declaringClass.kotlin
        val inst = instance ?: kotlinClass.objectInstance ?: kotlinClass.companionObjectInstance ?: return null
        val cmd = func.findAnnotation<Command>()!!
        val usage = inst::class.memberFunctions.firstOrNull { inner ->
            inner.name == cmd.usage
        } ?: CommandManager::getGenericErrorMessage

        return CommandData(
            cmd.name,
            func.parameters,
            func,
            usage,
            inst
        )
    }
}
