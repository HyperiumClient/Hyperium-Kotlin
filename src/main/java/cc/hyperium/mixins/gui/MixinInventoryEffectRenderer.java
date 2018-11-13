package cc.hyperium.mixins.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.inventory.Container;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryEffectRenderer.class)
public abstract class MixinInventoryEffectRenderer extends GuiContainer {

    @Shadow
    private boolean hasActivePotionEffects;

    public MixinInventoryEffectRenderer(Container inventorySlotsIn) {
        super(inventorySlotsIn);
    }

    /**
     * Removes the inventory going to the left once potion effects have worn out
     *
     * @author Kevin
     */
    @Inject(method = "updateActivePotionEffects", at = @At("HEAD"))
    private void updateActivePotionEffects(CallbackInfo ci) {
        // TODO: Once config system is complete we can cancel if they want status effects to move the inventory
        if (false) {
            this.hasActivePotionEffects = !Minecraft.getMinecraft().thePlayer.getActivePotionEffects().isEmpty();
            this.guiLeft = (this.width - this.xSize) / 2;
        }
    }

}
