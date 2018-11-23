package cc.hyperium.processes.services.game.mod

import cc.hyperium.processes.services.AbstractService
import cc.hyperium.processes.services.Priority
import cc.hyperium.processes.services.Service
import cc.hyperium.utils.instance
import net.minecraft.client.resources.I18n
import org.apache.logging.log4j.Logger
import org.kodein.di.Kodein
import org.kodein.di.generic.instance
import org.reflections.Reflections

@Service(priority = Priority.LOWEST)
class ModService(override val kodein: Kodein) : AbstractService() {
    private val logger: Logger by kodein.instance()
    private val reflections: Reflections by kodein.instance()

    override fun initialize() {
        reflections.getSubTypesOf(Mod::class.java)
            .asSequence()
            .map(this::classToMod)
            .filterNotNull()
            .forEach(this::registerMod)

        super.initialize()
    }

    private fun classToMod(clazz: Class<*>) =
        try {
            clazz.instance<Mod>(kodein)
        } catch (e: Exception) {
            logger.error(I18n.format("error.loading.mod", clazz.name), e)
            null
        }

    fun registerMod(mod: Mod) {
        childProcesses.add(mod)
    }

    fun killMod(mod: Mod): Boolean {
        return childProcesses.find { it == mod }?.kill() ?: false
    }
}