package io.github.wasabithumb.josdirs.platform.windows;

import io.github.wasabithumb.josdirs.OSDirs;
import io.github.wasabithumb.josdirs.platform.OSDirsService;
import io.github.wasabithumb.josdirs.util.SystemUtil;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public class WindowsOSDirsService implements OSDirsService {

    @Override
    public @NotNull String id() {
        return "windows";
    }

    @Override
    public @NotNull OSDirs create(@NotNull Logger logger) {
        return new WindowsOSDirs(logger);
    }

    @Override
    public boolean isCompatible() {
        return SystemUtil.isWindows();
    }

}
