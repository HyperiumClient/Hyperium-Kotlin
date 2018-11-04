package cc.hyperium.game.mods.hypixel

import cc.hyperium.processes.services.game.mod.Mod
import me.kbrewster.blazeapi.events.ChatReceivedEvent
import me.kbrewster.eventbus.Subscribe
import org.kodein.di.Kodein

class AutoGG(override val kodein: Kodein) : Mod() {
    @Subscribe
    fun onChat(event: ChatReceivedEvent) {
        // TODO: AutoGG
    }
}