plugins {
    id("java")
}

group = "net.ttfl.code"
version = "1.0-SNAPSHOT"

subprojects {
    apply(plugin = "java")

    repositories {
        mavenCentral()
        mavenLocal()
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") {
            name = "spigotmc-repo"
        }
        maven("https://oss.sonatype.org/content/repositories/snapshots/") {
            name = "sonatype"
        }
    }
}

project(":common") {
    dependencies {
        // Add common dependencies here
    }
}

project(":spigot") {
    dependencies {
        implementation(project(":common"))
        compileOnly("org.spigotmc:spigot-api:1.20-R0.1-SNAPSHOT")
    }
}

project(":bungee") {
    dependencies {
        implementation(project(":common"))
        compileOnly("net.md-5:bungeecord-api:1.20-SNAPSHOT")
    }
}