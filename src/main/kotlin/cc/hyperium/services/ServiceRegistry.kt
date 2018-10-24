package cc.hyperium.services

import cc.hyperium.Hyperium
import cc.hyperium.utils.Registry
import net.minecraft.client.resources.I18n
import org.reflections.Reflections

class ServiceRegistry : Registry<IService>() {

    private var initialised = false

    fun bootstrap(ref: Reflections) {
        if (this.initialised) {
            throw IllegalStateException("ServiceRegistry has already been initialised!")
        }

        fun onError(serviceName: String): IService? {
            Hyperium.LOGGER.error(I18n.format("error.loading.service", serviceName))
            return null
        }

        this += ref.getTypesAnnotatedWith(Service::class.java)
            .asSequence()
            .map { it.kotlin.objectInstance as? IService ?: onError(it.name) }
            .filterNotNull()
            .toList()

        this.initialised = true
    }

    override fun add(element: IService): Boolean {
        element.initialize()
        return super.add(element)
    }

    override fun addAll(elements: Collection<IService>): Boolean {
        elements.forEach(IService::initialize)
        return super.addAll(elements)
    }


    fun shutdownServices() {
        this.removeAll { service ->
            service.kill()
        }
    }

}