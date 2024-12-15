package io.github.wasabithumb.josdirs.loader;

import io.github.wasabithumb.josdirs.util.IOUtil;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.file.Files;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class JARLibraryLoader implements LibraryLoader {

    private final File file;
    public JARLibraryLoader(@NotNull File file) {
        this.file = file;
    }

    @Override
    public void load(@NotNull String os, @NotNull String arch, @NotNull String fileName) throws IOException {
        File temp = Files.createTempDirectory("josdirs").toFile();
        if (!temp.isDirectory() && !temp.mkdirs())
            throw new IOException("Failed to create temporary directory @ " + temp.getAbsolutePath());
        temp.deleteOnExit();

        File lib = new File(temp, fileName);
        lib.deleteOnExit();

        try (FileOutputStream fos = new FileOutputStream(lib, false);
             ZipFile zf = new ZipFile(this.file)
        ) {
            final String path = "META-INF/natives/" + os + "/" + arch + "/" + fileName;
            ZipEntry ze = zf.getEntry(path);
            if (ze == null) {
                throw new IOException("Path \"" + path + "\" not found in archive \"" +
                        this.file.getAbsolutePath() + "\"");
            }
            try (InputStream is = zf.getInputStream(ze)) {
                IOUtil.pipe(is, fos);
            }
        }

        System.load(lib.getAbsolutePath());
    }

}
