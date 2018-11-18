package cc.hyperium.game.server

import cc.hyperium.processes.services.utilities.RegisterEvents
import cc.hyperium.utils.Registry
import cc.hyperium.utils.instance
import me.kbrewster.blazeapi.EVENT_BUS
import me.kbrewster.blazeapi.events.ServerDisconnectEvent
import me.kbrewster.blazeapi.events.ServerJoinEvent
import me.kbrewster.eventbus.Subscribe
import org.reflections.Reflections

@RegisterEvents
class ServerRegistry : Registry<MinecraftServer>() {
    private var currentServer: MinecraftServer? = null

    fun bootstrap(ref: Reflections) {
        ref.getSubTypesOf(MinecraftServer::class.java).forEach {
            this.add(it.instance())
        }
    }

    @Subscribe
    fun joinServer(e: ServerJoinEvent) {
        this.currentServer = this.find { it.addresses.contains(e.ip) && it.port == e.port }

        this.currentServer?.let {
            EVENT_BUS.register(it)
            it.initialize()
        }
    }

    @Subscribe
    fun leaveServer(e: ServerDisconnectEvent) {
        this.currentServer?.let {
            it.kill()
            EVENT_BUS.unregister(it)
        }

        this.currentServer = null
    }
}