import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

object Dependencies {
    const val blazeApi = "com.github.KevinPriv:BlazeAPI:${Versions.blazeApi}"

    const val kryonet = "com.github.FalseHonesty:kryonet:${Versions.kryonet}"
    const val reflections = "com.github.FalseHonesty:reflections:${Versions.reflections}"
    const val hyperiumNetworking = "com.github.FalseHonesty:Hyperium-Networking:${Versions.hyperiumNetworking}"

    const val kotlinx_coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinx_coroutines}"
}

fun Project.applyStandardDependencies() {

    dependencies {
        "compile"(kotlin("stdlib-jdk8"))
        "compile"(kotlin("reflect"))
        "compile"(Dependencies.kotlinx_coroutines)

        "compile"(Dependencies.blazeApi)

        "compile"(Dependencies.kryonet)
        "compile"(Dependencies.reflections)
        "compile"(Dependencies.hyperiumNetworking)
    }
}