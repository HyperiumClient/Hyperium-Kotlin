package cc.hyperium.commands.engine

import cc.hyperium.commands.api.ArgumentParser
import cc.hyperium.commands.api.ArgumentQueue
import cc.hyperium.commands.api.Greedy
import cc.hyperium.commands.api.Take
import kotlin.reflect.KParameter

class IntArgumentParser : ArgumentParser {
    override fun parse(arguments: ArgumentQueue, param: KParameter): Any? {
        return arguments.poll()?.toInt()
    }
}

class BooleanArgumentParser : ArgumentParser {
    override fun parse(arguments: ArgumentQueue, param: KParameter): Any? {
        return arguments.poll()?.toBoolean()
    }
}

class DoubleArgumentParser : ArgumentParser {
    override fun parse(arguments: ArgumentQueue, param: KParameter): Any? {
        return arguments.poll()?.toDouble()
    }
}

class StringArgumentParser : ArgumentParser {
    override fun parse(arguments: ArgumentQueue, param: KParameter): Any? {
        if (param.annotations.isEmpty()) {
            return arguments.poll()
        }

        val greedy = param.annotations.firstOrNull { it.annotationClass == Greedy::class } as? Greedy
        val take = param.annotations.firstOrNull { it.annotationClass == Take::class } as? Take

        val sb = StringBuilder()

        if (greedy != null) {
            sb.append(arguments.poll())

            while (arguments.peek() != null) {
                sb.append(" ${arguments.poll()}")
            }
        } else if (take != null) {
            var i = 0
            sb.append(arguments.poll())

            while (arguments.peek() != null && i < take.number) {
                sb.append(" ${arguments.poll()}")

                i++
            }

            if (!take.allowLess && i < take.number - 1) {
                throw IllegalArgumentException("Needed ${take.number} words!")
            }
        }

        return sb.toString()
    }
}