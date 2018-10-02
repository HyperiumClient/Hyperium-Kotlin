package cc.hyperium.services.keybind

import cc.hyperium.services.AbstractService
import cc.hyperium.services.Service
import me.kbrewster.blazeapi.events.InputEvents
import me.kbrewster.eventbus.Subscribe

@Service
object KeybindManager : AbstractService() {

    val bindingSystems = HashSet<BindingSystem<KeyboardKey>>()

    override fun initialize() {
        super.initialize()
        println("test123")
        bindingSystems += object : BindingSystem<KeyboardKey> {

            override fun onKeyPress(key: KeyboardKey) {
                println("you pressed me, bit gay js.")
            }

            override fun onKeyRelease(key: KeyboardKey) {

            }

        }
    }

    @Subscribe
    fun onKeyPress(event: InputEvents.Keypress) {
        this.bindingSystems.forEach { system ->
            val key = LWJGLKey.fromIndex(event.key)
            if (event.isPressed) {
                system.onKeyPress(key)
            } else {
                system.onKeyRelease(key)
            }
        }
    }


}