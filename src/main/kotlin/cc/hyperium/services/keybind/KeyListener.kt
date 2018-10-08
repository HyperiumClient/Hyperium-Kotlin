package cc.hyperium.services.keybind

interface KeyListener<Key : KeyboardKey> {

    fun onKeyPress(key: Key)

    fun onKeyRelease(key: Key)

}

abstract class AbstractKeyListener<Key : KeyboardKey> : KeyListener<Key> {

    override fun onKeyPress(key: Key) {}

    override fun onKeyRelease(key: Key) {}
}