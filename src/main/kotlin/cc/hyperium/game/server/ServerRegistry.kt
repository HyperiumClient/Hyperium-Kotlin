package cc.hyperium.game.server

import cc.hyperium.services.utilities.Register
import cc.hyperium.utils.Registry
import me.kbrewster.blazeapi.events.ServerDisconnectEvent
import me.kbrewster.blazeapi.events.ServerJoinEvent
import me.kbrewster.eventbus.Subscribe

@Register
object ServerRegistry : Registry<MinecraftServer>() {

    private val servers = mutableListOf<MinecraftServer>()
    private var currentServer: MinecraftServer? = null

    @Subscribe
    fun joinServer(e: ServerJoinEvent) {
        this.currentServer = servers.find { it.addresses.contains(e.ip) && it.port == e.port }
        this.currentServer?.onServerJoin()
    }

    @Subscribe
    fun leaveServer(e: ServerDisconnectEvent) {
        this.currentServer?.onServerLeave()
    }
}