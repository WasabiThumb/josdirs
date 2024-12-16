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
package io.github.wasabithumb.josdirs;

import io.github.wasabithumb.josdirs.platform.OSDirsService;
import org.jetbrains.annotations.NotNull;

import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Entry point for jOSDirs
 */
public final class JOSDirs {

    private static final Logger LOGGER = Logger.getLogger("jOSDirs");
    private static String PLATFORM_ID = null;
    private static OSDirs INSTANCE = null;
    private static boolean INIT = false;

    public static @NotNull Logger logger() {
        return LOGGER;
    }

    /**
     * Returns the platform ID in use (windows, macos, unix).
     * @throws IllegalStateException No compatible platform implementation could be found.
     */
    public static @NotNull String platform() {
        synchronized (JOSDirs.class) {
            if (!INIT) reload();
            return PLATFORM_ID;
        }
    }

    /**
     * Returns the appropriate OSDirs instance for this platform.
     * @throws IllegalStateException No compatible platform implementation could be found.
     */
    public static @NotNull OSDirs osDirs() {
        synchronized (JOSDirs.class) {
            if (!INIT) reload();
            return INSTANCE;
        }
    }

    //

    private static void complete(@NotNull OSDirsService service) {
        PLATFORM_ID = service.id();
        INSTANCE = service.create(LOGGER);
        INIT = true;
    }

    private static void reload() throws IllegalStateException {
        final ServiceLoader<OSDirsService> loader = ServiceLoader.load(OSDirsService.class);
        final StringBuilder tried = new StringBuilder(21);
        LOGGER.log(Level.FINE, "Loading jOSDirs");

        for (OSDirsService next : loader) {
            LOGGER.log(Level.FINE, "Checking service: " + next.id());
            if (next.isCompatible()) {
                complete(next);
                return;
            }
            if (tried.length() > 0) tried.append(", ");
            tried.append(next.id());
        }

        String errMsg;
        if (tried.length() == 0) {
            errMsg = "No jOSDirs platforms are present on the classpath";
        } else {
            errMsg = "No compatible OSDirs instance found (got: " + tried + ")";
        }
        LOGGER.log(Level.SEVERE, errMsg);
        throw new IllegalStateException(errMsg);
    }

}
