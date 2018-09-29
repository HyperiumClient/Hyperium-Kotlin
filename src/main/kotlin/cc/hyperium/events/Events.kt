package cc.hyperium.events

open class Event

open class CancellableEvent : Event() {
    val cancelled = false
}