/*
 * ART
 *
 * Copyright 2020 ART
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.art.server.model;

import io.art.core.module.*;
import lombok.*;
import static io.art.core.checker.NullityChecker.*;
import static io.art.server.constants.ServerModuleConstants.ConfigurationKeys.*;

@Getter
@AllArgsConstructor
public class ServiceMethodConfiguration {
    private final boolean deactivated;

    public static ServiceMethodConfiguration from(ModuleConfigurationSource source) {
        boolean deactivated = orElse(source.getBool(DEACTIVATED_KEY), false);
        return new ServiceMethodConfiguration(deactivated);
    }
}