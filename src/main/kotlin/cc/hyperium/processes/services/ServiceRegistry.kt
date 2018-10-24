package cc.hyperium.processes.services

import cc.hyperium.Hyperium
import cc.hyperium.processes.Process
import cc.hyperium.utils.Registry
import net.minecraft.client.resources.I18n
import org.reflections.Reflections

class ServiceRegistry : Registry<Process>() {

    private var initialised = false

    fun bootstrap(ref: Reflections) {
        if (this.initialised) {
            throw IllegalStateException("ServiceRegistry has already been initialised!")
        }

        fun onError(serviceName: String): Process? {
            Hyperium.LOGGER.error(I18n.format("error.loading.service", serviceName))
            return null
        }

        this += ref.getTypesAnnotatedWith(Service::class.java)
                .asSequence()
                .map { it.kotlin.objectInstance as? Process ?: onError(it.name) }
                .filterNotNull()
                .toList()

        this.initialised = true
    }

    override fun add(element: Process): Boolean {
        element.initialize()
        return super.add(element)
    }

    override fun addAll(elements: Collection<Process>): Boolean {
        elements.forEach(Process::initialize)
        return super.addAll(elements)
    }


    fun shutdownServices() {
        this.removeAll { service ->
            service.kill()
        }
    }

}