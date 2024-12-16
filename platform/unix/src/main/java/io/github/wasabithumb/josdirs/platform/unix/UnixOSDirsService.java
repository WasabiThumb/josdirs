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
package io.github.wasabithumb.josdirs.platform.unix;

import io.github.wasabithumb.josdirs.platform.OSDirsService;
import io.github.wasabithumb.josdirs.util.SystemUtil;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public class UnixOSDirsService implements OSDirsService {

    @Override
    public @NotNull String id() {
        return "unix";
    }

    @Override
    public @NotNull UnixOSDirs create(@NotNull Logger logger) {
        return new UnixOSDirs(logger);
    }

    @Override
    public boolean isCompatible() {
        return SystemUtil.isUnix();
    }

}
