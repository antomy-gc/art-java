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

package io.art.grpc.client.communicator;

import io.grpc.*;
import lombok.*;
import io.art.entity.Value;
import io.art.entity.*;
import io.art.entity.interceptor.*;
import io.art.entity.mapper.*;
import io.art.grpc.client.exception.*;
import io.art.grpc.client.handler.*;
import static lombok.AccessLevel.*;
import static io.art.core.checker.EmptinessChecker.*;
import static io.art.core.factory.CollectionsFactory.*;
import static io.art.grpc.client.constants.GrpcClientExceptionMessages.*;
import static io.art.grpc.client.module.GrpcClientModule.*;
import java.util.*;
import java.util.concurrent.*;

@Getter(value = PACKAGE)
@Setter(value = PACKAGE)
@ToString(onlyExplicitlyIncluded = true)
public class GrpcCommunicationConfiguration {
    @ToString.Include
    private String serviceId;
    @ToString.Include
    private String methodId;
    @ToString.Include
    private String url;
    @ToString.Include
    private String path;
    private ValueFromModelMapper<?, ? extends Value> requestMapper;
    private ValueToModelMapper<?, ? extends Value> responseMapper;
    private GrpcCommunicationCompletionHandler<?, ?> completionHandler;
    private GrpcCommunicationExceptionHandler<?> exceptionHandler;
    private List<ClientInterceptor> interceptors = grpcClientModule().getInterceptors();
    private Executor overrideExecutor;
    @ToString.Include
    private long deadlineTimeout;
    @ToString.Include
    private long keepAliveTimeNanos = grpcClientModule().getKeepAliveTimeNanos();
    @ToString.Include
    private long keepAliveTimeOutNanos = grpcClientModule().getKeepAliveTimeOutNanos();
    @ToString.Include
    private boolean keepAliveWithoutCalls = grpcClientModule().isKeepAliveWithoutCalls();
    @ToString.Include
    private boolean waitForReady = grpcClientModule().isWaitForReady();
    @ToString.Include
    private boolean useSecuredTransport;
    private List<ValueInterceptor<Entity, Entity>> requestValueInterceptors = linkedListOf(grpcClientModule().getRequestValueInterceptors());
    private List<ValueInterceptor<Entity, Entity>> responseValueInterceptors = linkedListOf(grpcClientModule().getResponseValueInterceptors());
    private Executor asynchronousFuturesExecutor = grpcClientModule().getAsynchronousFuturesExecutor();

    void validateRequiredFields() {
        boolean serviceIdIsEmpty = isEmpty(serviceId);
        boolean methodIdIsEmpty = isEmpty(methodId);
        if (!serviceIdIsEmpty && !methodIdIsEmpty) {
            return;
        }
        if (serviceIdIsEmpty && methodIdIsEmpty) {
            throw new GrpcClientException(INVALID_GRPC_COMMUNICATION_CONFIGURATION + "serviceId,methodId or functionId");
        }
        if (serviceIdIsEmpty) {
            throw new GrpcClientException(INVALID_GRPC_COMMUNICATION_CONFIGURATION + "serviceId");
        }
        throw new GrpcClientException("methodId");
    }
}
