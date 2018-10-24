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

        val greedy = param.annotations.firstOrNull { it.annotationClass == Greedy::class } as? Greedy
        val take = param.annotations.firstOrNull { it.annotationClass == Take::class } as? Take
        val quotable = param.annotations.firstOrNull { it.annotationClass == Quotable::class } as? Quotable

        val sb = StringBuilder()
        val wordOne = arguments.poll() ?: return null

        if (greedy != null) {
            sb.append(wordOne)

            while (arguments.peek() != null) {
                sb.append(" ${arguments.poll()}")
            }
        } else if (take != null) {
            var i = 0
            sb.append(wordOne)

            while (arguments.peek() != null && i < take.number) {
                sb.append(" ${arguments.poll()}")

                i++
            }

            if (!take.allowLess && i < take.number - 1) {
                throw IllegalArgumentException("Needed ${take.number} words!")
            }
        } else if (quotable != null) {
            if (wordOne.startsWith("\"")) {
                sb.append(wordOne.substring(1))

                while (arguments.peek() != null) {
                    val word = arguments.poll()!!

                    if (word.endsWith("\"")) {
                        sb.append(word.substring(0, word.length - 1))
                        break
                    }

                    sb.append(word)
                }
            } else {
                sb.append(wordOne)
            }
        }

        return sb.toString()
    }
}