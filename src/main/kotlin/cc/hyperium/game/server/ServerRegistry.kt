package cc.hyperium.game.server

import cc.hyperium.services.utilities.RegisterEvents
import cc.hyperium.utils.Registry
import me.kbrewster.blazeapi.events.ServerDisconnectEvent
import me.kbrewster.blazeapi.events.ServerJoinEvent
import me.kbrewster.eventbus.Subscribe

@RegisterEvents
object ServerRegistry : Registry<MinecraftServer>() {
    private var currentServer: MinecraftServer? = null

    @Subscribe
    fun joinServer(e: ServerJoinEvent) {
        this.currentServer = this.find { it.addresses.contains(e.ip) && it.port == e.port }
        this.currentServer?.onServerJoin()
    }

    @Subscribe
    fun leaveServer(e: ServerDisconnectEvent) {
        this.currentServer?.onServerLeave()
        this.currentServer = null
    }
}