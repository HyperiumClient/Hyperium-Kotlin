package cc.hyperium.game.server

import cc.hyperium.game.player.HyPlayer
import cc.hyperium.processes.Process
import kotlinx.coroutines.Job
import me.kbrewster.blazeapi.events.PlayerDespawnEvent
import me.kbrewster.blazeapi.events.PlayerSpawnEvent
import me.kbrewster.eventbus.Subscribe
import java.util.*

abstract class MinecraftServer(
    val addresses: Array<String>,
    val port: Int = 25565
) : Process {

    override val childProcesses = mutableListOf<Process>()
    override lateinit var job: Job

    val playerMap = mutableMapOf<UUID, HyPlayer>()

    @Subscribe
    fun onPlayerJoin(e: PlayerSpawnEvent) {
        if (playerMap.containsKey(e.player.uniqueID)) return

        val hyplayer = HyPlayer(e.player)
        hyplayer.initialize()

        childProcesses.add(hyplayer)
        playerMap[e.player.uniqueID] = hyplayer
    }

    @Subscribe
    fun onPlayerLeave(e: PlayerDespawnEvent) {
        val hyplayer = playerMap.remove(e.player.uniqueID) ?: return

        hyplayer.kill()
        childProcesses.remove(hyplayer)
    }

    override fun initialize() {
        super.initialize()
        playerMap.clear()
        onServerJoin()
    }

    override fun kill(): Boolean {
        onServerLeave()
        playerMap.clear()
        return super.kill()
    }

    fun onServerJoin() {}

    fun onServerLeave() {}
}