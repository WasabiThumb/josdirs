package io.github.wasabithumb.josdirs.platform;

import io.github.wasabithumb.josdirs.OSDirs;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

@ApiStatus.Internal
public interface OSDirsService {

    /**
     * Provides the ID of this service, matching the name of the platform artifact it originates from.
     */
    @NotNull String id();

    /**
     * Creates a new OSDirs instance
     */
    @Contract("_ -> new")
    @NotNull
    OSDirs create(@NotNull Logger logger);

    /**
     * Returns true if this service should be preferred over other available META-INF,
     * based on compatibility with the current OS.
     */
    boolean isCompatible();

}
