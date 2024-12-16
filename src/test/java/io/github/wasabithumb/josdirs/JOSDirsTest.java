package io.github.wasabithumb.josdirs;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class JOSDirsTest {

    @BeforeAll
    static void setup() {
        final Logger logger = JOSDirs.logger();
        logger.setLevel(Level.FINER);

        final ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.FINER);
        logger.addHandler(handler);
    }

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