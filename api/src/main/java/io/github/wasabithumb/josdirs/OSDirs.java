package io.github.wasabithumb.josdirs;

import io.github.wasabithumb.josdirs.path.OSPath;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * A platform-specific provider of system directories.
 */
@ApiStatus.NonExtendable
public interface OSDirs {

    /**
     * Provides the user data directory.
     * <ul>
     *     <li>Windows: {@code C:\Users\USER\AppData\...}</li>
     *     <li>MacOS: {@code /Users/USER/Library/Application Support/...}</li>
     *     <li>Linux: {@code /home/USER/.local/share/...}</li>
     * </ul>
     * <strong>
     *     The above examples may differ from the return value of this method.
     *     This library reports paths exactly as given by the OS.
     * </strong>
     */
    @Contract("-> new")
    @NotNull OSPath userData();

    /**
     * Provides the user config directory.
     * <ul>
     *     <li>Windows: {@code C:\Users\USER\AppData\...}</li>
     *     <li>MacOS: {@code /Users/USER/Library/Preferences/...}</li>
     *     <li>Linux: {@code /home/USER/.config/...}</li>
     * </ul>
     * <strong>
     *     The above examples may differ from the return value of this method.
     *     This library reports paths exactly as given by the OS.
     * </strong>
     */
    @Contract("-> new")
    @NotNull OSPath userConfig();

    /**
     * Provides the user cache directory.
     * <ul>
     *     <li>Windows: {@code C:\Users\USER\AppData\...\Cache\...}</li>
     *     <li>MacOS: {@code /Users/USER/Library/Caches/...}</li>
     *     <li>Linux: {@code /home/USER/.cache/...}</li>
     * </ul>
     * <strong>
     *     The above examples may differ from the return value of this method.
     *     This library reports paths exactly as given by the OS.
     * </strong>
     */
    @Contract("-> new")
    @NotNull OSPath userCache();

    /**
     * Provides the user home directory.
     * <ul>
     *     <li>Windows: {@code C:\Users\USER\...}</li>
     *     <li>MacOS: {@code /Users/USER/...}</li>
     *     <li>Linux: {@code /home/USER/...}</li>
     * </ul>
     * <strong>
     *     The above examples may differ from the return value of this method.
     *     This library reports paths exactly as given by the OS.
     * </strong>
     */
    @Contract("-> new")
    @NotNull OSPath userHome();

    /**
     * Provides the user downloads directory.
     * <ul>
     *     <li>Windows: {@code C:\Users\USER\Downloads\...}</li>
     *     <li>MacOS: {@code /Users/USER/Downloads/...}</li>
     *     <li>Linux: {@code /home/USER/Downloads/...}</li>
     * </ul>
     * <strong>
     *     The above examples may differ from the return value of this method.
     *     This library reports paths exactly as given by the OS.
     * </strong>
     */
    @Contract("-> new")
    @NotNull OSPath userDownloads();

    /**
     * Provides the user documents directory.
     * <ul>
     *     <li>Windows: {@code C:\Users\USER\Documents\...}</li>
     *     <li>MacOS: {@code /Users/USER/Documents/...}</li>
     *     <li>Linux: {@code /home/USER/Documents/...}</li>
     * </ul>
     * <strong>
     *     The above examples may differ from the return value of this method.
     *     This library reports paths exactly as given by the OS.
     * </strong>
     */
    @Contract("-> new")
    @NotNull OSPath userDocuments();

    /**
     * Provides the user desktop directory.
     * <ul>
     *     <li>Windows: {@code C:\Users\USER\Desktop\...}</li>
     *     <li>MacOS: {@code /Users/USER/Desktop/...}</li>
     *     <li>Linux: {@code /home/USER/Desktop/...}</li>
     * </ul>
     * <strong>
     *     The above examples may differ from the return value of this method.
     *     This library reports paths exactly as given by the OS.
     * </strong>
     */
    @Contract("-> new")
    @NotNull OSPath userDesktop();

    /**
     * Provides the user music directory.
     * <ul>
     *     <li>Windows: {@code C:\Users\USER\Music\...}</li>
     *     <li>MacOS: {@code /Users/USER/Music/...}</li>
     *     <li>Linux: {@code /home/USER/Music/...}</li>
     * </ul>
     * <strong>
     *     The above examples may differ from the return value of this method.
     *     This library reports paths exactly as given by the OS.
     * </strong>
     */
    @Contract("-> new")
    @NotNull OSPath userMusic();

    /**
     * Provides the user pictures directory.
     * <ul>
     *     <li>Windows: {@code C:\Users\USER\Pictures\...}</li>
     *     <li>MacOS: {@code /Users/USER/Pictures/...}</li>
     *     <li>Linux: {@code /home/USER/Pictures/...}</li>
     * </ul>
     * <strong>
     *     The above examples may differ from the return value of this method.
     *     This library reports paths exactly as given by the OS.
     * </strong>
     */
    @Contract("-> new")
    @NotNull OSPath userPictures();

    /**
     * Provides the user videos directory.
     * <ul>
     *     <li>Windows: {@code C:\Users\USER\Videos\...}</li>
     *     <li>MacOS: {@code /Users/USER/Videos/...}</li>
     *     <li>Linux: {@code /home/USER/Videos/...}</li>
     * </ul>
     * <strong>
     *     The above examples may differ from the return value of this method.
     *     This library reports paths exactly as given by the OS.
     * </strong>
     */
    @Contract("-> new")
    @NotNull OSPath userVideos();

}
