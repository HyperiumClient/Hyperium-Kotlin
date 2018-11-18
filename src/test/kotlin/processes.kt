package cc.hyperium.processes

import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Job
import org.junit.jupiter.api.Test

class TestProcesses {
    private val child: Process = mockk(relaxed = true)
    private val parent = object : Process {
        override val childProcesses = mutableListOf(child)
        override var job = Job()
    }

    @Test
    fun `default processes start children`() {
        parent.initialize()

        verify { child.initialize() }
    }

    @Test
    fun `default processes kill children`() {
        parent.kill()

        verify { child.kill() }
    }
}