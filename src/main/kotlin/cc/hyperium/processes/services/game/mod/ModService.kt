package cc.hyperium.processes.services.game.mod

import cc.hyperium.Hyperium
import cc.hyperium.processes.Process
import cc.hyperium.processes.services.AbstractService
import cc.hyperium.processes.services.Service

@Service
object ModService : AbstractService() {

    /**
     * Technically a list of all the mods which are running
     */
    override val childProcesses = ArrayList<Process>()

    override fun initialize() {
        Hyperium.REFLECTIONS.getSubTypesOf(Mod::class.java)
                .asSequence()
                .map(this::classToMod)
                .filterNotNull()
                .forEach(this::registerMod)

        childProcesses.forEach(Process::initialize)
    }

    private fun classToMod(clazz: Class<*>): Mod? {
        return try {
            (clazz.kotlin.objectInstance ?: clazz.newInstance()) as Mod
        } catch (e: Exception) {
            null
        }
    }

    fun registerMod(mod: Mod) {
        childProcesses.add(mod)
    }

    fun killMod(mod: Mod): Boolean {
        return childProcesses.find { it == mod }?.kill() ?: false
    }

}