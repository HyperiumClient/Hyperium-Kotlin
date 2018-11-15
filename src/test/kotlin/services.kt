package cc.hyperium.processes

import cc.hyperium.processes.services.AbstractService
import cc.hyperium.processes.services.ServiceRegistry
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TestServices {
    private var registry = ServiceRegistry()

    @BeforeEach
    fun setup() {
        registry = ServiceRegistry()
    }

    @Test
    fun `services are initialized when added to registry`() {
        val service = mockk<AbstractService>(relaxUnitFun = true)

        registry.add(service)

        verify { service.initialize() }
    }

    @Test
    fun `services are removed from registry when shutdown`() {
        val service = mockk<AbstractService>(relaxUnitFun = true)
        every { service.kill() } returns true

        registry.add(service)
        registry.shutdownServices()

        assertEquals(0, registry.size)
    }

    @Test
    fun `services are not removed from registry when they fail to shutdown`() {
        val service = mockk<AbstractService>(relaxUnitFun = true)
        every { service.kill() } returns false

        registry.add(service)
        registry.shutdownServices()

        assertEquals(1, registry.size)
    }
}