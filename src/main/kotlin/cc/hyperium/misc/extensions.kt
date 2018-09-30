package cc.hyperium.misc

import java.awt.Color

fun Color.setAlpha(alpha: Int): Color {
    return Color(
            red,
            green,
            blue,
            alpha
    )
}