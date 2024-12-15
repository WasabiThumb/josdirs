import java.util.*

plugins {
    id("java-library")
    id("maven-publish")
    id("signing")
}

allprojects {
    group = "io.github.wasabithumb"
    version = "0.1.0"
}

description = "Reports OS-specific paths such as home & application data"

repositories {
    mavenCentral()
}

dependencies {
    api(project(":api"))
    implementation(project(":internals"))

    compileOnly("org.jetbrains:annotations:26.0.1")

    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // Test a different platform depending on the host
    val os = System.getProperty("os.name").lowercase(Locale.ROOT)
    if (os.contains("mac") || os.contains("darwin")) {
        testImplementation(project(":platform:macos"))
    } else if (os.contains("win")) {
        testImplementation(project(":platform:windows"))
    } else {
        testImplementation(project(":platform:unix"))
    }
}

tasks.test {
    useJUnitPlatform()
}

allprojects {
    if (name == "natives") return@allprojects

    listOf(
        "java-library",
        "maven-publish",
        "signing",
//        "net.thebugmc.gradle.sonatype-central-portal-publisher"
    ).forEach { pluginManager.apply(it) }

    val targetJavaVersion = 8
    java {
        val javaVersion = JavaVersion.toVersion(targetJavaVersion)
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion

        toolchain {
            languageVersion.set(JavaLanguageVersion.of(targetJavaVersion))
        }

        withSourcesJar()
        withJavadocJar()
    }

    tasks.compileJava {
        options.encoding = "UTF-8"
    }

    tasks.javadoc {
        (options as CoreJavadocOptions)
            .addBooleanOption("Xdoclint:none", true)
    }
}
