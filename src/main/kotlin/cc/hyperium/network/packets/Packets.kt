package cc.hyperium.network.packets

enum class Packets(val ID: Int) {
    HEARTBEAT(0);

    fun instance(): IPacket {
        return create(this)
    }

    fun <T : IPacket> instanceAs(): T {
        return instance() as T
    }

    companion object {
        fun create(type: Packets): IPacket {
            return when (type) {
                HEARTBEAT -> Heartbeat(System.currentTimeMillis())
                else -> TODO("The Packet Type $type cannot be automatically instantiated!")
            }
        }
    }
}