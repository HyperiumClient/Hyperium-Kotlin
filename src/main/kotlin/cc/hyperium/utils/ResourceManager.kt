package cc.hyperium.utils

import cc.hyperium.processes.services.AbstractService
import cc.hyperium.processes.services.Service
import net.minecraft.util.ResourceLocation
import org.kodein.di.Kodein

@Service
class ResourceManager(override val kodein: Kodein) : AbstractService(), MutableMap<String, ResourceLocation> by HashMap()
