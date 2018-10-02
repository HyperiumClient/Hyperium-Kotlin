package cc.hyperium.services.keybind

import org.lwjgl.input.Keyboard
import java.util.*


open class KeyboardKey(val keyName: String, val keyCode: Int)

class LWJGLKey(keyName: String, keyCode: Int) :
        KeyboardKey(keyName, keyCode) {

    companion object {

        @JvmStatic
        fun fromName(keyName: String): LWJGLKey {
            val keyIndex = Keyboard.getKeyIndex(keyName)
            return if (keyIndex != -1) {
                LWJGLKey(keyName, keyIndex)
            } else {
                throw IllegalArgumentException("Key name '$keyName' does not map to a key index")
            }
        }

        @JvmStatic
        fun fromIndex(keyIndex: Int): LWJGLKey {
            val keyName = Keyboard.getKeyName(keyIndex)
            return LWJGLKey(keyName ?: Integer.toString(keyIndex), keyIndex)
        }

    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        return this.keyCode == (o as LWJGLKey).keyCode
    }

    override fun hashCode(): Int {
        return Objects.hash(keyCode)
    }
}