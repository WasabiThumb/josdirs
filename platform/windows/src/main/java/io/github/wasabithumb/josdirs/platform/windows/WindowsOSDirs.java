package io.github.wasabithumb.josdirs.platform.windows;

import io.github.wasabithumb.josdirs.AbstractOSDirs;
import io.github.wasabithumb.josdirs.loader.LibraryLoader;
import io.github.wasabithumb.josdirs.path.AbstractOSPath;
import io.github.wasabithumb.josdirs.path.BasicOSPath;
import io.github.wasabithumb.josdirs.path.OSPath;
import io.github.wasabithumb.josdirs.platform.windows.jni.JNI;
import io.github.wasabithumb.josdirs.platform.windows.path.WindowsUserDataOSPath;
import io.github.wasabithumb.josdirs.util.SystemArch;
import io.github.wasabithumb.josdirs.util.SystemUtil;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WindowsOSDirs extends AbstractOSDirs {

    protected final JNI j;
    protected final boolean hasJ;
    public WindowsOSDirs(@NotNull Logger logger) {
        super(logger);
        final SystemArch arch = SystemUtil.arch();
        if (arch == SystemArch.X86_64 || arch == SystemArch.I686) {
            logger.log(Level.FINE, "[windows] Loading native library");
            LibraryLoader.create().loadAssert(
                    "windows",
                    arch.name().toLowerCase(Locale.ROOT),
                    "josdirs.dll"
            );
            this.j = new JNI();
            this.hasJ = true;
        } else {
            logger.log(Level.FINE, "[windows] Unsupported architecture (" + arch.name() + "), using fallback");
            this.j = null;
            this.hasJ = false;
        }
    }

    //

    @ApiStatus.Internal
    public @NotNull String home() {
        String home;
        if (this.hasJ) {
            home = this.j.homeDir();
            if (home != null) return home;
        }
        home = System.getProperty("user.home");
        if (home != null) return home;
        return "C:/Users/Default";
    }

    @ApiStatus.Internal
    public @NotNull String localAppData() {
        if (this.hasJ) {
            String dir = this.j.localAppDataDir();
            if (dir != null) return dir;
        }
        return AbstractOSPath.concat('\\', this.home(), "AppData", "Local");
    }

    @ApiStatus.Internal
    public @NotNull String roamingAppData() {
        if (this.hasJ) {
            String dir = this.j.roamingAppDataDir();
            if (dir != null) return dir;
        }
        return AbstractOSPath.concat('\\', this.home(), "AppData", "Roaming");
    }

    private @NotNull OSPath nativeOrRelative(@NotNull Function<JNI, String> fn, @NotNull String fallback) {
        if (this.hasJ) {
            String dir = fn.apply(this.j);
            if (dir != null) return new BasicOSPath(dir, '\\', 0);
        }
        return new BasicOSPath(
                AbstractOSPath.concat('\\', this.home(), fallback),
                '\\',
                0
        );
    }

    //

    @Override
    public @NotNull OSPath userData() {
        return new WindowsUserDataOSPath(this);
    }

    @Override
    public @NotNull OSPath userConfig() {
        return this.userData();
    }

    @Override
    public @NotNull OSPath userCache() {
        return new WindowsUserDataOSPath(this, "Cache");
    }

    @Override
    public @NotNull OSPath userHome() {
        return new BasicOSPath(this.home(), '\\', 0);
    }

    @Override
    public @NotNull OSPath userDownloads() {
        return this.nativeOrRelative(JNI::downloadsDir, "Downloads");
    }

    @Override
    public @NotNull OSPath userDocuments() {
        return this.nativeOrRelative(JNI::documentsDir, "Documents");
    }

    @Override
    public @NotNull OSPath userDesktop() {
        return this.nativeOrRelative(JNI::desktopDir, "Desktop");
    }

    @Override
    public @NotNull OSPath userMusic() {
        return this.nativeOrRelative(JNI::musicDir, "Music");
    }

    @Override
    public @NotNull OSPath userPictures() {
        return this.nativeOrRelative(JNI::picturesDir, "Pictures");
    }

    @Override
    public @NotNull OSPath userVideos() {
        return this.nativeOrRelative(JNI::videosDir, "Videos");
    }

}
