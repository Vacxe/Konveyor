import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("maven-publish")
    id("signing")
    id("com.github.johnrengelman.shadow")
}

java {
    withJavadocJar()
    withSourcesJar()
}

tasks.withType<ShadowJar>().configureEach {
    archiveClassifier.set("")
}

val releaseMode: String? by project
val versionSuffix = when (releaseMode) {
    "RELEASE" -> ""
    else -> "-SNAPSHOT"
}

version = readVersion() + versionSuffix

fun readVersion(): String {
    return project.layout.projectDirectory.file("version").asFile.readText().trim()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifactId = "konveyor"
            groupId = "io.github.vacxe.konveyor"

            from(components["java"])

            pom {
                name.set("Konveyor")
                description.set("Random data class generator")
                url.set("https://github.com/vacxe/konveyor")
                licenses {
                    license {
                        name.set("The Apache Software License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("vacxe")
                        name.set("Konstantin Aksenov")
                        email.set("aksenov.kostya@gmail.com")
                    }
                }
                scm {
                    connection.set("scm:git:github.com/vacxe/konveyor.git")
                    developerConnection.set("scm:git:ssh://github.com/vacxe/konveyor.git")
                    url.set("https://github.com/vacxe/konveyor")
                }
            }
        }
        repositories {
            maven {
                credentials {
                    username = System.getenv("SONATYPE_USERNAME")
                    password = System.getenv("SONATYPE_PASSWORD")
                }
                name = "Sonatype"

                setUrl(when (releaseMode) {
                    "RELEASE" -> System.getenv("SONATYPE_RELEASES_URL")
                        ?: "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"

                    else -> System.getenv("SONATYPE_SNAPSHOTS_URL")
                        ?: "https://s01.oss.sonatype.org/content/repositories/snapshots/"
                })
            }
        }
    }
}

val passphrase: String? = System.getenv("GPG_PASSPHRASE")
val keyId: String? = System.getenv("GPG_KEY_ID")

if (!passphrase.isNullOrBlank() && !keyId.isNullOrBlank()) {
    project.configure<SigningExtension> {
        sign(publishing.publications)
    }

    project.extra.set("signing.keyId", keyId)
    project.extra.set("signing.password", passphrase)
    project.extra.set("signing.secretKeyRingFile", "${project.rootProject.rootDir}/buildsystem/secring.gpg")
}
