package io.github.wasabithumb.josdirs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JOSDirsTest {

    @Test
    void platform() {
        String platform = JOSDirs.platform();
        assertTrue(platform.equals("windows") || platform.equals("macos") || platform.equals("unix"));
        System.out.println("Using platform: " + platform);
    }

    @Test
    void osDirs() {
        OSDirs instance = JOSDirs.osDirs();
        System.out.println(instance);
    }

}