
description = "Non-API components for jOSDirs"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":api"))
    compileOnly("org.jetbrains:annotations:26.0.1")
}
