import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

object Dependencies {
    const val blazeApi = "com.github.KevinPriv:BlazeAPI:${Versions.blazeApi}"

    const val kryonet = "com.github.FalseHonesty:kryonet:${Versions.kryonet}"
    const val reflections = "com.github.FalseHonesty:reflections:${Versions.reflections}"
    const val hyperiumNetworking = "com.github.FalseHonesty:Hyperium-Networking:${Versions.hyperiumNetworking}"
    const val kodein = "org.kodein.di:kodein-di-generic-jvm:${Versions.kodein}"

    const val kotlinx_coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinx_coroutines}"

    const val junit_api = "org.junit.jupiter:junit-jupiter-api:${Versions.junit}"
    const val junit_params = "org.junit.jupiter:junit-jupiter-params:${Versions.junit}"
    const val junit_engine = "org.junit.jupiter:junit-jupiter-engine:${Versions.junit}"

    const val mockk = "io.mockk:mockk:${Versions.mockk}"
    const val detekt = "io.gitlab.arturbosch.detekt:detekt-formatting:${Versions.detekt}"
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
        "compile"(Dependencies.kodein)

        "testCompile"(Dependencies.junit_api)
        "testCompile"(Dependencies.junit_params)
        "testCompile"(Dependencies.junit_engine)
        "testCompile"(Dependencies.mockk)
        "detektPlugins"(Dependencies.detekt)
    }
}
