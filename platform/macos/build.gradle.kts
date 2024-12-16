
description = "MacOS platform for jOSDirs"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(project(":api"))
    compileOnly(project(":internals"))
    compileOnly("org.jetbrains:annotations:26.0.1")
}

centralPortal {
    name = "${rootProject.name}-platform-macos"
    jarTask = tasks.jar
    sourcesJarTask = tasks.sourcesJar
    javadocJarTask = tasks.javadocJar
    pom {
        name = "jOSDirs Platform for MacOS"
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