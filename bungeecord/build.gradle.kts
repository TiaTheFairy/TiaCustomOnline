plugins {
    id("java")
}

group = "net.ttfl.code"

repositories {
    mavenCentral()
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
}

dependencies {
    compileOnly("net.md-5:bungeecord-api:1.16-R0.4")
}