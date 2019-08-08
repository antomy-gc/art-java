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

package ru.art.reactive.service.module;

import lombok.Getter;
import ru.art.core.module.Module;
import ru.art.core.module.ModuleState;
import ru.art.reactive.service.configuration.ReactiveServiceModuleConfiguration;
import ru.art.reactive.service.configuration.ReactiveServiceModuleConfiguration.ReactiveServiceModuleDefaultConfiguration;
import static ru.art.core.context.Context.context;
import static ru.art.reactive.service.constants.ReactiveServiceModuleConstants.REACTIVE_SERVICE_MODULE_ID;

@Getter
public class ReactiveServiceModule implements Module<ReactiveServiceModuleConfiguration, ModuleState> {
    @Getter(lazy = true)
    private static final ReactiveServiceModuleConfiguration reactiveServiceModule = context().getModule(REACTIVE_SERVICE_MODULE_ID, new ReactiveServiceModule());
    private final ReactiveServiceModuleConfiguration defaultConfiguration = new ReactiveServiceModuleDefaultConfiguration();
    private final String id = REACTIVE_SERVICE_MODULE_ID;

    public static ReactiveServiceModuleConfiguration reactiveServiceModule() {
        return getReactiveServiceModule();
    }
}