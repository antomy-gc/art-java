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

package io.art.tarantool.storage.dao.service;

import lombok.experimental.*;
import org.tarantool.*;
import io.art.tarantool.configuration.lua.*;
import static io.art.core.factory.ArrayFactory.fixedArrayOf;
import static io.art.tarantool.storage.dao.caller.TarantoolFunctionCaller.*;
import static io.art.tarantool.configuration.lua.TarantoolIndexConfiguration.*;
import static io.art.tarantool.constants.TarantoolModuleConstants.Functions.*;
import static io.art.tarantool.storage.dao.service.executor.TarantoolLuaExecutor.*;
import static io.art.tarantool.module.TarantoolModule.*;

@UtilityClass
public final class TarantoolIndexService {
    public static void createIndex(String instanceId, TarantoolIndexConfiguration configuration) {
        evaluateLuaScript(instanceId, configuration.toCreateIndexLua());
        evaluateLuaScript(instanceId, configuration.toManageIndexLua());
    }

    public static void dropIndex(String instanceId, String spaceName, String indexName) {
        evaluateLuaScript(instanceId, tarantoolIndex(indexName, spaceName).toManageIndexLua());
        TarantoolClient client = getClient(instanceId);
        callTarantoolFunction(client, DROP + spaceName + indexName);
    }

    public static void alterIndex(String instanceId, TarantoolIndexConfiguration newConfiguration) {
        evaluateLuaScript(instanceId, newConfiguration.toAlterIndexLua());
        evaluateLuaScript(instanceId, newConfiguration.toManageIndexLua());
    }

    public static void renameIndex(String instanceId, String spaceName, String currentIndexName, String newIndexName) {
        evaluateLuaScript(instanceId, tarantoolIndex(currentIndexName, spaceName).toManageIndexLua());
        TarantoolClient client = getClient(instanceId);
        callTarantoolFunction(client, RENAME + spaceName + currentIndexName, fixedArrayOf(newIndexName));
    }
}