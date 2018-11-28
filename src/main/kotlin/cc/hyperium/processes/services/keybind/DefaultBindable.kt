package cc.hyperium.processes.services.keybind

class DefaultBindable<Key : KeyboardKey>(val key: Key, val listener: KeyListener<Key>) : Bindable<Key> {

    override fun onKeyPress(key: Key) {
        if (this.key == key) {
            this.listener.onKeyPress(key)
        }
    }

    override fun onKeyRelease(key: Key) {
        if (this.key == key) {
            this.listener.onKeyRelease(key)
        }
    }
}
