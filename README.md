# jOSDirs
A modular Java 8+ library for reporting OS-specific paths. Similar to
[harawata/appdirs](https://github.com/harawata/appdirs) with some key differences.

- 🪶 No dependency on JNA; native calls are done through a Rust bridge
  - Supports ``x86`` (64 and 32-bit) and ``aarch64``. Other systems use a less rigorous fallback provider.
- 🌐 XDG compliant (checks [``~/.config/user-dirs.dirs``](https://wiki.archlinux.org/title/XDG_user_directories) on Linux)
- 🔣 Support for wide chars (non-ASCII)
- 📅 Uses [modern Win32 APIs](https://learn.microsoft.com/en-us/windows/win32/api/shlobj_core/nf-shlobj_core-shgetknownfolderpath) (no support for XP & older)

## Reference
The entry point of jOSDirs is ``JOSDirs.osDirs()``, which provides an ``OSDirs`` instance.
Available methods are:
- userCache
- userConfig
- userData
- userDesktop
- userDocuments
- userDownloads
- userHome
- userMusic
- userPictures
- userVideos

Each method returns an ``OSPath``, which can be mutated with builder syntax and consumed with
``toString()`` or ``toFile()``.

## Example
```java
String dataLocal = JOSDirs.osDirs()
        .userData()
        .toString();
// Windows: C:\Users\USER\AppData\Local
// MacOS: /Users/USER/Library/Application Support
// Linux: /home/USER/.local/share

String dataRoaming = JOSDirs.osDirs()
        .userData()
        .roaming(true)
        .appAuthor("TeamFoo")
        .appName("Bar")
        .appVersion("0.1.0")
        .toString();
// Windows: C:\Users\USER\AppData\Roaming\TeamFoo\Bar\0.1.0
// MacOS: /Users/USER/Library/Application Support/Bar/0.1.0
// Linux: /home/USER/.local/share/Bar/0.1.0
```

## Using
You only need to require the platforms you wish to use. Available platforms are:
- ``josdirs-platform-windows`` (Windows Vista or newer)
- ``josdirs-platform-unix`` (Linux / Any modern 'nix)
- ``josdirs-platform-macos`` (MacOS / OSX)

The ``josdirs-platform-all`` artifact is also available, including all the platforms listed above.
**If you are going to shade jOSDirs, see [this](#note-about-shading).**

### Gradle
#### Groovy DSL
```groovy
dependencies {
    implementation 'io.github.wasabithumb:josdirs-core:0.1.0'
    implementation 'io.github.wasabithumb:josdirs-platform-all:0.1.0'
}
```

#### Kotlin DSL
```kotlin
dependencies {
    implementation("io.github.wasabithumb:josdirs-core:0.1.0")
    implementation("io.github.wasabithumb:josdirs-platform-all:0.1.0")
}
```

### Maven
```xml
<dependencies>
    <dependency>
        <groupId>io.github.wasabithumb</groupId>
        <artifactId>josdirs-core</artifactId>
        <version>0.1.0</version>
        <scope>compile</scope>
    </dependency>
    <dependency>
        <groupId>io.github.wasabithumb</groupId>
        <artifactId>josdirs-platform-all</artifactId>
        <version>0.1.0</version>
        <scope>compile</scope>
    </dependency>
</dependencies>
```

### Note about shading
If you are using more than 1 platform, but NOT using ``platform-all``, you must tell the shade plugin to merge the
service metadata.

#### Gradle
```kotlin
tasks.shadowJar {
    mergeServiceFiles()
}
```

#### Maven
Use the [ServicesResourceTransformer](https://maven.apache.org/plugins/maven-shade-plugin/examples/resource-transformers.html#ServicesResourceTransformer).
