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

package ru.art.tarantool.module.state;

import lombok.*;
import org.tarantool.*;
import ru.art.core.module.*;
import ru.art.tarantool.configuration.lua.*;

import static ru.art.core.factory.CollectionsFactory.*;

import java.util.*;

@Getter
@Setter
public class TarantoolModuleState implements ModuleState {
    private final Map<String, TarantoolClient> clients = concurrentHashMap();
    private final Map<String, TarantoolClient> clusterClients = concurrentHashMap();
    private final Map<String, Set<TarantoolValueScriptConfiguration>> loadedValueScripts = concurrentHashMap();
    private final Map<String, Set<TarantoolCommonScriptConfiguration>> loadedCommonScripts = concurrentHashMap();
}
