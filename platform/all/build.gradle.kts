
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

centralPortal {
    name = "${rootProject.name}-platform-all"
    jarTask = tasks.shadowJar
    pom {
        name = "jOSDirs Platform Aggregator"
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