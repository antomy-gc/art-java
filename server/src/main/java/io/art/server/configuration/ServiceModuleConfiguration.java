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

package io.art.server.configuration;

import io.art.core.module.*;
import io.art.server.interceptor.ServiceExecutionInterceptor.*;
import io.art.server.interceptor.*;
import io.art.server.model.*;
import io.github.resilience4j.bulkhead.*;
import io.github.resilience4j.circuitbreaker.*;
import io.github.resilience4j.ratelimiter.*;
import io.github.resilience4j.retry.*;
import lombok.*;
import static io.art.core.factory.CollectionsFactory.*;
import static io.art.server.interceptor.ServiceExecutionInterceptor.*;
import java.util.*;


public interface ServiceModuleConfiguration extends ModuleConfiguration {
    List<RequestInterceptor> getRequestInterceptors();

    List<ResponseInterceptor> getResponseInterceptors();

    CircuitBreakerRegistry getCircuitBreakerRegistry();

    RateLimiterRegistry getRateLimiterRegistry();

    RetryRegistry getRetryRegistry();

    BulkheadRegistry getBulkheadRegistry();

    Map<String, ServiceExecutionConfiguration> getExecutionConfigurations();

    Map<String, DeactivationConfiguration> getDeactivationConfigurations();

    ServiceModuleDefaultConfiguration DEFAULT_CONFIGURATION = new ServiceModuleDefaultConfiguration();


    @Getter
    class ServiceModuleDefaultConfiguration implements ServiceModuleConfiguration {
        private final CircuitBreakerRegistry circuitBreakerRegistry = CircuitBreakerRegistry.ofDefaults();
        private final RateLimiterRegistry rateLimiterRegistry = RateLimiterRegistry.ofDefaults();
        private final RetryRegistry retryRegistry = RetryRegistry.ofDefaults();
        private final BulkheadRegistry bulkheadRegistry = BulkheadRegistry.ofDefaults();
        private final Map<String, ServiceExecutionConfiguration> executionConfigurations = mapOf();
        private final Map<String, DeactivationConfiguration> deactivationConfigurations = mapOf();
        @Getter
        private final List<RequestInterceptor> requestInterceptors = linkedListOf(
                interceptRequest(new ServiceLoggingInterception()),
                interceptRequest(new ServiceValidationInterception())
        );
        @Getter
        private final List<ResponseInterceptor> responseInterceptors = linkedListOf(interceptResponse(new ServiceLoggingInterception()));
    }
}
