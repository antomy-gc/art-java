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

package ru.art.logging;

import lombok.Getter;
import ru.art.core.module.Module;
import ru.art.core.module.ModuleState;
import ru.art.logging.LoggingModuleConfiguration.LoggingModuleDefaultConfiguration;
import static java.util.logging.LogManager.getLogManager;
import static org.slf4j.bridge.SLF4JBridgeHandler.install;
import static ru.art.core.context.Context.context;
import static ru.art.logging.LoggingModuleConstants.LOGGING_MODULE_ID;

@Getter
public class LoggingModule implements Module<LoggingModuleConfiguration, ModuleState> {
    static {
        getLogManager().reset();
        install();
    }

    @Getter
    private final LoggingModuleConfiguration defaultConfiguration = new LoggingModuleDefaultConfiguration();
    private final String id = LOGGING_MODULE_ID;

    public static LoggingModuleConfiguration loggingModule() {
        return context().getModule(LOGGING_MODULE_ID, new LoggingModule());
    }
}