package cc.hyperium.processes.services

import cc.hyperium.Hyperium
import cc.hyperium.utils.Registry
import cc.hyperium.utils.instance
import net.minecraft.client.resources.I18n
import org.kodein.di.Kodein
import org.reflections.Reflections

class ServiceRegistry : Registry<AbstractService>() {

    private var initialised = false

    fun bootstrap(ref: Reflections, kodein: Kodein) {
        if (this.initialised) {
            throw IllegalStateException("ServiceRegistry has already been initialised!")
        }

        ref.getTypesAnnotatedWith(Service::class.java)
            .asSequence()
            .sortedByDescending { it.getAnnotation(Service::class.java).priority.ordinal }
            .forEach {
                val instance = classToService(it, kodein)

                if (instance != null) this += instance
            }

        this.initialised = true
    }

    private fun classToService(clazz: Class<*>, kodein: Kodein) =
        try {
            clazz.instance<AbstractService>(kodein)
        } catch (e: Exception) {
            Hyperium.LOGGER.error(I18n.format("error.loading.service", clazz.name), e)
            null
        }

    override fun add(element: AbstractService): Boolean {
        element.initialize()
        return super.add(element)
    }

    override fun addAll(elements: Collection<AbstractService>): Boolean {
        elements.forEach(AbstractService::initialize)
        return super.addAll(elements)
    }

    fun shutdownServices() {
        this.removeAll(AbstractService::kill)
    }

}