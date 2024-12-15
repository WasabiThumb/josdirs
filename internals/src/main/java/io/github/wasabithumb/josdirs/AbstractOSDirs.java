package io.github.wasabithumb.josdirs;

import io.github.wasabithumb.josdirs.path.OSPath;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.nio.CharBuffer;
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
        final int max = methods.length;

        final String[] keys = new String[max];
        final String[] values = new String[max];
        int maxKeyLen = 0;
        int totalValueLen = 0;
        int count = 0;

        String key;
        OSPath tmp;
        String value;
        String value2;
        for (Method m : methods) {
            if (m.getParameterCount() != 0) continue;
            if (!OSPath.class.isAssignableFrom(m.getReturnType())) continue;

            key = m.getName();
            tmp = (OSPath) m.invoke(this);

            if (tmp == null) {
                value = "null";
            } else {
                value = tmp.roaming(false).toString();
                value2 = tmp.roaming(true).toString();
                if (!value2.equals(value)) {
                    value += " OR " + value2;
                }
            }

            maxKeyLen = Math.max(maxKeyLen, key.length());
            totalValueLen += value.length();

            keys[count] = key;
            values[count] = value;
            count++;
        }

        final Integer[] indices = new Integer[count];
        for (int i=0; i < count; i++) indices[i] = i;
        Arrays.sort(indices, Comparator.comparing((Integer i) -> keys[i]));

        final CharBuffer ret = CharBuffer.allocate(10 + (maxKeyLen + 5) * count + totalValueLen);
        ret.put("OSDirs {\n");

        for (Integer index : indices) {
            key = keys[index];
            value = values[index];

            ret.put('\t')
                    .put(key)
                    .put(' ');

            for (int i=0; i < maxKeyLen - key.length(); i++)
                ret.put(' ');

            ret.put('=')
                    .put(' ')
                    .put(value)
                    .put('\n');
        }

        ret.put('}');
        ret.position(0);
        return ret.toString();
    }

}
