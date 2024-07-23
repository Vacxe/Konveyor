plugins {
    kotlin("jvm")
    id("convention.publishing")
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("junit:junit:4.13.2")
}

kotlin {
    jvmToolchain(8)
}
