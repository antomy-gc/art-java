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

import io.art.core.collection.*;
import io.art.core.source.*;
import lombok.*;
import reactor.core.scheduler.*;
import static io.art.core.checker.NullityChecker.*;
import static io.art.core.collection.ImmutableMap.*;
import static io.art.server.constants.ServerModuleConstants.ConfigurationKeys.*;
import static io.art.server.constants.ServerModuleConstants.Defaults.*;
import static java.util.Optional.*;
import java.util.*;

@Getter
@AllArgsConstructor
public class ServiceConfiguration {
    private final boolean deactivated;
    private final ImmutableMap<String, ServiceMethodConfiguration> methods;
    private final Scheduler scheduler;

    public static ServiceConfiguration from(ConfigurationSource source) {
        boolean deactivated = orElse(source.getBool(DEACTIVATED_KEY), false);
        Scheduler scheduler = DEFAULT_SERVICE_METHOD_SCHEDULER;
        ImmutableMap<String, ServiceMethodConfiguration> methods = ofNullable(source.getNestedMap(METHODS_KEY))
                .map(configurations -> configurations.entrySet()
                        .stream()
                        .collect(immutableMapCollector(Map.Entry::getKey, entry -> ServiceMethodConfiguration.from(entry.getValue()))))
                .orElse(emptyImmutableMap());
        return new ServiceConfiguration(deactivated, methods, scheduler);
    }
}
