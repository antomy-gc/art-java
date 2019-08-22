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

package ru.art.configurator.configuration;

import lombok.*;
import org.zalando.logbook.*;
import ru.art.configurator.dao.*;
import ru.art.core.mime.*;
import ru.art.http.mapper.*;
import ru.art.http.server.HttpServerModuleConfiguration.*;
import ru.art.http.server.interceptor.*;
import java.util.*;

import static ru.art.config.ConfigProvider.*;
import static ru.art.configurator.api.constants.ConfiguratorServiceConstants.*;
import static ru.art.configurator.constants.ConfiguratorModuleConstants.*;
import static ru.art.configurator.constants.ConfiguratorModuleConstants.ConfiguratorLocalConfigKeys.*;
import static ru.art.configurator.http.content.mapping.ConfiguratorHttpContentMapping.*;
import static ru.art.core.extension.ExceptionExtensions.*;
import static ru.art.core.factory.CollectionsFactory.*;
import static ru.art.http.constants.HttpStatus.*;
import static ru.art.http.server.HttpServerModuleConfiguration.*;
import static ru.art.http.server.constants.HttpServerModuleConstants.HttpResourceServiceConstants.*;
import static ru.art.http.server.interceptor.HttpServerInterceptor.*;
import static ru.art.http.server.service.HttpResourceService.*;
import static ru.art.metrics.http.filter.MetricsHttpLogFilter.*;

@Getter
public class ConfiguratorHttpServerConfiguration extends HttpServerModuleDefaultConfiguration {
    private final Map<MimeType, HttpContentMapper> contentMappers = configureContentMappers(super.getContentMappers());
    private final int port = ifExceptionOrEmpty(() -> config(CONFIGURATOR_SECTION_ID).getInt(CONFIGURATOR_HTTP_PORT_PROPERTY), super.getPort());
    private final Logbook logbook = logbookWithoutMetricsLogs(logbookWithoutResourceLogs()).build();
    private final String path = ifExceptionOrEmpty(() ->
            config(CONFIGURATOR_SECTION_ID).getString(CONFIGURATOR_HTTP_PATH_PROPERTY), CONFIGURATOR_PATH);
    private final List<HttpServerInterceptor> requestInterceptors = initializeRequestInterceptors(super.getRequestInterceptors());

    private static List<HttpServerInterceptor> initializeRequestInterceptors(List<HttpServerInterceptor> superInterceptors) {
        List<HttpServerInterceptor> httpServerInterceptors = dynamicArrayOf(initializeWebServerInterceptors(superInterceptors));
        httpServerInterceptors.add(intercept(CookieInterceptor.builder()
                .paths(AUTHORIZATION_CHECKING_PATHS)
                .cookie(TOKEN_COOKIE, UserDao::getToken)
                .errorStatus(UNAUTHORIZED.getCode())
                .errorContent(getStringResource(INDEX_HTML))
                .build()));
        return httpServerInterceptors;
    }
}