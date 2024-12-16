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
