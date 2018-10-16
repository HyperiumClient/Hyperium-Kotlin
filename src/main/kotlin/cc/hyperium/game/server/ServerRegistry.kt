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
        servers.find {
            it.addresses.contains(e.ip) && it.port == e.port
        }?.let {
            this.currentServer = it
            it.onServerJoin()
        }
    }

    fun leaveServer(e: ServerDisconnectEvent) {
        currentServer?.onServerLeave()
    }
}