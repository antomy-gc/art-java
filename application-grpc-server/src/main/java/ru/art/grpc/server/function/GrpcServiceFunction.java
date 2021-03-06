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

package ru.art.grpc.server.function;

import ru.art.entity.*;
import ru.art.entity.interceptor.*;
import ru.art.entity.mapper.*;
import ru.art.grpc.server.model.GrpcService.*;
import ru.art.service.constants.*;
import ru.art.service.registry.*;
import static java.util.Objects.*;
import static ru.art.core.caster.Caster.*;
import static ru.art.grpc.server.constants.GrpcServerModuleConstants.*;
import static ru.art.service.ServiceModule.*;
import java.util.function.*;

public class GrpcServiceFunction {
    private GrpcMethod grpcMethod = GrpcMethod.grpcMethod();
    private final String functionId;

    private GrpcServiceFunction(String functionId) {
        this.functionId = functionId;
    }

    public <ResponseType> GrpcServiceFunction responseMapper(ValueFromModelMapper<ResponseType, ? extends Value> responseMapper) {
        grpcMethod.responseMapper(responseMapper);
        return this;
    }

    public <RequestType> GrpcServiceFunction requestMapper(ValueToModelMapper<RequestType, ? extends Value> requestMapper) {
        grpcMethod.requestMapper(requestMapper);
        return this;
    }

    public GrpcServiceFunction validationPolicy(RequestValidationPolicy policy) {
        grpcMethod.validationPolicy(policy);
        return this;
    }

    public GrpcServiceFunction addRequestValueInterceptor(ValueInterceptor<Entity, Entity> interceptor) {
        grpcMethod.addRequestValueInterceptor(interceptor);
        return this;
    }

    public GrpcServiceFunction addResponseValueInterceptor(ValueInterceptor<Entity, Entity> interceptor) {
        grpcMethod.addResponseValueInterceptor(interceptor);
        return this;
    }

    public <RequestType, ResponseType> void handle(Function<RequestType, ResponseType> function) {
        ServiceRegistry serviceRegistry = serviceModuleState().getServiceRegistry();
        GrpcFunctionalServiceSpecification specification = cast(serviceRegistry.getServices().get(GRPC_FUNCTION_SERVICE));
        if (isNull(specification)) {
            specification = new GrpcFunctionalServiceSpecification();
            specification.addFunction(functionId, grpcMethod, function);
            serviceRegistry.registerService(specification);
            return;
        }
        specification.addFunction(functionId, grpcMethod, function);
    }

    public <RequestType> void consume(Consumer<RequestType> consumer) {
        handle(request -> {
            consumer.accept(cast(request));
            return null;
        });
    }

    public <ResponseType> void produce(Supplier<ResponseType> producer) {
        handle(request -> producer.get());
    }

    public static GrpcServiceFunction grpc(String functionId) {
        return new GrpcServiceFunction(functionId);
    }
}