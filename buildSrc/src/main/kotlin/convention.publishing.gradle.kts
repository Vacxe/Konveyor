import java.net.URI

plugins {
    id("maven-publish")
    id("signing")
}

val releaseMode: String? by project
val versionSuffix = when (releaseMode) {
    "RELEASE" -> ""
    else -> "-SNAPSHOT"
}

group = "io.github.vacxe"
version = readVersion() + versionSuffix

configure<PublishingExtension> {
    publications {
        create<MavenPublication>("default") {
            groupId = project.group.toString()

            components.whenObjectAdded {
                if (this.name == "release") {
                    from(components["release"])
                }
            }

            pom {
                name.set(project.name)
                url.set("https://github.com/vacxe/konveyor")
                description.set("Random data class generator")

                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }

                scm {
                    url.set("https://github.com/KakaoCup/Kakao.git")
                    connection.set("scm:git:ssh://github.com/vacxe/konveyor")
                    developerConnection.set("scm:git:ssh://github.com/vacxe/konveyor")
                }
            }
        }
    }
    repositories {
        maven {
            name = "Local"
            setUrl("${project.rootDir}/build/repository")
        }
        maven {
            name = "OSSHR"
            credentials {
                username = System.getenv("SONATYPE_USERNAME")
                password = System.getenv("SONATYPE_PASSWORD")
            }
            url = URI.create(
                when (releaseMode) {
                    "RELEASE" -> System.getenv("SONATYPE_RELEASES_URL")
                        ?: "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"

                    else -> System.getenv("SONATYPE_SNAPSHOTS_URL")
                        ?: "https://s01.oss.sonatype.org/content/repositories/snapshots/"
                }
            )
        }
    }
}

val passphrase: String? = System.getenv("GPG_PASSPHRASE")

if (!passphrase.isNullOrBlank()) {
    project.configure<SigningExtension> {
        sign(publishing.publications.getByName("default"))
    }

    project.extra.set("signing.keyId", "0110979F")
    project.extra.set("signing.password", passphrase)
    project.extra.set("signing.secretKeyRingFile", "${project.rootProject.rootDir}/buildsystem/secring.gpg")
}

fun readVersion(): String {
    return project.layout.projectDirectory.file("version").asFile.readText().trim()
}
