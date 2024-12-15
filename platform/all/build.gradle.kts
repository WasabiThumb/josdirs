
plugins {
    id("io.github.goooler.shadow") version "8.1.8"
}

description = "Includes all available platforms for jOSDirs (shaded)"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":platform:windows"))
    implementation(project(":platform:macos"))
    implementation(project(":platform:unix"))
}

tasks.javadocJar {
    enabled = false
}

tasks.sourcesJar {
    enabled = false
}

tasks.jar {
    enabled = false
}

tasks.shadowJar {
    archiveClassifier.set("")
    mergeServiceFiles()
}

artifacts {
    add("default", tasks.shadowJar)
}

tasks.build {
    dependsOn(tasks.shadowJar)
}