package cc.hyperium.commands.engine

import cc.hyperium.commands.api.ArgumentParser
import cc.hyperium.commands.api.ArgumentQueue
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.KParameter

object CommandParser {
    private val argumentParsers = mutableMapOf<KClass<*>, ArgumentParser>()

    init {
        registerArgumentParser(Int::class, IntArgumentParser())
        registerArgumentParser(Boolean::class, BooleanArgumentParser())
        registerArgumentParser(Double::class, DoubleArgumentParser())
        registerArgumentParser(String::class, StringArgumentParser())
    }

    fun registerArgumentParser(type: KClass<*>, parser: ArgumentParser) {
        argumentParsers[type] = parser
    }

    fun parseAndCallFunction(command: List<String>, data: CommandData) {
        val queue = ArgumentQueue(LinkedList<String>(command))
        val paramMap = mutableMapOf<KParameter, Any>()

        try {
            data.parameters.forEach {
                paramMap[it] = parseParameter(it, queue, data)
            }
        } catch (e: Exception) {
            //TODO: CHAT THIS
            println(data.usage.call(data.instance))
        }

        data.function.callBy(paramMap)
    }

    private fun parseParameter(param: KParameter, queue: ArgumentQueue, data: CommandData): Any {
        if (param.kind == KParameter.Kind.INSTANCE) {
            return data.instance
        }

        val type = param.type
        val clazz = type.classifier as KClass<*>

        if (clazz.simpleName == "Optional") {
            val typeClazz = clazz.typeParameters[0] as KClass<*>

            val parsed = getParsedArgument(param, typeClazz, queue)
            return Optional.ofNullable(parsed)
        }

        return getParsedArgument(param, clazz, queue)!!
    }

    private fun getParsedArgument(param: KParameter, type: KClass<*>, queue: ArgumentQueue): Any? {
        return try {
            argumentParsers[type]?.parse(queue, param).also { queue.sync() }
        } catch (e: Exception) {
            queue.undo()
            null
        }
    }
}