package cc.hyperium.processes

import cc.hyperium.processes.services.commands.api.ArgumentQueue
import cc.hyperium.processes.services.commands.engine.CommandData
import cc.hyperium.processes.services.commands.engine.CommandParser
import cc.hyperium.utils.Failure
import cc.hyperium.utils.Success
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertAll
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestCommands {
    private val USAGE = "Usage"

    private lateinit var data: CommandData

    @BeforeAll
    fun construct() {
        val function = TestCommands::exampleFun
        val params = function.parameters

        data = CommandData(
            name = "",
            parameters = params,
            function = function,
            usage = TestCommands::usageFun,
            instance = Unit
        )
    }

    @Test
    fun `instance params return the class instance`() {
        val args = ArgumentQueue(LinkedList())

        val result = CommandParser.parseParameter(
            data.parameters[0],
            args,
            data
        )

        assertAll({
            assertTrue(result is Success)
        }, {
            assertEquals((result as Success).value, Unit)
        })
    }

    @Test
    fun `valid normal arguments return success`() {
        val args = ArgumentQueue(LinkedList(listOf("1337")))

        val result = CommandParser.parseParameter(
            data.parameters[1],
            args,
            data
        )

        assertTrue(result is Success)
    }

    @Test
    fun `invalid normal arguments return failure`() {
        val args = ArgumentQueue(LinkedList(listOf("string")))

        val result = CommandParser.parseParameter(
            data.parameters[1],
            args,
            data
        )

        assertTrue(result is Failure)
    }

    @Test
    fun `valid optional arguments return success`() {
        val args = ArgumentQueue(LinkedList(listOf("1337")))

        val result = CommandParser.parseParameter(
            data.parameters[2],
            args,
            data
        )

        assertTrue(result is Success)
    }

    @Test
    fun `invalid optional arguments return success`() {
        val args = ArgumentQueue(LinkedList(listOf("string")))

        val result = CommandParser.parseParameter(
            data.parameters[2],
            args,
            data
        )

        assertTrue(result is Success)
    }

    private fun exampleFun(normal: Int, optional: Optional<Int>) {}
    private fun usageFun() = USAGE
}
