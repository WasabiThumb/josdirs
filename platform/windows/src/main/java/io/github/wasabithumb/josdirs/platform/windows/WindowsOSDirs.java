package io.github.wasabithumb.josdirs.platform.windows;

import io.github.wasabithumb.josdirs.AbstractOSDirs;
import io.github.wasabithumb.josdirs.path.OSPath;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public class WindowsOSDirs extends AbstractOSDirs {

    public WindowsOSDirs(@NotNull Logger logger) {
        super(logger);
    }

    @Override
    public @NotNull OSPath userData() {
        return null;
    }

    @Override
    public @NotNull OSPath userConfig() {
        return null;
    }

    @Override
    public @NotNull OSPath userCache() {
        return null;
    }

    @Override
    public @NotNull OSPath userHome() {
        return null;
    }

    @Override
    public @NotNull OSPath userDownloads() {
        return null;
    }

    @Override
    public @NotNull OSPath userDocuments() {
        return null;
    }

    @Override
    public @NotNull OSPath userDesktop() {
        return null;
    }

    @Override
    public @NotNull OSPath userMusic() {
        return null;
    }

    @Override
    public @NotNull OSPath userPictures() {
        return null;
    }

    @Override
    public @NotNull OSPath userVideos() {
        return null;
    }

}
