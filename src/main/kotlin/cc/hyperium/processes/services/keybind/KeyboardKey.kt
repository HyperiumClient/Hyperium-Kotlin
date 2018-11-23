package cc.hyperium.processes.services.keybind

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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        return this.keyCode == (other as LWJGLKey).keyCode
    }

    override fun hashCode(): Int {
        return Objects.hash(keyCode)
    }
}