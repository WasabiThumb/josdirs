/*

   Copyright 2024 Wasabi Codes

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

*/
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
