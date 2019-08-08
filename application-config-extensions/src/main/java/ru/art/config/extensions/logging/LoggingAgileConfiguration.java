/*
 *    Copyright 2019 ART
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package ru.art.config.extensions.logging;

import lombok.Getter;
import org.apache.logging.log4j.Level;
import ru.art.logging.LoggingModuleConfiguration.LoggingModuleDefaultConfiguration;
import static ru.art.config.extensions.ConfigExtensions.configString;
import static ru.art.config.extensions.logging.LoggingConfigKeys.LEVEL;
import static ru.art.config.extensions.logging.LoggingConfigKeys.LOGGING_SECTION_ID;
import static ru.art.core.extension.ExceptionExtensions.ifException;

@Getter
public class LoggingAgileConfiguration extends LoggingModuleDefaultConfiguration {
    private Level level;

    public LoggingAgileConfiguration() {
        refresh();
    }

    @Override
    public void refresh() {
        level = ifException(() -> Level.getLevel(configString(LOGGING_SECTION_ID, LEVEL).toUpperCase()), super.getLevel());
        super.refresh();
    }
}