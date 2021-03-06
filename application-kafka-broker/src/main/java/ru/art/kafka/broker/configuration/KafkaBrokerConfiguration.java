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

package ru.art.kafka.broker.configuration;

import lombok.*;
import static java.io.File.*;
import static java.nio.file.Paths.*;
import static ru.art.core.constants.NetworkConstants.*;
import static ru.art.core.constants.StringConstants.*;
import static ru.art.kafka.broker.constants.KafkaBrokerModuleConstants.*;
import java.util.*;

@Getter
@Builder
@EqualsAndHashCode
public class KafkaBrokerConfiguration {
    @Builder.Default
    private final String zookeeperConnection = LOCALHOST + COLON + DEFAULT_ZOOKEEPER_PORT;
    @Builder.Default
    private final Properties additionalProperties = new Properties();
    @Builder.Default
    private final int port = DEFAULT_BROKER_PORT;
    @Builder.Default
    private final short replicationFactor = 1;
    @Builder.Default
    private final String logDirectory = get(EMPTY_STRING).toAbsolutePath() + separator + DEFAULT_KAFKA_LOG_DIRECTORY;
}
