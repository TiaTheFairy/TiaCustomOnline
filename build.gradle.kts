plugins {
    id("java")
}

group = "net.ttfl.code"
version = "1.0"


repositories {
    mavenCentral()
    mavenLocal()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") {
        name = "spigotmc-repo"
    }
    maven("https://oss.sonatype.org/content/repositories/snapshots/") {
        name = "sonatype"
    }
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
}

dependencies {
    compileOnly("net.md-5:bungeecord-api:1.20-R0.1")
    compileOnly("io.papermc.paper:paper-api:1.20-R0.1-SNAPSHOT")
    compileOnly("me.clip:placeholderapi:2.11.6")
    implementation ("com.ezylang:EvalEx:3.3.0")
}

tasks.named<Jar>("jar") {
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
}