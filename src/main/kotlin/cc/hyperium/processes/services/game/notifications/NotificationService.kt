package cc.hyperium.processes.services.game.notifications

import cc.hyperium.processes.services.AbstractService
import cc.hyperium.processes.services.Service
import cc.hyperium.processes.services.game.notifications.api.Notification
import cc.hyperium.processes.services.game.notifications.api.NotificationData
import me.kbrewster.blazeapi.EVENT_BUS
import me.kbrewster.blazeapi.events.ClientTickEvent
import me.kbrewster.blazeapi.events.RenderEvent
import me.kbrewster.eventbus.Subscribe
import net.minecraft.client.Minecraft
import org.kodein.di.Kodein
import java.util.*

@Service
class NotificationService(override val kodein: Kodein) : AbstractService() {
    private val notifications: Queue<Notification> = LinkedList()
    private var currentNotif: Notification? = null
    private var sysTime: Long = Minecraft.getSystemTime()
    private val fps = 60L

    override fun initialize() {
        super.initialize()
        EVENT_BUS.register(this)
    }

    override fun kill(): Boolean {
        EVENT_BUS.unregister(this)
        return super.kill()
    }

    @Subscribe
    fun onTick(event: ClientTickEvent) {
        if (currentNotif?.complete != false) {
            currentNotif = notifications.poll()

            if (currentNotif != null) sysTime = Minecraft.getSystemTime()
        }
    }

    @Subscribe
    fun onRender(event: RenderEvent) {
        if (currentNotif == null) return
        var calls = 0

        while (sysTime < Minecraft.getSystemTime() + 1000 / fps) {
            currentNotif?.update()
            this.sysTime += 1000 / fps

            calls++
        }

        currentNotif?.render()
    }

    fun addNotification(data: NotificationData) {
        addNotification(Notification(data))
    }

    fun addNotification(notification: Notification) {
        notifications.offer(notification)
    }
}
