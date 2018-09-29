package cc.hyperium.commands.api

interface ICommand {
    fun getName(): String
    fun getUsage(): String
    fun execute(@Greedy args: String)
}

/**
 * Registers this function as a command. The function must belong to a kotlin
 * object so the instance can be located and used to call the function.
 *
 * Also specifies the function to use to display an error to the user when parameters
 * cannot be parsed correctly, called the 'usage' function. The default is 'getUsage'.
 * If this function is missing or uncallable, a generic 'An error has occurred' message will appear.
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Command(val name: String, val usage: String = "getUsage")

/**
 * Marks this parameter as greedy and should take up the rest of the available command.
 * This is primarily meant for the last parameter in a function, as anything after it will be left without
 * a value.
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class Greedy

/**
 * Indicates that this parameter should take [number] words of the command.
 *
 * If [allowLess] is set to true, anything from 1 to [number] words are allowed,
 * however if it is set to false an error will be thrown and the usage message
 * will be displayed, which should indicate to the user the amount of words
 * needed.
 *
 * If you need this specific amount of words to create a custom data type
 * look into creating and registering an [ArgumentParser] instead.
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class Take(val number: Int, val allowLess: Boolean = true)