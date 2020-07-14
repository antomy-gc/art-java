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

package io.art.config.extensions.logging;

import lombok.*;
import org.apache.logging.log4j.*;
import io.art.logging.LoggingModuleConfiguration.*;
import static io.art.config.extensions.ConfigExtensions.*;
import static io.art.config.extensions.logging.LoggingConfigKeys.*;
import static io.art.core.checker.EmptinessChecker.*;
import static io.art.core.constants.StringConstants.*;
import static io.art.core.extension.ExceptionExtensions.*;

@Getter
public class LoggingAgileConfiguration extends LoggingModuleDefaultConfiguration {
    private Level level;
    private boolean enabledColoredLogs;
    private boolean enabledAsynchronousLogging;

    public LoggingAgileConfiguration() {
        refresh();
    }

    @Override
    public void refresh() {
        enabledColoredLogs = configBoolean(LOGGING_SECTION_ID, ENABLED_COLORED_LOGS, super.isEnabledColoredLogs());
        enabledAsynchronousLogging = configBoolean(LOGGING_SECTION_ID, ENABLED_ASYNCHRONOUS_LOGGING, super.isEnabledAsynchronousLogging());
        String levelString = configString(LOGGING_SECTION_ID, LEVEL, EMPTY_STRING);
        if (isEmpty(levelString)) {
            level = super.getLevel();
            return;
        }
        level = ifException(() -> Level.getLevel(levelString.toUpperCase()), super.getLevel());
        super.refresh();
    }
}