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
package io.github.wasabithumb.josdirs.platform;

import io.github.wasabithumb.josdirs.OSDirs;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

@ApiStatus.Internal
public interface OSDirsService {

    /**
     * Provides the ID of this service, matching the name of the platform artifact it originates from.
     */
    @NotNull String id();

    /**
     * Creates a new OSDirs instance
     */
    @Contract("_ -> new")
    @NotNull
    OSDirs create(@NotNull Logger logger);

    /**
     * Returns true if this service should be preferred over other available services,
     * based on compatibility with the current OS.
     */
    boolean isCompatible();

}
