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

package io.art.server.decorator;

import io.art.core.constants.*;
import io.art.core.lazy.*;
import io.art.core.model.*;
import io.art.server.specification.*;
import lombok.*;
import org.apache.logging.log4j.*;
import reactor.core.publisher.*;
import static io.art.core.lazy.ManagedValue.*;
import static io.art.logging.LoggingModule.*;
import static io.art.server.constants.ServerModuleConstants.LoggingMessages.*;
import static io.art.server.module.ServerModule.*;
import static java.text.MessageFormat.*;
import static lombok.AccessLevel.*;
import java.util.*;
import java.util.function.*;

public class ServiceLoggingDecorator implements UnaryOperator<Flux<Object>> {
    @Getter(lazy = true, value = PRIVATE)
    private static final Logger logger = logger(ServiceLoggingDecorator.class);
    private final ManagedValue<UnaryOperator<Flux<Object>>> decorator = managed(this::createDecorator);
    private final MethodDecoratorScope scope;
    private final Supplier<Boolean> enabled;
    private final ServiceMethodIdentifier serviceMethodId;

    public ServiceLoggingDecorator(ServiceMethodIdentifier serviceMethodId, MethodDecoratorScope scope) {
        this.scope = scope;
        this.enabled = () -> serverModule().configuration().isLogging(serviceMethodId);
        this.serviceMethodId = serviceMethodId;
    }

    @Override
    public Flux<Object> apply(Flux<Object> input) {
        if (!enabled.get()) {
            return input;
        }
        return decorator.get().apply(input);
    }

    private void logSubscribe(ServiceMethodSpecification specification) {
        getLogger().info(format(SERVICE_SUBSCRIBED_MESSAGE, specification.getServiceId(), specification.getMethodId()));
    }

    private void logComplete(ServiceMethodSpecification specification) {
        getLogger().info(format(SERVICE_COMPLETED_MESSAGE, specification.getServiceId(), specification.getMethodId()));
    }

    private void logInput(Object data, ServiceMethodSpecification specification) {
        getLogger().info(format(SERVICE_INPUT_DATA, specification.getServiceId(), specification.getMethodId(), data));
    }

    private void logOutput(Object data, ServiceMethodSpecification specification) {
        getLogger().info(format(SERVICE_OUTPUT_DATA, specification.getServiceId(), specification.getMethodId(), data));
    }

    private void logException(Throwable exception, ServiceMethodSpecification specification) {
        getLogger().error(format(SERVICE_FAILED_MESSAGE, specification.getServiceId(), specification.getMethodId()), exception);
    }

    private UnaryOperator<Flux<Object>> createDecorator() {
        Optional<ServiceMethodSpecification> possibleSpecification = specifications().findMethodById(serviceMethodId);
        if (!possibleSpecification.isPresent()) {
            return UnaryOperator.identity();
        }
        ServiceMethodSpecification specification = possibleSpecification.get();
        switch (scope) {
            case INPUT:
                return input -> input
                        .doOnSubscribe(subscription -> logSubscribe(specification))
                        .doOnNext(data -> logInput(data, specification))
                        .doOnError(exception -> logException(exception, specification));
            case OUTPUT:
                return output -> output
                        .doOnNext(data -> logOutput(data, specification))
                        .doOnError(exception -> logException(exception, specification))
                        .doOnComplete(() -> logComplete(specification));
        }
        return UnaryOperator.identity();
    }
}
