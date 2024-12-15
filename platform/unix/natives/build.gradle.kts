
plugins {
    id("fr.stardustenterprises.rust.wrapper")
}

val libName = "libjosdirs.so"
rust {
    release.set(true)
    command.set("cargo")
    cargoInstallTargets.set(true)
    targets {
        create("linux-x86_64") {
            target = "x86_64-unknown-linux-gnu"
            outputName = libName
        }
        create("linux-i686") {
            target = "i686-unknown-linux-gnu"
            outputName = libName
        }
        create("linux-aarch64") {
            target = "aarch64-unknown-linux-gnu"
            outputName = libName
        }
        /*
        create("linux-armeabi") {
            target = "arm-unknown-linux-gnueabi"
            outputName = libName
        }
        */
        /*
        create("linux-ppc64") {
            target = "powerpc64-unknown-linux-gnu"
            outputName = libName
        }
         */
    }
}
