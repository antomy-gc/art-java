/*
 * ART Java
 *
 * Copyright 2019 ART
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

package io.art.tarantool.configuration;

import io.art.core.factory.*;
import io.art.core.module.*;
import io.art.core.source.*;
import io.art.tarantool.constants.TarantoolModuleConstants.*;
import io.art.tarantool.exception.*;
import io.art.tarantool.model.*;
import lombok.*;
import static io.art.core.factory.MapFactory.map;
import static io.art.tarantool.constants.TarantoolModuleConstants.*;
import static io.art.tarantool.constants.TarantoolModuleConstants.ExceptionMessages.*;
import static io.art.tarantool.constants.TarantoolModuleConstants.TarantoolInitializationMode.*;
import static io.art.tarantool.module.TarantoolModule.*;
import static java.text.MessageFormat.*;
import static java.util.Objects.*;
import java.util.*;

@Getter
public class TarantoolModuleConfiguration implements ModuleConfiguration {
    private final Map<String, TarantoolConfiguration> tarantoolConfigurations = map();
    private final long connectionTimeoutMillis = DEFAULT_TARANTOOL_CONNECTION_TIMEOUT;
    private final long probeConnectionTimeoutMillis = DEFAULT_TARANTOOL_PROBE_CONNECTION_TIMEOUT;
    private final boolean enableTracing = false;
    private final TarantoolLocalInstanceConfiguration localConfiguration = TarantoolLocalInstanceConfiguration.builder().build();
    private final TarantoolInitializationMode initializationMode = BOOTSTRAP;


    public static class Configurator implements ModuleConfigurator<TarantoolModuleConfiguration, Configurator> {
        @Override
        public Configurator from(ConfigurationSource source) {
            return this;
        }
    }

    static TarantoolEntityFieldsMapping fieldMapping(String instanceId, String entityName) {
        TarantoolConfiguration tarantoolConfiguration = getTarantoolConfiguration(instanceId, tarantoolModule().configuration().getTarantoolConfigurations());
        TarantoolEntityFieldsMapping entityFieldsMapping = tarantoolConfiguration
                .getEntityFieldsMappings()
                .get(entityName);
        if (isNull(entityFieldsMapping)) {
            throw new TarantoolConnectionException(format(ENTITY_FIELDS_MAPPING_IS_NULL, instanceId, entityName));
        }
        return entityFieldsMapping;
    }
}

