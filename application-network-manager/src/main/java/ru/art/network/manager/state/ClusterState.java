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

package ru.art.network.manager.state;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.art.network.manager.balancer.Balancer;
import ru.art.state.api.model.ModuleNetworkResponse;
import static java.util.stream.Collectors.toMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class ClusterState {
    private final Balancer balancer;

    public void updateModuleEndpoints(Map<String, ModuleNetworkResponse> moduleEndpoints) {
        this.balancer.updateEndpoints(moduleEndpoints
                .entrySet()
                .stream()
                .collect(toMap(Map.Entry::getKey, entry -> entry.getValue().getEndpoints())));
    }

}