package cc.hyperium.utils

import net.minecraft.util.ResourceLocation

class ResourceManager : HashMap<String, ResourceLocation>() {
    fun get(k: String, def: ResourceLocation?) : ResourceLocation {
        return computeIfAbsent(k){ def!! }
    }
}