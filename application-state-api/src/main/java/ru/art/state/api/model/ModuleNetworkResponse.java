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

package ru.art.state.api.model;

import lombok.*;
import static java.util.Comparator.comparingInt;
import static ru.art.core.factory.CollectionsFactory.priorityQueueOf;
import java.util.Queue;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ModuleNetworkResponse {
    @Builder.Default
    private final Queue<ModuleEndpoint> endpoints = priorityQueueOf(comparingInt(ModuleEndpoint::getSessions));

    public static ModuleNetworkResponse fromNetwork(ModuleNetwork network) {
        return ModuleNetworkResponse.builder().endpoints(network.getEndpoints()).build();
    }
}