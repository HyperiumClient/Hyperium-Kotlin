package cc.hyperium.commands.api

import kotlin.reflect.KParameter

/**
 * Defines how to parse a certain data type using the available command parameters.
 *
 * Register using [cc.hyperium.commands.engine.CommandParser.registerArgumentParser]
 */
interface ArgumentParser {
    /**
     * Parses to a certain type based on the arguments provided by the user
     * and the parameter (for accessing annotations).
     *
     * If the arguments provided do not allow for your custom type to be created, throw an
     * Exception.
     */
    @Throws(Exception::class)
    fun parse(arguments: ArgumentQueue, param: KParameter): Any?
}