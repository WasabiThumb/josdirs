
plugins {
    id("java-library")
    id("maven-publish")
    id("signing")
    id("net.thebugmc.gradle.sonatype-central-portal-publisher") version "1.2.4"
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
    val os = System.getProperty("os.name").lowercase()
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
        "net.thebugmc.gradle.sonatype-central-portal-publisher"
    ).forEach { pluginManager.apply(it) }

    val targetJavaVersion = 8
    java {
        val javaVersion = JavaVersion.toVersion(targetJavaVersion)
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion

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

centralPortal {
    name = "${rootProject.name}-core"
    jarTask = tasks.jar
    sourcesJarTask = tasks.sourcesJar
    javadocJarTask = tasks.javadocJar
    pom {
        name = "jOSDirs Core"
        description = project.description
        url = "https://github.com/WasabiThumb/josdirs"
        licenses {
            license {
                name = "The Apache License, Version 2.0"
                url = "http://www.apache.org/licenses/LICENSE-2.0.txt"
            }
        }
        developers {
            developer {
                id = "wasabithumb"
                email = "wasabithumbs@gmail.com"
                organization = "Wasabi Codes"
                organizationUrl = "https://wasabithumb.github.io/"
                timezone = "-5"
            }
        }
        scm {
            connection = "scm:git:git://github.com/WasabiThumb/josdirs.git"
            url = "https://github.com/WasabiThumb/josdirs"
        }
    }
}
