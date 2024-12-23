plugins {
    kotlin("jvm") version "2.0.20"
}

group = "by.n3b"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.arrow-kt:arrow-core:1.2.4")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.11.3")
    testImplementation("org.assertj:assertj-core:3.26.3")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.11.3")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
