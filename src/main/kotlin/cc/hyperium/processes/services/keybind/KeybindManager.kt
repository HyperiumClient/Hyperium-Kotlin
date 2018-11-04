package cc.hyperium.processes.services.keybind

import cc.hyperium.processes.services.AbstractService
import cc.hyperium.processes.services.Service
import me.kbrewster.blazeapi.events.InputEvents
import me.kbrewster.eventbus.Subscribe
import org.kodein.di.Kodein

@Service
class KeybindManager(override val kodein: Kodein) : AbstractService() {

    val bindings = HashSet<Bindable<LWJGLKey>>()

    @Subscribe
    fun onKeyPress(event: InputEvents.Keypress) {
        bindings.forEach { binding ->
            val key = LWJGLKey.fromIndex(event.key)
            if (event.isPressed) {
                binding.onKeyPress(key)
            } else {
                binding.onKeyRelease(key)
            }
        }
    }


}