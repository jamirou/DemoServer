plugins {
    application
    kotlin("jvm") version "1.9.23"
}

group = "com.example"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    //KTOR DEPENDENCIES
    implementation("io.ktor:ktor-server-core:2.3.0")
    implementation("io.ktor:ktor-server-netty:2.3.0")
    //LOGGING
    implementation("ch.qos.logback:logback-classic:1.4.5")

}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}