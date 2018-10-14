package cc.hyperium.services.mods

import cc.hyperium.Hyperium
import cc.hyperium.services.AbstractService
import cc.hyperium.services.Service

@Service
object ModService : AbstractService() {
    var mods = listOf<Mod>()

    override fun initialize() {
        super.initialize()

        loadMods()
    }

    fun loadMods() {
        mods = Hyperium.REFLECTIONS.getSubTypesOf(Mod::class.java)
                .asSequence()
                .map { return@map try { it.newInstance() } catch (e: Exception) { null } }.filterNotNull()
                .toList()

        mods.forEach(Mod::initialize)
    }

    fun reloadMods() {
        destroyMods()
        loadMods()
    }

    fun destroyMods() {
        mods.forEach(Mod::destroy)
    }

    override fun destroy(): Boolean {
        destroyMods()

        return super.destroy()
    }
}