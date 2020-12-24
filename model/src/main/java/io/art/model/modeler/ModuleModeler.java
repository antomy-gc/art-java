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

package io.art.model.modeler;

import io.art.model.implementation.*;
import lombok.*;
import static io.art.model.constants.ModelConstants.DEFAULT_MODULE_ID;
import java.util.function.*;

@Getter
@RequiredArgsConstructor
public class ModuleModeler {
    private final String mainModuleId;
    private final ServerModeler server = new ServerModeler();

    public ModuleModeler serve(UnaryOperator<ServerModeler> server) {
        server.apply(this.server);
        return this;
    }

    public ModuleModel apply() {
        return ModuleModel.builder()
                .mainModuleId(mainModuleId)
                .serverModel(server.apply())
                .build();
    }

    public static ModuleModeler module() {
        return new ModuleModeler(DEFAULT_MODULE_ID);
    }

    public static ModuleModeler module(String id) {
        return new ModuleModeler(id);
    }
}
