package cc.hyperium.services

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


    fun shutdownServices() {
        this.services.removeAll { service ->
            service.destroy()
        }
    }

    fun getServices(): ArrayList<IService> {
        return this.services
    }

}