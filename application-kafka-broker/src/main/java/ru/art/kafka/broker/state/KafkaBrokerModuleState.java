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

package ru.art.kafka.broker.state;

import lombok.Getter;
import lombok.Setter;
import ru.art.core.module.ModuleState;
import ru.art.kafka.broker.embedded.EmbeddedKafkaBroker;
import ru.art.kafka.broker.embedded.EmbeddedZookeeper;

@Getter
@Setter
public class KafkaBrokerModuleState implements ModuleState {
    private EmbeddedKafkaBroker broker;
    private EmbeddedZookeeper zookeeper;
}