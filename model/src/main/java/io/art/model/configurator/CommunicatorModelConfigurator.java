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

package io.art.model.configurator;

import io.art.core.collection.*;
import io.art.model.implementation.communicator.*;
import lombok.*;
import static io.art.core.collection.ImmutableMap.*;
import static io.art.core.collection.ImmutableSet.*;
import static java.util.function.Function.*;
import static lombok.AccessLevel.*;
import java.util.function.*;

@Getter(value = PACKAGE)
public class CommunicatorModelConfigurator {
    private final ImmutableSet.Builder<CommunicatorSpecificationConfigurator> communicators = immutableSetBuilder();

    public CommunicatorModelConfigurator rsocket(Class<?> implementationInterface) {
        return rsocket(implementationInterface, UnaryOperator.identity());
    }

    public CommunicatorModelConfigurator rsocket(Class<?> implementationInterface, UnaryOperator<CommunicatorSpecificationConfigurator> modeler) {
        communicators.add(modeler.apply(new CommunicatorSpecificationConfigurator(implementationInterface.getSimpleName(), implementationInterface)));
        return this;
    }

    CommunicatorModel configure() {
        ImmutableMap<String, CommunicatorSpecificationModel> communicators = this.communicators.build()
                .stream()
                .map(CommunicatorSpecificationConfigurator::configure)
                .collect(immutableMapCollector(CommunicatorSpecificationModel::getId, identity()));
        return new CommunicatorModel(communicators);
    }
}
