package cc.hyperium.game.mods.notifications.api

import cc.hyperium.utils.*
import me.kbrewster.blazeapi.client.fontRenderer
import net.minecraft.client.gui.Gui

class Notification(private val data: NotificationData) : Gui() {
    var percentComplete: Float = 0f
    var increasing: Boolean = true
    var complete: Boolean = false
    val facadePercent: Float
        get() = percentComplete.map(0f, 0.6f, 0f, 1f)

    fun render() {
        draw()
    }

    /**
     * Draws the notification at the current completion percentage.
     * Does not affect completion, for that use [tick].
     */
    private fun draw() {
        val fr = fontRenderer
        val borders = getBorder()
        val white = 0xffffffff.toInt()
        val currentAlpha = (255 * facadePercent).toInt()

        drawRect(borders.x1, borders.y1, borders.x2, borders.y2, data.backgroundColor.setAlpha(currentAlpha).rgb)

        fr.drawString(
                fr.trimStringToWidth(data.title, WIDTH - RIGHT_MARGINS),
                borders.x1 + HIGHLIGHT_BAR_MARGINS + HIGHLIGHT_BAR_WIDTH,
                borders.y1 + TOP_PADDING,
                white
        )
    }

    fun update() {
        val hovering = isInside(mouseX, mouseY)

        if (!hovering || increasing) {
            tick()
        }
    }

    private fun tick() {
        this.percentComplete = percentComplete.easeOut(
                if (increasing) 1f else 0f,
                0.01f, 16f
        ).clamp(0f, 1f)

        if (percentComplete == 1f && increasing) {
            increasing = false
        }

        if (percentComplete == 0f && !increasing) {
            complete = true
            data.callback?.invoke()
        }
    }

    fun isInside(x: Int, y: Int): Boolean {
        val border = getBorder()

        return x > border.x1
                && x < border.x2
                && y > border.y1
                && y < border.y2
    }

    private fun getBorder() = object {
        val x1 = scaledRes.scaledWidth - (WIDTH * facadePercent).toInt()
        val y1 = scaledRes.scaledHeight - HEIGHT - BOTTOM_MARGINS
        val x2 = scaledRes.scaledWidth
        val y2 = scaledRes.scaledHeight - BOTTOM_MARGINS
    }

    companion object {
        /**
         * Width of the notification body
         */
        const val WIDTH = 175

        /**
         * Height of the notification body
         */
        const val HEIGHT = 40

        /**
         * Margins between the bottom of the notification and the bottom of the screen
         */
        const val BOTTOM_MARGINS = 15

        /**
         * Margins between text or images (whichever is applicable) from the right of the screen
         */
        const val RIGHT_MARGINS = 5

        /**
         * Padding between the top of the notification and title text
         */
        const val TOP_PADDING = 5

        /**
         * Maximum number of lines descriptions can span.
         */
        const val MAX_DESCRIPTION_LINES = 4

        /**
         * Width of the highlight bar
         */
        const val HIGHLIGHT_BAR_WIDTH = 5

        /**
         * Margins between the highlight bar and the text next to it
         */
        const val HIGHLIGHT_BAR_MARGINS = 5

        /**
         * Spacing in pixels between each line
         */
        const val LINE_SPACING = 1
    }
}