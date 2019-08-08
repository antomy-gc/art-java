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

package ru.art.tarantool.configuration;

import lombok.Builder;
import lombok.Getter;
import static ru.art.core.constants.NetworkConstants.LOCALHOST;
import static ru.art.tarantool.constants.TarantoolModuleConstants.DEFAULT_TARANTOOL_PORT;
import static ru.art.tarantool.constants.TarantoolModuleConstants.DEFAULT_TARANTOOL_USERNAME;

@Getter
@Builder
public class TarantoolConnectionConfiguration {
    @Builder.Default
    private final String host = LOCALHOST;
    @Builder.Default
    private final Integer port = DEFAULT_TARANTOOL_PORT;
    @Builder.Default
    private final String username = DEFAULT_TARANTOOL_USERNAME;
    private String password;
}