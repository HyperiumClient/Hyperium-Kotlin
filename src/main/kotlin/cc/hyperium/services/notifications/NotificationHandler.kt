package cc.hyperium.services.notifications

import cc.hyperium.misc.mouseX
import cc.hyperium.misc.mouseY
import cc.hyperium.services.AbstractService
import cc.hyperium.services.Service
import cc.hyperium.services.commands.api.Command
import cc.hyperium.services.commands.api.Greedy
import cc.hyperium.services.commands.api.Quotable
import cc.hyperium.services.notifications.api.Notification
import cc.hyperium.services.notifications.api.NotificationData
import me.kbrewster.blazeapi.client.mc
import me.kbrewster.blazeapi.events.ClientTickEvent
import me.kbrewster.blazeapi.events.RenderEvent
import me.kbrewster.eventbus.Subscribe
import net.minecraft.client.Minecraft
import java.util.*

@Service
object NotificationHandler : AbstractService() {
    private val notifications: Queue<Notification> = LinkedList()
    private var currentNotif: Notification? = null
    private var sysTime: Long = Minecraft.getSystemTime()
    private const val FPS = 60L

    @Command("addNotif")
    fun notifCommand(@Quotable title: String) {
        addNotification(NotificationData.Builder()
                        .title(title)
                        .build())
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