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

package ru.art.service;

import lombok.*;
import org.apache.logging.log4j.*;
import ru.art.logging.*;
import ru.art.service.exception.*;
import ru.art.service.interceptor.*;
import ru.art.service.model.*;
import static java.text.MessageFormat.*;
import static java.util.Objects.*;
import static org.apache.logging.log4j.ThreadContext.*;
import static ru.art.core.checker.CheckerForEmptiness.isEmpty;
import static ru.art.core.constants.StringConstants.*;
import static ru.art.core.extension.NullCheckingExtensions.*;
import static ru.art.core.factory.CollectionsFactory.*;
import static ru.art.logging.LoggingModule.*;
import static ru.art.logging.LoggingModuleConstants.DEFAULT_REQUEST_ID;
import static ru.art.logging.LoggingModuleConstants.LoggingParameters.*;
import static ru.art.logging.LoggingParametersManager.*;
import static ru.art.logging.ThreadContextExtensions.*;
import static ru.art.service.ServiceModule.*;
import static ru.art.service.constants.ServiceLoggingMessages.*;
import static ru.art.service.constants.ServiceModuleConstants.*;
import static ru.art.service.model.ServiceInterceptionResult.*;
import java.util.*;

public class ServiceLoggingInterception implements ServiceRequestInterception, ServiceResponseInterception {
    private final static ThreadLocal<Stack<ServiceCallLoggingParameters>> serviceLoggingParameters = new ThreadLocal<>();
    @Getter(lazy = true)
    private final Logger logger = loggingModule().getLogger(ServiceLoggingInterception.class);

    private static void putRequestResponseMetrics(ServiceRequest<?> request, ServiceCallLoggingParameters parameters) {
        putIfNotNull(REQUEST_KEY, request);
        putServiceCallLoggingParameters(parameters);
        List<String> serviceTypes = serviceModuleState()
                .getServiceRegistry()
                .getService(request.getServiceMethodCommand().getServiceId())
                .getServiceTypes();
        putIfNotNull(SERVICE_TYPES_KEY, serviceTypes);
    }

    @Override
    public ServiceInterceptionResult intercept(ServiceRequest<?> request) {
        if (isNull(serviceLoggingParameters.get())) {
            serviceLoggingParameters.set(stackOf());
        }
        ServiceCallLoggingParameters parameters = ServiceCallLoggingParameters.builder()
                .serviceId(request.getServiceMethodCommand().getServiceId())
                .serviceMethodId(request.getServiceMethodCommand().toString())
                .serviceMethodCommand(request.getServiceMethodCommand().toString() + DOT + getOrElse(get(REQUEST_ID_KEY), DEFAULT_REQUEST_ID))
                .build();
        serviceLoggingParameters.get().push(parameters);
        putRequestResponseMetrics(request, parameters);
        putIfNotNull(SERVICE_EVENT_TYPE_KEY, REQUEST_EVENT);
        getLogger().info(format(EXECUTION_SERVICE_MESSAGE, request.getServiceMethodCommand(), getOrElse(get(REQUEST_ID_KEY), DEFAULT_REQUEST_ID), request));
        remove(SERVICE_EVENT_TYPE_KEY);
        return nextInterceptor(request);
    }

    @Override
    public ServiceInterceptionResult intercept(ServiceRequest<?> request, ServiceResponse<?> response) {
        if (!isEmpty(serviceLoggingParameters.get())) {
            putRequestResponseMetrics(request, serviceLoggingParameters.get().pop());
        }
        ServiceExecutionException serviceException = response.getServiceException();
        if (nonNull(serviceException)) {
            putIfNotNull(RESPONSE_KEY, response);
            putIfNotNull(SERVICE_EXCEPTION_KEY, serviceException);
            putIfNotNull(SERVICE_EVENT_TYPE_KEY, RESPONSE_EVENT);
            getLogger().error(format(SERVICE_FAILED_MESSAGE,
                    request.getServiceMethodCommand(),
                    getOrElse(get(REQUEST_ID_KEY), DEFAULT_REQUEST_ID),
                    serviceException.getErrorCode(),
                    serviceException.getErrorMessage(),
                    serviceException.getStackTrace()),
                    serviceException);
            return nextInterceptor(request, response);
        }
        putIfNotNull(RESPONSE_KEY, response);
        putIfNotNull(SERVICE_EVENT_TYPE_KEY, RESPONSE_EVENT);
        getLogger().info(format(SERVICE_EXECUTED_MESSAGE, request.getServiceMethodCommand(), getOrElse(get(REQUEST_ID_KEY), DEFAULT_REQUEST_ID), response));
        return nextInterceptor(request, response);
    }
}
