package cc.hyperium.processes.services.commands.engine

import cc.hyperium.processes.services.commands.api.*
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

        val sb = StringBuilder()
        val wordOne = arguments.poll() ?: return null

        return when (val ann = param.annotations.first()) {
            is Greedy -> greedy(sb, wordOne, arguments)
            is Take -> take(sb, wordOne, arguments, ann)
            is Quotable -> quotable(sb, wordOne, arguments)
            else -> throw UnsupportedOperationException("Unknown annotation $ann")
        }
    }

    private fun greedy(sb: StringBuilder, wordOne: String, arguments: ArgumentQueue): String {
        sb.append(wordOne)

        while (arguments.peek() != null) {
            sb.append(" ${arguments.poll()}")
        }

        return sb.toString()
    }

    private fun take(sb: StringBuilder, wordOne: String, arguments: ArgumentQueue, take: Take): String {
        sb.append(wordOne)

        var i = 0

        while (arguments.peek() != null && i < take.number) {
            sb.append(" ${arguments.poll()}")

            i++
        }

        if (!take.allowLess && i < take.number - 1) {
            throw IllegalArgumentException("Needed ${take.number} words!")
        }

        return sb.toString()
    }

    private fun quotable(sb: StringBuilder, wordOne: String, arguments: ArgumentQueue): String {
        if (!wordOne.startsWith("\"")) {
            sb.append(wordOne)
            return sb.toString()
        }

        sb.append(wordOne.substring(1))

        while (arguments.peek() != null) {
            val word = arguments.poll()!!

            if (word.endsWith("\"")) {
                sb.append(" ${word.substring(0, word.length - 1)}")
                break
            }

            sb.append(" $word")
        }

        return sb.toString()
    }
}