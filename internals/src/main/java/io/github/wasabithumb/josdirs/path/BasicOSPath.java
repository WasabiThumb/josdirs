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
package io.github.wasabithumb.josdirs.path;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * An implementation of OSPath that ignores the "roaming" flag, and concatenates the appAuthor, appName and appVersion fields
 * as indicated by the "flags" field.
 */
@ApiStatus.Internal
public class BasicOSPath extends AbstractOSPath {

    public static final int F_APP_AUTHOR  = 0b001;
    public static final int F_APP_NAME    = 0b010;
    public static final int F_APP_VERSION = 0b100;

    private final CharSequence base;
    private final char sep;
    private final int flags;

    public BasicOSPath(@NotNull CharSequence base, char sep, int flags) {
        this.base = base;
        this.sep = sep;
        this.flags = flags;
    }

    protected boolean check(int flag) {
        return (this.flags & flag) != 0;
    }

    @Override
    public @NotNull String toString() {
        return concat(
                this.sep,
                this.base,
                this.check(F_APP_AUTHOR) && this.appAuthor != null ? this.appAuthor : "",
                this.check(F_APP_NAME) && this.appName != null ? this.appName : "",
                this.check(F_APP_VERSION) && this.appVersion != null ? this.appVersion : ""
        );
    }

    @Override
    public @NotNull File toFile() {
        File file = new File(this.base.toString());
        if (this.check(F_APP_AUTHOR) && this.appAuthor != null) file = new File(file, this.appAuthor);
        if (this.check(F_APP_NAME) && this.appName != null) file = new File(file, this.appName);
        if (this.check(F_APP_VERSION) && this.appVersion != null) file = new File(file, this.appVersion);
        return file;
    }

}
