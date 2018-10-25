package cc.hyperium.processes.services.commands.api

import java.util.*

class ArgumentQueue(private val backingDeque: Deque<String>) {
    private val changed = mutableListOf<String>()

    fun poll(): String? {
        changed.add(peek() ?: return null)
        return backingDeque.pollFirst()
    }

    fun peek(): String? {
        return backingDeque.peekFirst()
    }

    internal fun undo() {
        changed.forEach {
            backingDeque.addFirst(it)
        }

        sync()
    }

    internal fun sync() {
        changed.clear()
    }
}