package io.github.wasabithumb.josdirs.platform.unix;

import io.github.wasabithumb.josdirs.platform.OSDirsService;
import io.github.wasabithumb.josdirs.util.SystemUtil;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public class UnixOSDirsService implements OSDirsService {

    @Override
    public @NotNull String id() {
        return "unix";
    }

    @Override
    public @NotNull UnixOSDirs create(@NotNull Logger logger) {
        return new UnixOSDirs(logger);
    }

    @Override
    public boolean isCompatible() {
        return SystemUtil.isUnix();
    }

}
