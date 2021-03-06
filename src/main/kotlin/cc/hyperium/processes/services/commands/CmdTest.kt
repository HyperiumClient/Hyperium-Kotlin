package cc.hyperium.processes.services.commands

import cc.hyperium.Hyperium
import cc.hyperium.game.player.hyplayer
import cc.hyperium.network.NetworkManager
import cc.hyperium.network.packets.CrossClientData
import cc.hyperium.network.packets.Packets
import cc.hyperium.processes.services.commands.api.Command
import cc.hyperium.processes.services.commands.api.Greedy
import cc.hyperium.processes.services.commands.api.Quotable
import me.kbrewster.blazeapi.client.thePlayer
import org.kodein.di.generic.instance
import java.util.*

object CmdTest {
    private val network: NetworkManager = Hyperium.dkodein.instance()

    @Command("packet")
    fun heartbeat(type: Optional<String>) {
        if (!type.isPresent) {
            val res = network.sendPacket(Packets.HEARTBEAT.newInstance())

            println(if (res) "Sent packet!" else "Failed to send packet :(")

            return
        }

        val res = when (type.get()) {
            "data" -> network.sendPacket(CrossClientData("LUL!"))
            else -> false
        }

        println(if (res) "Sent packet!" else "Failed to send packet :(")
    }

    @Command("player")
    fun getPlayer(name: Optional<String>) {
        if (!name.isPresent) {
            println(thePlayer.hyplayer)
        }
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
