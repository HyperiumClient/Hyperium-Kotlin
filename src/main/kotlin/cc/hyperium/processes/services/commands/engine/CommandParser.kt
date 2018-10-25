package cc.hyperium.processes.services.commands.engine

import cc.hyperium.processes.services.commands.api.ArgumentQueue
import cc.hyperium.services.commands.api.ArgumentParser
import me.kbrewster.blazeapi.client.thePlayer
import net.minecraft.util.ChatComponentText
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

            data.function.callBy(paramMap)
        } catch (e: Exception) {
            val usage = if (data.usage.parameters.isNotEmpty()) data.usage.call(data.instance) else data.usage.call()

            thePlayer.addChatMessage(
                ChatComponentText(
                    usage.toString()
                )
            )
        }
    }

    private fun parseParameter(param: KParameter, queue: ArgumentQueue, data: CommandData): Any {
        if (param.kind == KParameter.Kind.INSTANCE) {
            return data.instance
        }

        val type = param.type
        val clazz = type.classifier as KClass<*>

        if (clazz == Optional::class) {
            val typeClazz = type.arguments[0].type!!.classifier as KClass<*>

            val parsed = getParsedArgument(param, typeClazz, queue)
            return Optional.ofNullable(parsed)
        }

        return getParsedArgument(param, clazz, queue)!!
    }

    private fun getParsedArgument(param: KParameter, type: KClass<*>, queue: ArgumentQueue): Any? {
        if (!argumentParsers.containsKey(type)) println("NO PARSER FOR TYPE $type")

        return try {
            argumentParsers[type]?.parse(queue, param).also { queue.sync() }
        } catch (e: Exception) {
            queue.undo()
            null
        }
    }
}