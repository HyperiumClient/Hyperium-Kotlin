package cc.hyperium.game.server

open class MinecraftServer(val addresses: Array<String>, val port: Int = 25565) {

    fun onServerJoin() {}

    fun onServerLeave() {}
}