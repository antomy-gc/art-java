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

package ru.art.reactive.service.configuration;

import lombok.*;
import ru.art.core.exception.*;
import ru.art.core.module.*;
import ru.art.reactive.service.interception.*;
import ru.art.reactive.service.wrapper.*;
import ru.art.service.exception.*;
import static ru.art.core.caster.Caster.*;
import static ru.art.core.factory.CollectionsFactory.*;
import static ru.art.service.constants.ServiceErrorCodes.*;
import static ru.art.service.interceptor.ServiceExecutionInterceptor.*;
import java.util.*;

public interface ReactiveServiceModuleConfiguration extends ModuleConfiguration {
    List<RequestInterceptor> getRequestInterceptors();

    List<ResponseInterceptor> getResponseInterceptors();

    ReactiveServiceExceptionWrappers getReactiveServiceExceptionWrappers();

    ReactiveServiceModuleDefaultConfiguration DEFAULT_CONFIGURATION = new ReactiveServiceModuleDefaultConfiguration();

    @Getter
    class ReactiveServiceModuleDefaultConfiguration implements ReactiveServiceModuleConfiguration {
        private final List<RequestInterceptor> requestInterceptors = linkedListOf(interceptRequest(new ReactiveServiceLoggingInterception()),
                interceptRequest(new ReactiveServiceValidationInterception()));
        private final List<ResponseInterceptor> responseInterceptors = cast(linkedListOf(interceptResponse(new ReactiveServiceLoggingInterception())));
        private final ReactiveServiceExceptionWrappers reactiveServiceExceptionWrappers = new ReactiveServiceExceptionWrappers()
                .add(NullPointerException.class,  (command, exception) -> new ServiceExecutionException(command, NPE, exception))
                .add(UnknownServiceMethodException.class, (command, exception) -> new ServiceExecutionException(command, UNKNOWN_METHOD_ERROR, exception))
                .add(InternalRuntimeException.class, (command, exception) -> new ServiceExecutionException(command, INTERNAL_ERROR, exception))
                .add(ChildServiceException.class, (command, exception) -> new ServiceExecutionException(command, CHILD_SERVICE_ERROR, exception));
    }
}
