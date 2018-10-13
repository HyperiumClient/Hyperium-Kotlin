package cc.hyperium.services.keybind

import cc.hyperium.services.AbstractService
import cc.hyperium.services.Service
import me.kbrewster.blazeapi.events.InputEvents
import me.kbrewster.eventbus.Subscribe
@Service
object KeybindManager : AbstractService() {

    val bindings = HashSet<Bindable<LWJGLKey>>()

    @Subscribe
    fun onKeyPress(event: InputEvents.Keypress) {
        this.bindings.forEach { binding ->
            val key = LWJGLKey.fromIndex(event.key)
            if (event.isPressed) {
                binding.onKeyPress(key)
            } else {
                binding.onKeyRelease(key)
            }
        }
    }


}