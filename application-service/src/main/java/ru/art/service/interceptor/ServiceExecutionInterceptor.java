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

package ru.art.service.interceptor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.art.service.constants.ServiceExceptionsMessages;
import ru.art.service.exception.ServiceInternalException;
import static java.util.Objects.isNull;

public interface ServiceExecutionInterceptor {
    static ServiceRequestInterceptor interceptRequest(ServiceRequestInterception interception) {
        if (isNull(interception)) throw new ServiceInternalException(ServiceExceptionsMessages.INTERCEPTION_IS_NULL);
        return new ServiceRequestInterceptor(interception);
    }

    static ServiceResponseInterceptor interceptResponse(ServiceResponseInterception interception) {
        if (isNull(interception)) throw new ServiceInternalException(ServiceExceptionsMessages.INTERCEPTION_IS_NULL);
        return new ServiceResponseInterceptor(interception);
    }

    @Getter
    @AllArgsConstructor
    class ServiceRequestInterceptor {
        private final ServiceRequestInterception interception;
    }

    @Getter
    @AllArgsConstructor
    class ServiceResponseInterceptor {
        private final ServiceResponseInterception interception;
    }
}