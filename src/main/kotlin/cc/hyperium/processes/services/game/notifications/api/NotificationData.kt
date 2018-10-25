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
        private var title: String = ""
        private var description: String = ""
        private var backgroundColor: Color = Color(80, 80, 80)
        private var accentColor: Color = Color(149, 201, 144)
        private var link: String? = null
        private var callback: (() -> Unit)? = null

        fun title(title: String) = apply { this.title = title }

        fun description(description: String) = apply { this.description = description }

        fun backgroundColor(backgroundColor: Color) = apply { this.backgroundColor = backgroundColor }

        fun accentColor(accentColor: Color) = apply { this.accentColor = accentColor }

        fun link(link: String) = apply { this.link = link }

        fun callback(callback: (() -> Unit)?) = apply { this.callback = callback }

        fun build() = NotificationData(title, description, backgroundColor, accentColor, link, callback)
    }
}