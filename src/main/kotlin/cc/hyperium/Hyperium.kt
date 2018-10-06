package cc.hyperium

import cc.hyperium.services.IService
import cc.hyperium.services.Service
import me.kbrewster.blazeapi.events.InitializationEvent
import me.kbrewster.eventbus.Subscribe
import net.minecraft.client.resources.I18n
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.reflections.Reflections
import org.reflections.scanners.MethodAnnotationsScanner
import org.reflections.scanners.SubTypesScanner
import org.reflections.scanners.TypeAnnotationsScanner
import java.lang.Exception

object Hyperium {
    val REFLECTIONS = Reflections("cc.hyperium", "com.chattriggers.ctjs", MethodAnnotationsScanner(), TypeAnnotationsScanner(), SubTypesScanner())
    
    private val LOGGER: Logger = LogManager.getLogger()

    @Subscribe
    fun onInit(event: InitializationEvent) {
        REFLECTIONS.getTypesAnnotatedWith(Service::class.java).asSequence().map {
            return@map try {
                it.kotlin.objectInstance as IService
            } catch (e: Exception) {
                LOGGER.error(I18n.format("error.loading.service", it.name))
                null
            }
        }.filterNotNull().forEach(IService::initialize)
    }
}