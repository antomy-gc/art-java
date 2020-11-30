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

package io.art.model.implementation;

import io.art.core.collection.*;
import lombok.*;
import static io.art.core.checker.NullityChecker.*;
import static io.art.server.specification.ServiceMethodSpecification.*;

@Getter
@RequiredArgsConstructor
public class ServerModel {
    private final ImmutableMap<String, ServiceModel<?>> services;

    public ServiceMethodSpecificationBuilder implement(String serviceId, String methodId, ServiceMethodSpecificationBuilder current) {
        return let(services.get(serviceId), service -> service.implement(methodId, current));
    }
}
