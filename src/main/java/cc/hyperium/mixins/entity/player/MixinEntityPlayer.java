package cc.hyperium.mixins.entity.player;

import cc.hyperium.game.player.HyPlayer;
import cc.hyperium.imixins.entity.player.IMixinEntityPlayer;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayer.class)
public abstract class MixinEntityPlayer implements IMixinEntityPlayer {
    private HyPlayer hyPlayer;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void init(World worldIn, GameProfile gameProfileIn, CallbackInfo ci) {
        hyPlayer = new HyPlayer((EntityPlayer) (Object) this);
    }

    @Override
    public HyPlayer getHyPlayer() {
        return hyPlayer;
    }
}
