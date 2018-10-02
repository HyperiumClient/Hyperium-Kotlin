package cc.hyperium.services.keybind

interface KeyListener<Key : KeyboardKey> {

    fun onKeyPress(key: Key)

    fun onKeyRelease(key: Key)

}