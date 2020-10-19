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

package ru.art.tarantool.storage.dao.service;

import lombok.experimental.*;
import org.tarantool.*;
import ru.art.tarantool.configuration.lua.*;
import static ru.art.core.factory.CollectionsFactory.*;
import static ru.art.tarantool.storage.dao.caller.TarantoolFunctionCaller.*;
import static ru.art.tarantool.configuration.lua.TarantoolSpaceConfiguration.*;
import static ru.art.tarantool.constants.TarantoolModuleConstants.Functions.*;
import static ru.art.tarantool.storage.dao.service.executor.TarantoolLuaExecutor.*;
import static ru.art.tarantool.module.TarantoolModule.*;
import java.util.*;

@UtilityClass
public final class TarantoolSpaceService {
   public static void createSpace(String instanceId, TarantoolSpaceConfiguration configuration) {
        evaluateLuaScript(instanceId, configuration.toCreateSpaceLua());
        evaluateLuaScript(instanceId, configuration.toManageSpaceLua());
    }

    public static void dropSpace(String instanceId, String spaceName) {
        evaluateLuaScript(instanceId, tarantoolSpace(spaceName).toManageSpaceLua());
        TarantoolClient client = getClient(instanceId);
        callTarantoolFunction(client, DROP + spaceName);
    }

    public static void formatSpace(String instanceId, String spaceName, Set<TarantoolSpaceConfiguration.Format> fieldsFormat) {
        evaluateLuaScript(instanceId, TarantoolSpaceConfiguration.builder().spaceName(spaceName).formats(fieldsFormat).build().toFormatSpaceLua());
    }

    public static void renameSpace(String instanceId, String currentSpaceName, String newSpaceName) {
        evaluateLuaScript(instanceId, tarantoolSpace(currentSpaceName).toManageSpaceLua());
        TarantoolClient client = getClient(instanceId);
        callTarantoolFunction(client, RENAME + currentSpaceName, fixedArrayOf(newSpaceName));
    }
}


