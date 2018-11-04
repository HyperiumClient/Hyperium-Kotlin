package cc.hyperium.network

import cc.hyperium.network.packets.IPacket
import cc.hyperium.network.packets.Packets
import com.esotericsoftware.kryonet.Client
import com.esotericsoftware.kryonet.Connection
import com.esotericsoftware.kryonet.Listener
import java.net.SocketTimeoutException

object NetworkManager {

    private const val HOST = "localhost"
    private const val PORT = 9001

    private lateinit var client: Client

    /**
     * Attempts to connect to the Hyperium Server.
     *
     * This method will block for up to 5000 milliseconds while waiting for the connection.
     *
     * @throws SocketTimeoutException when the connection cannot be completed.
     */
    @Throws(SocketTimeoutException::class)
    internal fun bootstrapClient() {
        this.client = Client()
        this.client.start()
        this.client.connect(5000, HOST, PORT)

        Packets.forEach(::registerPacket)
    }

    /**
     * Registers a packet to be sent over the network.
     *
     * This function MUST be called before trying to use the
     * [sendPacket] function or else errors will be raised.
     */
    fun registerPacket(packet: Class<*>) {
        this.client.kryo.register(packet)
    }

    /**
     * Sends a packet over the network.
     *
     * To send a packet, it must be registered via the
     * [registerPacket] function beforehand.
     *
     * @return true if the packet was sent successfully
     */
    fun sendPacket(packet: IPacket): Boolean {
        if (!this.client.isConnected) return false

        return this.client.sendTCP(packet) != 0
    }

    /**
     * Registers a handler for a specific packet.
     *
     * The function passed in will be called every time the client receives a packet
     * of the specified type. By making the type [IPacket], the function will be called
     * when receiving any packet.
     */
    inline fun <reified T : IPacket> addListener(crossinline listener: (T) -> Unit) {
        val listenerObj = object : Listener() {
            override fun received(connection: Connection, packet: Any?) {
                if (packet is T) {
                    listener(packet)
                }
            }
        }

        this.addListener(listenerObj)
    }

    fun addListener(listener: Listener) {
        this.client.addListener(listener)
    }

    fun removeListener(listener: Listener) {
        this.client.removeListener(listener)
    }

}