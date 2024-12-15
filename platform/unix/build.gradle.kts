
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
