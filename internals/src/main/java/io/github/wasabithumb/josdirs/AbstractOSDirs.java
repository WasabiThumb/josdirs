package io.github.wasabithumb.josdirs;

import io.github.wasabithumb.josdirs.path.OSPath;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Logger;

@ApiStatus.Internal
public abstract class AbstractOSDirs implements OSDirs {

    protected final Logger logger;
    public AbstractOSDirs(@NotNull Logger logger) {
        this.logger = logger;
    }

    @Override
    public @NotNull String toString() {
        try {
            return this.toString0();
        } catch (ReflectiveOperationException | SecurityException e) {
            throw new AssertionError("Unexpected reflection error", e);
        }
    }

    private @NotNull String toString0() throws ReflectiveOperationException, SecurityException {
        final Method[] methods = OSDirs.class.getDeclaredMethods();

        List<String> keys = new ArrayList<>(methods.length);
        List<String> values = new ArrayList<>(methods.length);
        int maxKeyLen = 0;
        int count = 0;

        String key;
        String value;
        for (Method m : methods) {
            if (m.getParameterCount() != 0) continue;
            if (!OSPath.class.isAssignableFrom(m.getReturnType())) continue;

            key = m.getName();
            value = ((OSPath) m.invoke(this)).toString();

            maxKeyLen = Math.max(maxKeyLen, key.length());
            keys.add(key);
            values.add(value);
            count++;
        }

        char[] pad = new char[maxKeyLen + 1];
        Arrays.fill(pad, ' ');

        Iterator<String> iKeys = keys.iterator();
        Iterator<String> iValues = values.iterator();
        StringBuilder ret = new StringBuilder();
        ret.append("OSDirs {\n");

        for (int i=0; i < count; i++) {
            key = iKeys.next();
            value = iValues.next();

            ret.append('\t')
                    .append(key)
                    .append(pad, 0, pad.length - key.length())
                    .append('=')
                    .append(' ')
                    .append(value)
                    .append('\n');
        }

        return ret.append('}')
                .toString();
    }

}
