package cc.hyperium.network

import cc.hyperium.network.packets.IPacket
import cc.hyperium.network.packets.Packets
import com.esotericsoftware.kryonet.Client
import com.esotericsoftware.kryonet.Connection
import com.esotericsoftware.kryonet.Listener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import kotlin.coroutines.CoroutineContext

class NetworkManager : CoroutineScope {
    private val HOST = "localhost"
    private val PORT = 9001
    private lateinit var client: Client

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + job

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
     * This method defaults to being asynchronous,
     * but that can be changed with the [async] parameter.
     * If the method is asynchronous, this method will
     * always return true.
     *
     * @return true if the packet was sent successfully
     */
    fun sendPacket(packet: IPacket, async: Boolean = true): Boolean {
        if (!this.client.isConnected) return false

        if (async) {
            launch {
                client.sendTCP(packet)
            }

            return true
        }

        return this.client.sendTCP(packet) != 0
    }

    /**
     * Registers a handler for a specific packet.
     *
     * The function passed in will be called every time the client receives a packet
     * of the specified type. By making the type [IPacket], the function will be called
     * when receiving any packet.
     */
    inline fun <reified T : IPacket> addListener(crossinline listener: (packet: T) -> Unit) {
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

    fun finalize() {
        job.cancel()
    }
}