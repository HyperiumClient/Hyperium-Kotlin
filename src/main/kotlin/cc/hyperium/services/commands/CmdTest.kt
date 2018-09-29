package cc.hyperium.services.commands

import cc.hyperium.services.commands.api.Command
import cc.hyperium.services.commands.api.Greedy
import java.util.*

object CmdTest {
    @Command("gay")
    fun gayCommand(thing1: Int, thing2: Optional<Int>, thing3: Optional<String>) {
        println("WOW $thing1")

        if (thing2.isPresent) println("WOW2 ${thing2.get()}")

        if (thing3.isPresent) println("STRING ${thing3.get()}")
    }

    @Command("thingy")
    fun thingyCommand(@Greedy consumer: String) {
        println("woW $consumer")
    }

    fun getUsage(): String {
        return "L"
    }
}