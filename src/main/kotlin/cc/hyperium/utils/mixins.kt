package cc.hyperium.utils

import cc.hyperium.mixins.renderer.MixinRenderManager
import net.minecraft.client.renderer.entity.RenderManager

val RenderManager.mixinType: MixinRenderManager
    get() = this as MixinRenderManager