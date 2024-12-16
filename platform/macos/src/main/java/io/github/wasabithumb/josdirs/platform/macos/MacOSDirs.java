/*

   Copyright 2024 Wasabi Codes

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

*/
package io.github.wasabithumb.josdirs.platform.macos;

import io.github.wasabithumb.josdirs.AbstractOSDirs;
import io.github.wasabithumb.josdirs.path.AbstractOSPath;
import io.github.wasabithumb.josdirs.path.BasicOSPath;
import io.github.wasabithumb.josdirs.path.OSPath;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public class MacOSDirs extends AbstractOSDirs {

    protected final String home;
    public MacOSDirs(@NotNull Logger logger) {
        super(logger);
        String home = System.getProperty("user.home");
        if (home == null)
            throw new AssertionError("Unable to locate user home");
        this.home = home;
    }

    private @NotNull OSPath relative(@NotNull String rel, int flags) {
        return new BasicOSPath(
                AbstractOSPath.concat('/', this.home, rel),
                '/',
                flags
        );
    }

    private @NotNull OSPath relative(@NotNull String rel) {
        return this.relative(rel, 0);
    }

    @Override
    public @NotNull OSPath userData() {
        return this.relative(
                "Library/Application Support",
                BasicOSPath.F_APP_NAME | BasicOSPath.F_APP_VERSION
        );
    }

    @Override
    public @NotNull OSPath userConfig() {
        return this.relative(
                "Library/Preferences",
                BasicOSPath.F_APP_NAME | BasicOSPath.F_APP_VERSION
        );
    }

    @Override
    public @NotNull OSPath userCache() {
        return this.relative(
                "Library/Caches",
                BasicOSPath.F_APP_NAME | BasicOSPath.F_APP_VERSION
        );
    }

    @Override
    public @NotNull OSPath userHome() {
        return new BasicOSPath(this.home, '/', 0);
    }

    @Override
    public @NotNull OSPath userDownloads() {
        return this.relative("Downloads");
    }

    @Override
    public @NotNull OSPath userDocuments() {
        return this.relative("Documents");
    }

    @Override
    public @NotNull OSPath userDesktop() {
        return this.relative("Desktop");
    }

    @Override
    public @NotNull OSPath userMusic() {
        return this.relative("Music");
    }

    @Override
    public @NotNull OSPath userPictures() {
        return this.relative("Pictures");
    }

    @Override
    public @NotNull OSPath userVideos() {
        return this.relative("Movies");
    }

}
