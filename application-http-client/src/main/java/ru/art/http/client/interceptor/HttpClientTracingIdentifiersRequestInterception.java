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

package ru.art.http.client.interceptor;

import org.apache.http.client.methods.HttpUriRequest;
import ru.art.core.constants.InterceptionStrategy;
import static org.apache.logging.log4j.ThreadContext.get;
import static ru.art.core.checker.CheckerForEmptiness.isNotEmpty;
import static ru.art.core.constants.InterceptionStrategy.NEXT_INTERCEPTOR;
import static ru.art.http.client.constants.HttpClientModuleConstants.TRACE_ID_HEADER;
import static ru.art.logging.LoggingModuleConstants.LoggingParameters.TRACE_ID_KEY;

public class HttpClientTracingIdentifiersRequestInterception implements HttpClientRequestInterception {
    @Override
    public InterceptionStrategy intercept(HttpUriRequest request) {
        String traceId = get(TRACE_ID_KEY);
        if (isNotEmpty(traceId)) {
            request.addHeader(TRACE_ID_HEADER, traceId);
        }
        return NEXT_INTERCEPTOR;
    }
}