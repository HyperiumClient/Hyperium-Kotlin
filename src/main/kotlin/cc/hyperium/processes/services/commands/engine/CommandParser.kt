package cc.hyperium.processes.services.commands.engine

import cc.hyperium.processes.services.commands.api.ArgumentParser
import cc.hyperium.processes.services.commands.api.ArgumentQueue
import cc.hyperium.utils.Failure
import cc.hyperium.utils.Success
import cc.hyperium.utils.Try
import me.kbrewster.blazeapi.client.mc
import net.minecraft.util.ChatComponentText
import org.apache.logging.log4j.LogManager
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter

object CommandParser {
    private val argumentParsers = mutableMapOf<KClass<*>, ArgumentParser>()
    private val LOGGER = LogManager.getLogger()

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

        val paramMap = data.parameters.associateWith map@{
            val parsed = parseParameter(it, queue, data)

            return@map parsed.resolve {
                fail(data.usage, data.instance)
                return
            }
        }

        data.function.callBy(paramMap)
    }

    private fun fail(usageFunction: KFunction<*>, instance: Any) {
        val usage = if (usageFunction.parameters.isNotEmpty()) usageFunction.call(instance) else usageFunction.call()

        mc.thePlayer.addChatMessage(
            ChatComponentText(
                usage.toString()
            )
        )
    }

    private fun parseParameter(param: KParameter, queue: ArgumentQueue, data: CommandData): Try<Any> {
        if (param.kind == KParameter.Kind.INSTANCE) {
            return Success(data.instance)
        }

        val type = param.type
        var clazz = type.classifier as KClass<*>
        val isOptional = clazz == Optional::class

        if (isOptional) {
            clazz = type.arguments[0].type!!.classifier as KClass<*>
        }

        val result = getParsedArgument(param, clazz, queue)

        if (isOptional) {
            return result.force(Optional.empty()) { Optional.of(it) }
        }

        return result
    }

    private fun getParsedArgument(param: KParameter, type: KClass<*>, queue: ArgumentQueue): Try<Any> {
        val parser = argumentParsers[type]

        if (parser == null) {
            LOGGER.info("No parser found for type $type.")
            return Failure
        }

        val result = Try {
            parser.parse(queue, param)
        }

        when (result) {
            is Success -> queue.sync()
            is Failure -> queue.undo()
        }

        return result
    }
}