package io.github.wasabithumb.josdirs.platform.unix;

import io.github.wasabithumb.josdirs.AbstractOSDirs;
import io.github.wasabithumb.josdirs.loader.LibraryLoader;
import io.github.wasabithumb.josdirs.path.AbstractOSPath;
import io.github.wasabithumb.josdirs.path.BasicOSPath;
import io.github.wasabithumb.josdirs.path.OSPath;
import io.github.wasabithumb.josdirs.platform.unix.jni.JNI;
import io.github.wasabithumb.josdirs.platform.unix.xdg.XDGUserDirs;
import io.github.wasabithumb.josdirs.util.SystemArch;
import io.github.wasabithumb.josdirs.util.SystemUtil;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.FileSystems;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UnixOSDirs extends AbstractOSDirs {

    protected final Supplier<String> home;
    protected final Map<String, String> env;
    protected final XDGUserDirs xdg;

    UnixOSDirs(@NotNull Logger logger) {
        super(logger);
        final SystemArch arch = SystemUtil.arch();
        Supplier<String> home;
        if (arch != SystemArch.UNKNOWN) {
            final String archName = arch.name().toLowerCase(Locale.ROOT);
            logger.log(Level.FINE, "[unix] Loading natives (" + archName + ")");
            LibraryLoader.create().loadAssert(
                    "linux",
                    archName,
                    "libjosdirs.so"
            );
            final JNI jni = new JNI();
            home = jni::homePath;
        } else {
            logger.log(Level.FINE, "[unix] Unknown CPU architecture, using fallback system");
            final String[] path = new String[1];
            path[0] = System.getProperty("user.home");
            if (path[0] == null)
                path[0] = FileSystems.getDefault().getPath("~").toAbsolutePath().toString();
            home = () -> path[0];
        }
        this.home = home;
        this.env = System.getenv();
        this.xdg = new XDGUserDirs(new File(this.userConfig().toFile(), "user-dirs.dirs"), logger);
    }

    protected @NotNull String rel(@NotNull CharSequence path) {
        return AbstractOSPath.concat('/', this.home.get(), path);
    }

    protected @NotNull OSPath xdg(@NotNull String key, @NotNull String defaultName) {
        String path = this.xdg.get(this.home.get(), key); // XDG_key_DIR in ~/.config/user-dirs.dirs
        if (path == null) {
            path = this.rel(defaultName);
        }
        return new BasicOSPath(path, '/', 0);
    }

    @Override
    public @NotNull OSPath userData() {
        String path = this.env.get("XDG_DATA_HOME");
        if (path == null) {
            path = this.rel(".local/share");
        }
        return new BasicOSPath(path, '/', BasicOSPath.F_APP_NAME | BasicOSPath.F_APP_VERSION);
    }

    @Override
    public @NotNull OSPath userConfig() {
        String path = this.env.get("XDG_CONFIG_HOME");
        if (path == null) {
            path = this.rel(".config");
        }
        return new BasicOSPath(path, '/', BasicOSPath.F_APP_NAME | BasicOSPath.F_APP_VERSION);
    }

    @Override
    public @NotNull OSPath userCache() {
        String path = this.env.get("XDG_CACHE_HOME");
        if (path == null) {
            path = this.rel(".cache");
        }
        return new BasicOSPath(path, '/', BasicOSPath.F_APP_NAME | BasicOSPath.F_APP_VERSION);
    }

    @Override
    public @NotNull OSPath userHome() {
        return new BasicOSPath(this.home.get(), '/', 0);
    }

    @Override
    public @NotNull OSPath userDownloads() {
        return this.xdg("DOWNLOAD", "Downloads");
    }

    @Override
    public @NotNull OSPath userDocuments() {
        return this.xdg("DOCUMENTS", "Documents");
    }

    @Override
    public @NotNull OSPath userDesktop() {
        return this.xdg("DESKTOP", "Desktop");
    }

    @Override
    public @NotNull OSPath userMusic() {
        return this.xdg("MUSIC", "Music");
    }

    @Override
    public @NotNull OSPath userPictures() {
        return this.xdg("PICTURES", "Pictures");
    }

    @Override
    public @NotNull OSPath userVideos() {
        return this.xdg("VIDEOS", "Videos");
    }

}
