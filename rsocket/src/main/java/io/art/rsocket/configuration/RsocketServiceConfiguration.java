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

package io.art.rsocket.configuration;

import io.art.core.collection.*;
import io.art.core.source.*;
import lombok.*;
import static io.art.core.collection.ImmutableMap.*;
import static io.art.server.constants.ServerModuleConstants.ConfigurationKeys.*;
import static java.util.Optional.*;
import java.util.*;

@Getter
@RequiredArgsConstructor
public class RsocketServiceConfiguration {
    private ImmutableMap<String, RsocketMethodConfiguration> methods;
    private final RsocketServerConfiguration serverConfiguration;

    public static RsocketServiceConfiguration from(RsocketServerConfiguration serverConfiguration, ConfigurationSource source) {
        RsocketServiceConfiguration configuration = new RsocketServiceConfiguration(serverConfiguration);
        configuration.methods = ofNullable(source.getNestedMap(METHODS_KEY))
                .map(configurations -> configurations.entrySet()
                        .stream()
                        .collect(immutableMapCollector(Map.Entry::getKey, entry -> RsocketMethodConfiguration.from(configuration, entry.getValue()))))
                .orElse(emptyImmutableMap());
        return configuration;
    }
}
