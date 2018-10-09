package cc.hyperium.network.packets

class Heartbeat(val time: Long) : IPacket(Packets.HEARTBEAT.ID)