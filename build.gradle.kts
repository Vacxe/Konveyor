plugins {
    kotlin("jvm") version "2.0.0"
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
