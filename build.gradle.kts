import net.minecraftforge.gradle.user.tweakers.ClientTweaker
import net.minecraftforge.gradle.user.tweakers.TweakerExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.spongepowered.asm.gradle.plugins.MixinGradlePlugin

buildscript {
    repositories {
        maven(url = "http://files.minecraftforge.net/maven")
        maven(url = "http://repo.spongepowered.org/maven")
    }

    dependencies {
        classpath("net.minecraftforge.gradle:ForgeGradle:${Versions.forge_gradle}")
        classpath("org.spongepowered:mixingradle:${Versions.mixin_gradle}")
    }
}

group = "cc.hyperium"
version = "1.0-SNAPSHOT"

plugins {
    kotlin("jvm") version Versions.kotlin
    id("io.gitlab.arturbosch.detekt").version(Versions.detekt)
}

apply {
    plugin<ClientTweaker>()
    plugin<MixinGradlePlugin>()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

configure<TweakerExtension> {
    setSuppressVersionTest(true)

    version = "1.8.9"
    mappings = "stable_22"

    runDir = rootProject.file("run").path

    makeObfSourceJar = true
    setTweakClass("cc.hyperium.launch.HyperiumTweaker")
}

repositories {
    maven(url = "http://dl.bintray.com/kotlin/kotlin-eap")
    maven(url = "https://jitpack.io")
    maven(url = "https://repo.spongepowered.org/maven/")
    mavenCentral()
    jcenter()
}

val ktlintConfig by configurations.creating

applyStandardDependencies()

val packageSources by tasks.creating(Jar::class) {
    from(sourceSets["main"].allSource)
    classifier = "sources"
}

artifacts {
    add("archives", packageSources)
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

detekt {
    config = files("detekt-config.yml")
}