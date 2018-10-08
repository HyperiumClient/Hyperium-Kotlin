package cc.hyperium.services.keybind

import cc.hyperium.services.AbstractService
import cc.hyperium.services.Service
import me.kbrewster.blazeapi.events.InputEvents
import me.kbrewster.eventbus.Subscribe
@Service
object KeybindManager : AbstractService() {

    val bindings = HashSet<Bindable<LWJGLKey>>()

    override fun initialize() {
        super.initialize()

        this.bindings.add(DefaultBindable(LWJGLKey.fromName("A"), object : KeyListener<LWJGLKey> {
            override fun onKeyPress(key: LWJGLKey) {
                println("hello world")
            }

            override fun onKeyRelease(key: LWJGLKey) {
                println("other heelo world")
            }

        }))


    }

    @Subscribe
    fun onKeyPress(event: InputEvents.Keypress) {
        println("event")
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