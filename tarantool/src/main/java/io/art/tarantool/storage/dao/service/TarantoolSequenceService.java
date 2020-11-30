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
import static io.art.core.caster.Caster.*;
import static io.art.core.checker.EmptinessChecker.isEmpty;
import static io.art.core.factory.ArrayFactory.fixedArrayOf;
import static io.art.tarantool.storage.dao.caller.TarantoolFunctionCaller.*;
import static io.art.tarantool.configuration.lua.TarantoolSequenceConfiguration.*;
import static io.art.tarantool.constants.TarantoolModuleConstants.Functions.*;
import static io.art.tarantool.storage.dao.service.executor.TarantoolLuaExecutor.*;
import static io.art.tarantool.module.TarantoolModule.*;

@UtilityClass
public final class TarantoolSequenceService {
    public static void createSequence(String instanceId, TarantoolSequenceConfiguration sequenceConfiguration) {
        evaluateLuaScript(instanceId, sequenceConfiguration.toCreateSequenceLua());
        evaluateLuaScript(instanceId, sequenceConfiguration.toManageSequenceLua());
    }

    public static void dropSequence(String instanceId, String sequenceName) {
        evaluateLuaScript(instanceId, tarantoolSequence(sequenceName).toManageSequenceLua());
        TarantoolClient client = getClient(instanceId);
        callTarantoolFunction(client, DROP + sequenceName);
    }

    public static Integer sequenceNext(String instanceId, String sequenceName) {
        evaluateLuaScript(instanceId, tarantoolSequence(sequenceName).toManageSequenceLua());
        TarantoolClient client = getClient(instanceId);
        Integer result = cast(callTarantoolFunction(client, NEXT + sequenceName).get(0));
        if (isEmpty(result)) return null;
        return result;
    }

    public static void resetSequence(String instanceId, String sequenceName) {
        evaluateLuaScript(instanceId, tarantoolSequence(sequenceName).toManageSequenceLua());
        TarantoolClient client = getClient(instanceId);
        callTarantoolFunction(client, RESET + sequenceName);
    }

    public static void setSequence(String instanceId, String sequenceName, int value) {
        evaluateLuaScript(instanceId, tarantoolSequence(sequenceName).toManageSequenceLua());
        TarantoolClient client = getClient(instanceId);
        callTarantoolFunction(client, SET + sequenceName, fixedArrayOf(value));
    }
}
