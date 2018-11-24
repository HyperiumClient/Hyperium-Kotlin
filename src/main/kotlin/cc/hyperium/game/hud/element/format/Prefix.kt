package cc.hyperium.game.hud.element.format

import cc.hyperium.game.hud.ColorScheme

enum class Prefix(val format: (String, Any, ColorScheme) -> String) {

    COLON({ name, value, scheme ->
        val (primary, secondary, tertiary) = scheme
        "$primary$name$secondary: $tertiary$value"
    }),
    ARROW({ name, value, scheme ->
        val (primary, secondary, tertiary) = scheme
        "$primary$name$secondary> $tertiary$value"
    }),
    BRACKETS({ name, value, scheme ->
        val (primary, secondary, tertiary) = scheme
        "$secondary[$primary$name$secondary] $tertiary$value"
    }),
}
