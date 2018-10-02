package cc.hyperium.services.keybind

interface Bindable<Key : KeyboardKey> : KeyListener<Key>, Iterable<Set<Key>> {

    fun bind(keys: Set<Key>)

    fun unbind(keys: Set<Key>)

    fun areBound(keys: Set<Key>): Boolean

}