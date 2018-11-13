package cc.hyperium.game.server

import cc.hyperium.processes.Process
import kotlinx.coroutines.Job

abstract class MinecraftServer(
    val addresses: Array<String>,
    val port: Int = 25565
) : Process {

    override val childProcesses = mutableListOf<Process>()
    override lateinit var job: Job

    override fun initialize() {
        super.initialize()
        onServerJoin()
    }

    override fun kill(): Boolean {
        onServerLeave()
        return super.kill()
    }

    fun onServerJoin() {}

    fun onServerLeave() {}
}