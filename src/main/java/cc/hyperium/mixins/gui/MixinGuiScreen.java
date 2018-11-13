package cc.hyperium.mixins.gui;

import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiScreen.class)
public abstract class MixinGuiScreen {

    @Inject(method = "handleKeyboardInput", at = @At("HEAD"))
    private void handleKeyboardInput(CallbackInfo ci) {
        // TODO: Fix MC-2781
    }
}
