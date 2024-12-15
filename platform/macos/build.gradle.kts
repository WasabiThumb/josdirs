
description = "MacOS platform for jOSDirs"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(project(":api"))
    compileOnly(project(":internals"))
    compileOnly("org.jetbrains:annotations:26.0.1")
}
