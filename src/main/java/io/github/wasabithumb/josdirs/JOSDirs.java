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

    private static String PLATFORM_ID = null;
    private static OSDirs INSTANCE = null;
    private static boolean INIT = false;

    public static @NotNull Logger logger() {
        return Logger.getLogger("jOSDirs");
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

    private static void complete(@NotNull OSDirsService service, @NotNull Logger logger) {
        PLATFORM_ID = service.id();
        INSTANCE = service.create(logger);
        INIT = true;
    }

    private static void reload() throws IllegalStateException {
        final Logger logger = logger();
        final ServiceLoader<OSDirsService> loader = ServiceLoader.load(OSDirsService.class);
        final StringBuilder tried = new StringBuilder(21);
        logger.log(Level.FINE, "Loading jOSDirs");

        for (OSDirsService next : loader) {
            logger.log(Level.FINE, "Checking service: " + next.id());
            if (next.isCompatible()) {
                complete(next, logger);
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
        logger.log(Level.SEVERE, errMsg);
        throw new IllegalStateException(errMsg);
    }

}
