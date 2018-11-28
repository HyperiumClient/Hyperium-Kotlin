package cc.hyperium.launch

import me.kbrewster.blazeapi.internal.launch.BlazeTweaker
import net.minecraft.launchwrapper.LaunchClassLoader
import org.spongepowered.asm.mixin.Mixins

class HyperiumTweaker : BlazeTweaker() {
    override fun injectIntoClassLoader(cl: LaunchClassLoader) {
        super.injectIntoClassLoader(cl)

        Mixins.addConfiguration("mixins.hyperium.json")
    }
}
