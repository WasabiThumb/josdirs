package io.github.wasabithumb.josdirs.platform.windows.path;

import io.github.wasabithumb.josdirs.path.AbstractOSPath;
import io.github.wasabithumb.josdirs.platform.windows.WindowsOSDirs;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Internal
public class WindowsUserDataOSPath extends AbstractOSPath {

    private final WindowsOSDirs dirs;
    private final String extra;
    public WindowsUserDataOSPath(@NotNull WindowsOSDirs dirs, @NotNull String extra) {
        this.dirs = dirs;
        this.extra = extra;
    }

    public WindowsUserDataOSPath(@NotNull WindowsOSDirs dirs) {
        this(dirs, "");
    }

    @Override
    public @NotNull String toString() {
        return concat(
                '\\',
                this.roaming ? this.dirs.roamingAppData() : this.dirs.localAppData(),
                this.appAuthor != null ? this.appAuthor : "",
                this.appName != null ? this.appName : "",
                this.extra,
                this.appVersion != null ? this.appVersion : ""
        );
    }

}
