package cc.hyperium.processes.services.commands

import cc.hyperium.network.packets.CrossClientData
import cc.hyperium.network.packets.Packets
import cc.hyperium.processes.services.commands.api.Command
import cc.hyperium.processes.services.commands.api.Greedy
import cc.hyperium.processes.services.commands.api.Quotable
import cc.hyperium.processes.services.network.NetworkManager
import java.util.*

object CmdTest {
    @Command("packet")
    fun heartbeat(type: Optional<String>) {
        if (!type.isPresent) {
            NetworkManager.sendPacket(Packets.HEARTBEAT.newInstance())
            println("Sent packet!")
            return
        }

        when (type.get()) {
            "data" -> NetworkManager.sendPacket(CrossClientData("LUL!"))
        }

        println("Sent packet!")
    }

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

    @Command("quotes")
    fun withQuotes(@Quotable quotes: String) {
        println("cool $quotes")
    }

    fun getUsage(): String {
        return "L"
    }
}