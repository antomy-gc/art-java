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

package ru.art.metrics.http.specification;

import lombok.Getter;
import ru.art.http.server.model.HttpService;
import ru.art.http.server.specification.HttpServiceSpecification;
import ru.art.service.exception.UnknownServiceMethodException;
import static java.util.Collections.emptyList;
import static ru.art.core.caster.Caster.cast;
import static ru.art.entity.PrimitiveMapping.stringMapper;
import static ru.art.http.server.model.HttpService.httpService;
import static ru.art.metrics.constants.MetricsModuleConstants.*;
import static ru.art.metrics.http.constants.MetricsModuleHttpConstants.METRICS_CONTENT_TYPE;
import static ru.art.metrics.module.MetricsModule.metricsModule;
import static ru.art.service.interceptor.ServiceExecutionInterceptor.ServiceRequestInterceptor;
import static ru.art.service.interceptor.ServiceExecutionInterceptor.ServiceResponseInterceptor;
import java.util.List;

@Getter
public class MetricServiceSpecification implements HttpServiceSpecification {
    private final String serviceId = METRICS_SERVICE_ID;
    private final List<ServiceRequestInterceptor> requestInterceptors = emptyList();
    private final List<ServiceResponseInterceptor> responseInterceptors = emptyList();

    private final HttpService httpService = httpService()
            .get(METRICS_METHOD_ID)
            .produces(METRICS_CONTENT_TYPE)
            .responseMapper(stringMapper.getFromModel())
            .listen(METRICS_PATH)
            .serve(metricsModule().getPath());

    @Override
    public <P, R> R executeMethod(String methodId, P request) {
        if (METRICS_METHOD_ID.equals(methodId)) {
            return cast(metricsModule().getPrometheusMeterRegistry().scrape());
        }
        throw new UnknownServiceMethodException(METRICS_SERVICE_ID, methodId);
    }
}