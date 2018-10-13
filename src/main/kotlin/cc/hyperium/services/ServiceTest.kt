package cc.hyperium.services

import cc.hyperium.services.commands.api.Command
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch

@Service
object CoroutineTest : AbstractService() {
    override fun initialize() {
        super.initialize()

        launch {
            while (true) {
                println("LOLGAY")
                delay(1000)
            }
        }
    }

    @Command("kill")
    fun kill() {
        println("KILLING")

        destroy()
    }
}