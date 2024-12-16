
plugins {
    id("fr.stardustenterprises.rust.importer") version "3.2.5"
}

description = "UNIX platform for jOSDirs"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(project(":api"))
    compileOnly(project(":internals"))
    compileOnly("org.jetbrains:annotations:26.0.1")
    rust(project(":platform:unix:natives"))
}

rustImport {
    baseDir.set("/META-INF/natives")
    layout.set("hierarchical")
}

centralPortal {
    name = "${rootProject.name}-platform-unix"
    jarTask = tasks.jar
    sourcesJarTask = tasks.sourcesJar
    javadocJarTask = tasks.javadocJar
    pom {
        name = "jOSDirs Platform for UNIX"
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
