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

package ru.art.config.configuration;

import lombok.*;
import ru.art.config.constants.*;
import ru.art.core.module.*;
import static ru.art.config.constants.ConfigModuleConstants.*;
import static ru.art.config.constants.ConfigType.*;

public interface ConfigModuleConfiguration extends ModuleConfiguration {
    ConfigType getModuleConfigType();

    int getCacheUpdateIntervalSeconds();

    ConfigModuleDefaultConfiguration DEFAULT_CONFIGURATION = new ConfigModuleDefaultConfiguration();

	@Getter
	class ConfigModuleDefaultConfiguration implements ConfigModuleConfiguration {
        private final ConfigType moduleConfigType = YAML;
        private final int cacheUpdateIntervalSeconds = DEFAULT_CACHE_UPDATE_INTERVAL_SECONDS;
    }
}
