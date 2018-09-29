package cc.hyperium.commands.engine

import cc.hyperium.Hyperium
import cc.hyperium.commands.CommandManager
import cc.hyperium.commands.api.Command
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.full.companionObjectInstance
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberFunctions
import kotlin.reflect.jvm.javaMethod
import kotlin.reflect.jvm.kotlinFunction

val ref = Hyperium.REFLECTIONS

interface CommandLoader {
    fun loadCommands(): List<CommandData>
}

object AnnotationCommandLoader : CommandLoader {
    override fun loadCommands(): List<CommandData> {
        return ref.getMethodsAnnotatedWith(Command::class.java).asSequence().map {
            it.kotlinFunction!!
        }.map(::mapToData).filterNotNull().toList()
    }

    private fun mapToData(it: KFunction<*>): CommandData? {
        val kotlinClass = it.javaMethod!!.declaringClass.kotlin
        val inst = kotlinClass.objectInstance ?: kotlinClass.companionObjectInstance ?: return null
        val cmd = it.findAnnotation<Command>()!!
        val usage = inst::class.memberFunctions.firstOrNull { inner ->
            inner.name == cmd.usage
        } ?: CommandManager::getGenericErrorMessage

        return CommandData(
                cmd.name,
                it.parameters,
                it,
                usage,
                inst
        )
    }
}