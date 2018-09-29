package cc.hyperium

import cc.hyperium.commands.CommandManager
import cc.hyperium.events.InitializationEvent
import com.google.common.eventbus.EventBus
import com.google.common.eventbus.Subscribe
import org.reflections.Reflections
import org.reflections.scanners.MethodAnnotationsScanner

object Hyperium {
    val EVENT_BUS = EventBus()
    val REFLECTIONS = Reflections("cc.hyperium", "com.chattriggers.ctjs", MethodAnnotationsScanner())

    init {
        EVENT_BUS.register(this)
    }

    @Subscribe
    fun onInit(event: InitializationEvent) {
        CommandManager
    }
}