package cc.hyperium.misc

import java.awt.Color

/**
 * TODO: move out into a more sensible place
 */
fun Color.setAlpha(alpha: Int): Color {
    return Color(
            red,
            green,
            blue,
            alpha
    )
}