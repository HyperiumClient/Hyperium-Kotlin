package cc.hyperium

import cc.hyperium.service.IService
import cc.hyperium.service.Service
import me.kbrewster.blazeapi.events.InitializationEvent
import me.kbrewster.eventbus.Subscribe
import org.reflections.Reflections
import org.reflections.scanners.MethodAnnotationsScanner
import org.reflections.scanners.SubTypesScanner
import org.reflections.scanners.TypeAnnotationsScanner

object Hyperium {
    val REFLECTIONS = Reflections("cc.hyperium", "com.chattriggers.ctjs", MethodAnnotationsScanner(), TypeAnnotationsScanner(), SubTypesScanner())

    @Subscribe
    fun onInit(event: InitializationEvent) {
        REFLECTIONS.getTypesAnnotatedWith(Service::class.java).map {
            it.kotlin.objectInstance as IService
        }.forEach(IService::initialize)
    }
}