package cc.hyperium.processes

import cc.hyperium.game.server.MinecraftServer
import cc.hyperium.game.server.ServerRegistry
import io.mockk.clearMocks
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import me.kbrewster.blazeapi.events.ServerJoinEvent
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class TestServers {
    private var registry = ServerRegistry()
    private val server = spyk(MinecraftServer(
        listOf("server.ip")
    ))
    private val junkServer = spyk(MinecraftServer(
        listOf("fake")
    ))

    @BeforeEach
    fun setup() {
        clearMocks(server, junkServer)

        registry = ServerRegistry()
        registry.add(server)
    }

    @Nested
    inner class Joining {
        @Test
        fun `server join is called when joining a server`() {
            val event = ServerJoinEvent("server.ip", 25565)

            registry.joinServer(event)

            verify { server.onServerJoin() }
        }

        @Test
        fun `only the correct server is joined`() {
            val event = ServerJoinEvent("server.ip", 25565)

            registry.joinServer(event)

            verify { server.onServerJoin() }
            verify(inverse = true) { junkServer.onServerJoin() }
        }
    }

    @Nested
    inner class Leaving {
        @Test
        fun `server leave is called when leaving a server`() {
            val event = ServerJoinEvent("server.ip", 25565)

            registry.joinServer(event)
            registry.leaveServer(mockk())

            verify { server.onServerLeave() }
        }

        @Test
        fun `only the correct server is joined`() {
            val event = ServerJoinEvent("server.ip", 25565)

            registry.joinServer(event)
            registry.leaveServer(mockk())

            verify { server.onServerLeave() }
            verify(inverse = true) { junkServer.onServerLeave() }
        }
    }
}