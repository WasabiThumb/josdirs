package io.github.wasabithumb.josdirs.path;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ApiStatus.Internal
public abstract class AbstractOSPath implements OSPath {

    /**
     * Joins path parts with the specified platform path separator
     */
    public static @NotNull String concat(char sep, @NotNull CharSequence a, @NotNull CharSequence... extra) {
        final int al = a.length();
        int len = al;
        for (CharSequence s : extra) len += s.length() + 1;

        final StringBuilder sb = new StringBuilder(len);
        sb.append(a);

        boolean queueSep = al != 0 && a.charAt(al - 1) != sep;
        boolean block = false;
        for (CharSequence s : extra) {
            int sl = s.length();
            if (sl == 0) continue;
            char c;
            for (int i=0; i < sl; i++) {
                if (queueSep) {
                    sb.append(sep);
                    queueSep = false;
                    block = true;
                }
                c = s.charAt(i);
                if (c == sep) {
                    if (block) continue;
                    queueSep = true;
                    continue;
                }
                block = false;
                sb.append(c);
            }
            queueSep = true;
        }

        return sb.toString();
    }

    //

    protected String appName = null;
    protected String appAuthor = null;
    protected String appVersion = null;
    protected boolean roaming = false;

    //

    @Override
    public @NotNull AbstractOSPath appName(@Nullable String appName) {
        this.appName = appName;
        return this;
    }

    @Override
    public @NotNull AbstractOSPath appAuthor(@Nullable String appAuthor) {
        this.appAuthor = appAuthor;
        return this;
    }

    @Override
    public @NotNull AbstractOSPath appVersion(@Nullable String appVersion) {
        this.appVersion = appVersion;
        return this;
    }

    @Override
    public @NotNull AbstractOSPath roaming(boolean roaming) {
        this.roaming = roaming;
        return this;
    }

    @Override
    public abstract @NotNull String toString();

}
