package cc.hyperium.mixins.entity;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityLivingBase.class)
public abstract class MixinEntityLivingBase extends MixinEntity {

    /**
     * Prevents issue where Mouse Updates less frequently than it should in 1.8.9
     * MC-67665
     *
     * @param partialTicks
     * @param ci
     */
    @Inject(method = "getLook", at = @At("HEAD"), cancellable = true)
    private void getLook(float partialTicks, CallbackInfoReturnable<Vec3> ci) {
        EntityLivingBase base = (EntityLivingBase) (Object) this;
        if (base instanceof EntityPlayerSP) {
            ci.setReturnValue(super.getLook(partialTicks));
        }
    }

}