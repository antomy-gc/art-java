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

package ru.art.service.model;

import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.retry.RetryConfig;
import lombok.Builder;
import lombok.Getter;
import ru.art.service.constants.ServiceExecutionFeatureTarget;

@Builder
public class ServiceExecutionConfiguration {
    @Builder.Default
    private final RetryServiceConfig retryConfig = new RetryServiceConfig();
    @Builder.Default
    private final CircuitBreakerServiceConfig circuitBreakerConfig = new CircuitBreakerServiceConfig();
    @Builder.Default
    private final RateLimiterServiceConfig rateLimiterConfig = new RateLimiterServiceConfig();
    @Builder.Default
    private final BulkheadServiceConfig bulkheadConfig = new BulkheadServiceConfig();
    @Getter
    private ServiceExecutionFeatureTarget retryTarget;
    @Getter
    private ServiceExecutionFeatureTarget circuitBreakTarget;
    @Getter
    private ServiceExecutionFeatureTarget rateLimiterTarget;
    @Getter
    private ServiceExecutionFeatureTarget bulkheadTarget;

    public boolean isBreakable() {
        return circuitBreakerConfig.isBreakable();
    }

    public boolean isLimited() {
        return rateLimiterConfig.isLimited();
    }

    public boolean isBulkHeaded() {
        return bulkheadConfig.isBulkheaded();
    }

    public boolean isRetryable() {
        return retryConfig.isRetryable();
    }

    public CircuitBreakerConfig getCircuitBreakerConfig() {
        return circuitBreakerConfig.getCircuitBreakerConfig();
    }

    public RateLimiterConfig getRateLimiterConfig() {
        return rateLimiterConfig.getRateLimiterConfig();
    }

    public BulkheadConfig getBulkheadConfig() {
        return bulkheadConfig.getBulkheadConfig();
    }

    public RetryConfig getRetryConfig() {
        return retryConfig.getRetryConfig();
    }
}