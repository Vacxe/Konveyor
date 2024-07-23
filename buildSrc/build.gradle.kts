plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation(libs.org.jetbrains.kotlin.kotlinGradlePlugin)
    implementation("gradle.plugin.com.github.johnrengelman:shadow:7.1.2")
}
