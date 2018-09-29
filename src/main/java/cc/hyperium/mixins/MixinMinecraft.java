package cc.hyperium.mixins;

import cc.hyperium.Hyperium;
import me.kbrewster.blazeapi.BlazeAPI;
import net.minecraft.client.Minecraft;
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
}
