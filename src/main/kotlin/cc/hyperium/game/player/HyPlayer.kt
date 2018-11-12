package cc.hyperium.game.player

import cc.hyperium.game.server.ServerRegistry
import cc.hyperium.processes.Process
import kotlinx.coroutines.Job
import net.minecraft.entity.player.EntityPlayer

/**
 * A class that holds all of the Hyperium
 * specific data for any EntityPlayer.
 */
class HyPlayer(private val backingPlayer: EntityPlayer) : Process {
    override val childProcesses = mutableListOf<Process>()
    override lateinit var job: Job
}

val EntityPlayer.hyplayer: HyPlayer?
    get() = ServerRegistry.currentServer?.playerMap?.get(this.uniqueID)