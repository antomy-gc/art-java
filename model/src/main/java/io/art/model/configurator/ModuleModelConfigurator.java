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

package io.art.model.configurator;

import io.art.model.implementation.*;
import lombok.*;
import static io.art.model.constants.ModelConstants.*;
import java.util.function.*;

@Getter
@RequiredArgsConstructor
public class ModuleModelConfigurator {
    private final String mainModuleId;
    private final ServerModelConfigurator server = new ServerModelConfigurator();

    public ModuleModelConfigurator serve(UnaryOperator<ServerModelConfigurator> server) {
        server.apply(this.server);
        return this;
    }

    public ModuleModel configure() {
        return ModuleModel.builder()
                .mainModuleId(mainModuleId)
                .serverModel(server.configure())
                .build();
    }

    public static ModuleModelConfigurator module(String id) {
        return new ModuleModelConfigurator(id);
    }

    public static ModuleModelConfigurator module() {
        return module(DEFAULT_MODULE_ID);
    }

    public static ModuleModelConfigurator module(Class<?> mainClass) {
        return module(mainClass.getSimpleName());
    }
}