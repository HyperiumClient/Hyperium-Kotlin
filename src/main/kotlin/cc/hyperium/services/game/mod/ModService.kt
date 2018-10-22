package cc.hyperium.services.game.mod

import cc.hyperium.Hyperium
import cc.hyperium.services.AbstractService
import cc.hyperium.services.IService

object ModService : AbstractService() {

    /**
     * Technically a list of all the mods which are running
     */
    override val subServices = ArrayList<IService>()

    override fun initialize() {
        Hyperium.REFLECTIONS.getSubTypesOf(Mod::class.java)
                .asSequence()
                .map(this::classToMod)
                .filterNotNull()
                .forEach(this::registerMod)

        this.subServices.forEach(IService::initialize)
    }

    private fun classToMod(clazz: Class<*>): Mod? {
        return try {
            (clazz.kotlin.objectInstance ?: clazz.newInstance()) as Mod
        } catch (e: Exception) {
            null
        }
    }

    fun registerMod(mod: Mod) {
        this.subServices.add(mod)
    }

    fun killMod(mod: Mod): Boolean {
        return this.subServices.find { it == mod }?.kill() ?: false
    }

}