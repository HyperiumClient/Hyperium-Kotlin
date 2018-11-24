package cc.hyperium.game.player

import cc.hyperium.imixins.entity.player.IMixinEntityPlayer
import cc.hyperium.processes.Process
import kotlinx.coroutines.Job
import net.minecraft.entity.player.EntityPlayer

/**
 * A class that holds all of the Hyperium specific data for any EntityPlayer.
 *
 * This is attached to one single EntityPlayer,
 * and will only exist while that EntityPlayer exists.
 */
class HyPlayer(private val backingPlayer: EntityPlayer) : Process {
    override val childProcesses = mutableListOf<Process>()
    override lateinit var job: Job
}

val EntityPlayer.hyplayer: HyPlayer
    get() = (this as IMixinEntityPlayer).hyPlayer
