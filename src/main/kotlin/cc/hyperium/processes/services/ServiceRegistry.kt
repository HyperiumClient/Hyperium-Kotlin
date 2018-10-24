package cc.hyperium.processes.services

import cc.hyperium.Hyperium
import cc.hyperium.utils.Registry
import net.minecraft.client.resources.I18n
import org.reflections.Reflections

class ServiceRegistry : Registry<AbstractService>() {

    private var initialised = false

    fun bootstrap(ref: Reflections) {
        if (this.initialised) {
            throw IllegalStateException("ServiceRegistry has already been initialised!")
        }

        fun onError(serviceName: String): AbstractService? {
            Hyperium.LOGGER.error(I18n.format("error.loading.service", serviceName))
            return null
        }

        this += ref.getTypesAnnotatedWith(Service::class.java)
                .asSequence()
                .map { it.kotlin.objectInstance as? AbstractService ?: onError(it.name) }
                .filterNotNull()
                .toList()

        this.initialised = true
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