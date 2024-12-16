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
package io.github.wasabithumb.josdirs.loader;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public interface LibraryLoader {

    static @NotNull LibraryLoader create() {
        // Find the caller class
        final String thisClassName = LibraryLoader.class.getName();
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        String callerClassName = null;
        for (int i=1; i < trace.length; i++) {
            String candidate = trace[i].getClassName();
            if (!candidate.equals(thisClassName) && !candidate.startsWith("java.lang.Thread")) {
                callerClassName = candidate;
                break;
            }
        }

        // Use an appropriate class to resolve code source
        Class<?> sourceClass = LibraryLoader.class;
        if (callerClassName != null) {
            try {
                sourceClass = Class.forName(callerClassName);
            } catch (ClassNotFoundException ignored) { }
        }

        // Find the code source
        File codeSource;
        try {
            codeSource = new File(sourceClass.getProtectionDomain().getCodeSource().getLocation().toURI());
        } catch (URISyntaxException e) {
            throw new AssertionError("Code source has invalid location", e);
        }

        // Assume the code source is a JAR
        if (codeSource.isFile()) {
            return new JARLibraryLoader(codeSource);
        } else {
            // TODO: The un-packaged class files never seem to be used in development; handle anyways?
            throw new AssertionError("Code source is not a file");
        }
    }

    //

    void load(@NotNull String os, @NotNull String arch, @NotNull String fileName) throws IOException;

    default void loadAssert(@NotNull String os, @NotNull String arch, @NotNull String fileName) {
        try {
            this.load(os, arch, fileName);
        } catch (IOException e) {
            throw new AssertionError("Unexpected IO error while loading library \"" + fileName + "\"", e);
        }
    }

}
