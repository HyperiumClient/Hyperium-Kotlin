package cc.hyperium.commands.engine

import kotlin.reflect.KFunction
import kotlin.reflect.KParameter

data class CommandData(
        val name: String,
        val parameters: List<KParameter>,
        val function: KFunction<*>,
        val usage: KFunction<String>,
        val instance: Any
)