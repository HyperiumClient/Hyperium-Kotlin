package cc.hyperium.services.game.notifications

import cc.hyperium.services.AbstractService
import cc.hyperium.services.game.notifications.api.Notification
import cc.hyperium.services.game.notifications.api.NotificationData
import me.kbrewster.blazeapi.EVENT_BUS
import me.kbrewster.blazeapi.events.ClientTickEvent
import me.kbrewster.blazeapi.events.RenderEvent
import me.kbrewster.eventbus.Subscribe
import net.minecraft.client.Minecraft
import java.util.*

class NotificationService : AbstractService() {

    private val notifications: Queue<Notification> = LinkedList()
    private var currentNotif: Notification? = null
    private var sysTime: Long = Minecraft.getSystemTime()
    private val FPS = 60L

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

        while (sysTime < Minecraft.getSystemTime() + 1000 / FPS) {
            currentNotif?.update()
            this.sysTime += 1000 / FPS

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