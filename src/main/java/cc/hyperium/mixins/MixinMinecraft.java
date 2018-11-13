package cc.hyperium.mixins;

import cc.hyperium.Hyperium;
import me.kbrewster.blazeapi.BlazeAPI;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinMinecraft {

    @Inject(method = "startGame", at = @At("HEAD"))
    private void startGame(CallbackInfo ci) {
        BlazeAPI.getEventBus().register(Hyperium.INSTANCE);
    }

    /**
     * Fixes bug MC-68754 and MC-111254
     *
     * @param ci
     */
    @Inject(method = "toggleFullscreen", at = @At(value = "JUMP", target = "Lnet/minecraft/client/Minecraft;toggleFullscreen()V", shift = At.Shift.AFTER))
    private void toggleFullScreen(CallbackInfo ci) {
        Display.setResizable(false);
        Display.setResizable(true);
    }
}
