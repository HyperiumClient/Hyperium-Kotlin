package cc.hyperium.commands

import cc.hyperium.commands.api.Command
import cc.hyperium.commands.api.Greedy
import java.util.*

object CmdTest {
    @Command("gay")
    fun gayCommand(thing1: Int, thing2: Optional<Int>) {
        println("WOW $thing1")
    }

    @Command("thingy")
    fun thingyCommand(@Greedy consumer: String) {

    }

    fun getUsage(): String {
        return "L"
    }
}