package cc.hyperium.processes.services.keybind

interface Bindable<Key : KeyboardKey> : KeyListener<Key>
