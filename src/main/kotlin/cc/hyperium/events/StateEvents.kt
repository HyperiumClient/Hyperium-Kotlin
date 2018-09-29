package cc.hyperium.events

import java.io.File

open class StateEvent(val configDirectory: File) : Event()

class PreInitializationEvent(configDirectory: File) : StateEvent(configDirectory)

class InitializationEvent(configDirectory: File) : StateEvent(configDirectory)

class PostInitializationEvent(configDirectory: File) : StateEvent(configDirectory)