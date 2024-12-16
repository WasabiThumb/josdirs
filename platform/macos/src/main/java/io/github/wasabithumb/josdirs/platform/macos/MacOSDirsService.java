package io.github.wasabithumb.josdirs.platform.macos;

import io.github.wasabithumb.josdirs.platform.OSDirsService;
import io.github.wasabithumb.josdirs.util.SystemUtil;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public class MacOSDirsService implements OSDirsService {

    @Override
    public @NotNull String id() {
        return "macos";
    }

    @Override
    public @NotNull MacOSDirs create(@NotNull Logger logger) {
        return new MacOSDirs(logger);
    }

    @Override
    public boolean isCompatible() {
        return SystemUtil.isMacOS();
    }

}
