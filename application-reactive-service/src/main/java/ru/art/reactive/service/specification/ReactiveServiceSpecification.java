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

package ru.art.reactive.service.specification;

import ru.art.reactive.service.model.ReactiveService;
import ru.art.service.Specification;
import static ru.art.core.factory.CollectionsFactory.fixedArrayOf;
import static ru.art.reactive.service.constants.ReactiveServiceModuleConstants.REACTIVE_SERVICE_TYPE;
import static ru.art.reactive.service.module.ReactiveServiceModule.reactiveServiceModule;
import static ru.art.service.interceptor.ServiceExecutionInterceptor.ServiceRequestInterceptor;
import static ru.art.service.interceptor.ServiceExecutionInterceptor.ServiceResponseInterceptor;
import java.util.List;

public interface ReactiveServiceSpecification extends Specification {
    ReactiveService getReactiveService();

    @Override
    default List<ServiceRequestInterceptor> getRequestInterceptors() {
        return reactiveServiceModule().getRequestInterceptors();
    }

    @Override
    default List<ServiceResponseInterceptor> getResponseInterceptors() {
        return reactiveServiceModule().getResponseInterceptors();
    }

    @Override
    default List<String> getServiceTypes() {
        return fixedArrayOf(REACTIVE_SERVICE_TYPE);
    }
}