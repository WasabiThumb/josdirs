
plugins {
    id("fr.stardustenterprises.rust.wrapper")
}

val libName = "josdirs.dll"
rust {
    release.set(true)
    command.set("cargo")
    cargoInstallTargets.set(true)
    targets {
        create("windows-x86_64") {
            target = "x86_64-pc-windows-msvc"
            outputName = libName
        }
        create("windows-i686") {
            target = "i686-pc-windows-msvc"
            outputName = libName
        }
        create("windows-aarch64") {
            target = "aarch64-pc-windows-msvc"
            outputName = libName
        }
    }
}
