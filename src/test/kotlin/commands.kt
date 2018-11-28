package cc.hyperium.processes

import cc.hyperium.processes.services.commands.api.ArgumentQueue
import cc.hyperium.processes.services.commands.engine.CommandData
import cc.hyperium.processes.services.commands.engine.CommandParser
import cc.hyperium.utils.Failure
import cc.hyperium.utils.Success
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import java.util.*

class TestCommands {
    private val USAGE = "Usage"

    private val data = CommandData(
        name = "",
        parameters = TestCommands::exampleFun.parameters,
        function = TestCommands::exampleFun,
        usage = TestCommands::usageFun,
        instance = Unit
    )

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

    @Nested
    inner class NormalArguments {
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
    }

    @Nested
    inner class OptionalArguments {
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
    }

    private fun exampleFun(normal: Int, optional: Optional<Int>) {}
    private fun usageFun() = USAGE
}