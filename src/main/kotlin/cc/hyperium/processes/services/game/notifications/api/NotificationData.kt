package cc.hyperium.processes.services.game.notifications.api

import java.awt.Color

data class NotificationData(
    val title: String,
    val description: String,
    val backgroundColor: Color,
    val accentColor: Color,
    val link: String?,
    val callback: (() -> Unit)?
) {

    class Builder {
        var title: String = ""
        var description: String = ""
        var backgroundColor: Color = Color(80, 80, 80)
        var accentColor: Color = Color(149, 201, 144)
        var link: String? = null
        var callback: (() -> Unit)? = null

        fun build() = NotificationData(title, description, backgroundColor, accentColor, link, callback)
    }
}
