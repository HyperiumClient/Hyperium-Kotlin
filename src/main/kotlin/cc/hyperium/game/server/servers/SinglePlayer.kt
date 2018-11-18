package cc.hyperium.game.server.servers

import cc.hyperium.game.server.MinecraftServer

class SinglePlayer : MinecraftServer(
    arrayOf("localhost")
) {

}