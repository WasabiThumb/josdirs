package io.github.wasabithumb.josdirs.platform.unix.xdg;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ApiStatus.Internal
public final class XDGUserDirs {

    private static final Pattern LINE_PATTERN = Pattern.compile("^XDG_([^_]+)_DIR\\s*=\\s*([\"'])(.+?)\\2$");

    private final File file;
    private final Logger logger;
    private final Map<String, Entry> data;
    private boolean init = false;
    public XDGUserDirs(@NotNull File file, @NotNull Logger logger) {
        this.file = file;
        this.logger = logger;
        this.data = new HashMap<>(8);
    }

    public @Nullable String get(@NotNull String home, @NotNull String key) {
        synchronized (this) {
            if (!this.init) {
                this.logger.log(Level.FINE, "[unix] Checking XDG user dirs config");
                try {
                    this.load();
                } catch (IOException e) {
                    this.logger.log(
                            Level.WARNING,
                            "[unix] Unexpected error while loading XDG user dirs config",
                            e
                    );
                    return null;
                }
                this.init = true;
            }
        }
        Entry e = this.data.get(key);
        if (e.relative && !home.isEmpty()) {
            if (home.charAt(home.length() - 1) == '/' && !e.value.isEmpty()) {
                return home + e.value.substring(1);
            } else {
                return home + e.value;
            }
        } else {
            return e.value;
        }
    }

    private void load() throws IOException {
        if (!this.file.isFile()) {
            this.logger.log(
                    Level.FINE,
                    "[unix] XDG user dirs config does not exist (checked " + this.file.getAbsolutePath() + ")"
            );
            return;
        }
        try (FileInputStream fis = new FileInputStream(this.file);
             InputStreamReader r = new InputStreamReader(fis, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(r)
        ) {
            String next;
            while ((next = br.readLine()) != null) this.load0(next);
        }
    }

    private void load0(@NotNull String line) {
        if (line.isEmpty()) return;
        if (line.charAt(0) == '#') return;
        Matcher m = LINE_PATTERN.matcher(line);
        if (!m.matches()) return;

        final String key = m.group(1).toUpperCase(Locale.ROOT);
        String value = m.group(3);
        boolean relative = false;

        if (value.isEmpty()) return;
        if (value.startsWith("$HOME/")) {
            value = value.substring(5);
            relative = true;
        }

        this.data.put(key, new Entry(value, relative));
    }

    //

    private static class Entry {

        final String value;
        final boolean relative;
        Entry(@NotNull String value, boolean relative) {
            this.value = value;
            this.relative = relative;
        }

    }

}
