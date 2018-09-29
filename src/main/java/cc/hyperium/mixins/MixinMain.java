package cc.hyperium.mixins;

import cc.hyperium.Hyperium;
import net.minecraft.client.main.Main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

@Mixin(Main.class)
public class MixinMain {
    @Inject(method = "main", at = @At("HEAD"))
    private static void doMain(String[] p_main_0_, CallbackInfo ci) {
        System.out.println(Arrays.asList(p_main_0_));
        Hyperium.INSTANCE.getREFLECTIONS().expandSuperTypes();
    }
}
