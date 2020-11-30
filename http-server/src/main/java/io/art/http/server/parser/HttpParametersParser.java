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

package io.art.http.server.parser;

import io.art.core.factory.CollectionsFactory.*;
import io.art.http.server.model.*;
import static java.util.stream.Collectors.*;
import static io.art.core.checker.EmptinessChecker.*;
import static io.art.core.constants.StringConstants.*;
import static io.art.core.factory.CollectionsFactory.*;
import static io.art.value.immutable.Entity.entityBuilder;
import static io.art.value.factory.PrimitivesFactory.*;
import javax.servlet.http.*;
import java.util.*;

public interface HttpParametersParser {
    static Entity parseQueryParameters(HttpServletRequest request) {
        Map<Primitive, Value> parameters = request
                .getParameterMap()
                .keySet()
                .stream()
                .collect(mapCollector(PrimitivesFactory::stringPrimitive, name -> stringPrimitive(request.getParameterValues(name)[0])));
        return entityBuilder().putAll(parameters).build();
    }

    static Entity parsePathParameters(HttpServletRequest request, HttpService.HttpMethod methodConfig) {
        String parameterString = request.getPathInfo();
        if (isEmpty(parameterString)) return null;
        String[] parameterValues = parameterString.split(SLASH);
        if (isEmpty(parameterValues) || parameterValues.length == 1)
            return null;
        Set<String> parameterNames = methodConfig.getPath().getParameters();
        if (isEmpty(parameterNames)) {
            return null;
        }
        int parameterIndex = 1;
        MapBuilder<Primitive, Value> parameters = mapOf();
        String lastParameter = EMPTY_STRING;
        for (String name : parameterNames) {
            lastParameter = name;
            parameters.add(stringPrimitive(name), stringPrimitive(parameterValues[parameterIndex]));
            parameterIndex++;
        }
        if (isEmpty(lastParameter)) {
            return null;
        }
        if (parameters.size() < parameterIndex) {
            for (int i = parameterIndex; i < parameterValues.length; i++) {
                int index = i;
                parameters.computeIfPresent(stringPrimitive(lastParameter), (key, value) -> stringPrimitive(value + SLASH + parameterValues[index]));
            }
        }
        return entityBuilder().putAll(parameters).build();
    }
}

