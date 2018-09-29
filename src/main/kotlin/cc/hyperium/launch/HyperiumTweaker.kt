package cc.hyperium.launch

import net.minecraft.launchwrapper.ITweaker
import net.minecraft.launchwrapper.LaunchClassLoader
import org.spongepowered.asm.launch.MixinBootstrap
import org.spongepowered.asm.mixin.MixinEnvironment
import org.spongepowered.asm.mixin.Mixins
import java.io.File

class HyperiumTweaker : ITweaker {
    override fun acceptOptions(args: MutableList<String>?, gameDir: File?, assetsDir: File?, profile: String?) {}

    override fun getLaunchTarget(): String {
        return "net.minecraft.client.main.Main"
    }

    override fun injectIntoClassLoader(classLoader: LaunchClassLoader?) {
        MixinBootstrap.init()

        val environment = MixinEnvironment.getDefaultEnvironment()
        Mixins.addConfiguration("mixins.hyperium.json")
        environment.obfuscationContext = "notch"
        environment.side = MixinEnvironment.Side.CLIENT
    }

    override fun getLaunchArguments(): Array<String> {
        return arrayOf("--version", "1.8.9", "--accessToken", "gey")
    }
}