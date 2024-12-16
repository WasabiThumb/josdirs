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

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * Represents a parameterized OS path. Can be finalized with {@link #toString()}.
 */
public interface OSPath {

    /**
     * Sets the application name to include in the path. If the path does not accept an application name,
     * this is simply ignored. If the application name is null, the section of the path it would create is truncated.
     */
    @Contract("_ -> this")
    @NotNull
    OSPath appName(@Nullable String appName);

    /**
     * Sets the application author to include in the path. If the path does not accept an application author,
     * this is simply ignored. If the application author is null, the section of the path it would create is truncated.
     */
    @Contract("_ -> this")
    @NotNull
    OSPath appAuthor(@Nullable String appAuthor);

    /**
     * Sets the application version to include in the path. If the path does not accept an application version,
     * this is simply ignored. If the application version is null, the section of the path it would create is truncated.
     */
    @Contract("_ -> this")
    @NotNull
    OSPath appVersion(@Nullable String appVersion);

    /**
     * Sets the "roaming" flag to true. Ignored if not applicable to the current path.
     * This is used for config & data dirs on Windows.
     */
    @Contract("_ -> this")
    @NotNull
    OSPath roaming(boolean roaming);

    /**
     * Returns a newly created String that represents the value of this path at the time of invocation.
     */
    @Contract("-> new")
    @NotNull String toString();

    /** @see #toString() */
    default @NotNull File toFile() {
        return new File(this.toString());
    }

    /** @see #toString() */
    default @NotNull URI toURI() {
        return this.toFile().toURI();
    }

    /** @see #toString() */
    default @NotNull URL toURL() {
        try {
            return this.toURI().toURL();
        } catch (MalformedURLException e) {
            throw new AssertionError("File URL from stdlib is malformed", e);
        }
    }

    /** @see #toString() */
    default @NotNull Path toPath() {
        return FileSystems.getDefault().getPath(this.toString());
    }

}
