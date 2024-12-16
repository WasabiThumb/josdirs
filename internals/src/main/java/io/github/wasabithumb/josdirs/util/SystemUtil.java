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
package io.github.wasabithumb.josdirs.util;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public final class SystemUtil {

    private static final String OS_NAME;
    private static final String OS_ARCH;
    static {
        String name = System.getProperty("os.name");
        if (name == null) name = "";
        OS_NAME = name.toLowerCase(Locale.ROOT);

        String arch = System.getProperty("os.arch");
        if (arch == null) arch = "";
        OS_ARCH = arch.toLowerCase(Locale.ROOT);
    }

    private static final String S_MAC = "mac";
    private static final String S_DARWIN = "darwin";

    //

    public static boolean isMacOS() {
        // Find either "mac" or "darwin" in OS_NAME

        String cmp = null;
        int z = 0;
        char c;

        for (int i=0; i < OS_NAME.length(); i++) {
            c = OS_NAME.charAt(i);
            if (z == 0) {
                if (c == 'm') {
                    cmp = S_MAC;
                } else if (c == 'd') {
                    cmp = S_DARWIN;
                } else {
                    continue;
                }
                z = 1;
                continue;
            }
            if (c == cmp.charAt(z++)) {
                if (z == cmp.length()) return true;
            } else {
                z = 0;
            }
        }

        return false;
    }

    public static boolean isWindows() {
        // Find "win" but NOT "darwin" in OS_NAME

        int z = 0;
        char c;

        for (int i=0; i < OS_NAME.length(); i++) {
            c = OS_NAME.charAt(i);
            if (c == 'w') {
                if (z == 3) {
                    z = 0;
                } else {
                    z = 4;
                }
                continue;
            }
            if (c != S_DARWIN.charAt(z++)) {
                z = 0;
                continue;
            }
            if (z == 6) return true;
        }

        return false;
    }

    public static boolean isUnix() {
        // Alias for !isMacOS() && !isWindows()
        // Presumed to be a safe assumption
        return !isMacOS() && !isWindows();
    }

    public static @NotNull SystemArch arch() {
        switch (OS_ARCH) {
            case "x86":
            case "i686":
            case "i386":
                return SystemArch.I686;
            case "x86_64":
            case "amd64":
                return SystemArch.X86_64;
            case "aarch64":
                return SystemArch.AARCH64;
            default:
                return SystemArch.UNKNOWN;
        }
    }

}
