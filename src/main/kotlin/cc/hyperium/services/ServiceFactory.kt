package cc.hyperium.services

import cc.hyperium.Hyperium
import net.minecraft.client.resources.I18n
import org.reflections.Reflections

class ServiceFactory {

    private val services = ArrayList<IService>()

    @JvmName("addService")
    operator fun plusAssign(service: IService) {
        service.initialize()
        this.services.add(service)
    }

    @JvmName("addServices")
    operator fun plusAssign(services: List<IService>) {
        services.forEach(IService::initialize)
        this.services.addAll(services)
    }

    fun bootstrapServices(ref: Reflections) {
        fun onError(serviceName: String): IService? {
            Hyperium.LOGGER.error(I18n.format("error.loading.service", serviceName))
            return null
        }

        this += ref.getTypesAnnotatedWith(Service::class.java)
                .asSequence()
                .map { it.kotlin.objectInstance as? IService ?: onError(it.name) }
                .filterNotNull()
                .toList()
    }

    fun shutdownServices() {
        this.services.removeAll { service ->
            service.destroy()
        }
    }

    fun getServices(): ArrayList<IService> {
        return this.services
    }

}