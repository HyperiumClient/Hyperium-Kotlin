package cc.hyperium.mixins;

import cc.hyperium.Hyperium;
import cc.hyperium.events.InitializationEvent;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;

@Mixin(Minecraft.class)
public class MixinMinecraft {
    @Inject(method = "startGame", at = @At("RETURN"))
    private void init(CallbackInfo ci) {
        InitializationEvent event = new InitializationEvent(new File("./"));
        Hyperium.INSTANCE.getEVENT_BUS().post(event);
    }
}
