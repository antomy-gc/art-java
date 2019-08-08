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

package ru.art.reactive.service.configuration;

import lombok.Getter;
import ru.art.core.module.ModuleConfiguration;
import ru.art.reactive.service.interception.ReactiveServiceLoggingInterception;
import ru.art.reactive.service.interception.ReactiveServiceValidationInterception;
import static ru.art.core.factory.CollectionsFactory.linkedListOf;
import static ru.art.service.interceptor.ServiceExecutionInterceptor.*;
import java.util.List;

public interface ReactiveServiceModuleConfiguration extends ModuleConfiguration {
    List<ServiceRequestInterceptor> getRequestInterceptors();

    List<ServiceResponseInterceptor> getResponseInterceptors();

    class ReactiveServiceModuleDefaultConfiguration implements ReactiveServiceModuleConfiguration {
        @Getter(lazy = true)
        private final List<ServiceRequestInterceptor> requestInterceptors = linkedListOf(interceptRequest(new ReactiveServiceLoggingInterception()), interceptRequest(new ReactiveServiceValidationInterception()));
        @Getter(lazy = true)
        private final List<ServiceResponseInterceptor> responseInterceptors = linkedListOf(interceptResponse(new ReactiveServiceLoggingInterception()));
    }
}