package cc.hyperium.mixins.renderer

import net.minecraft.client.renderer.entity.RenderManager
import net.minecraft.client.renderer.entity.RenderPlayer
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.gen.Accessor

@Mixin(RenderManager::class)
interface MixinRenderManager {

    @Accessor
    fun getRenderPosX(): Double

    @Accessor
    fun getRenderPosY(): Double

    @Accessor
    fun getRenderPosZ(): Double

    @Accessor
    fun getSkinMap(): Map<String, RenderPlayer>

}

val RenderManager.mixinType: MixinRenderManager
    get() = this as MixinRenderManager