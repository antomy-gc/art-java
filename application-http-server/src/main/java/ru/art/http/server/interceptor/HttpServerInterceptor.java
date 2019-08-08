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

package ru.art.http.server.interceptor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.art.core.constants.InterceptionStrategy;
import ru.art.http.server.exception.HttpServerException;
import static java.util.Objects.isNull;
import static lombok.AccessLevel.PRIVATE;
import static ru.art.core.constants.InterceptionStrategy.*;
import static ru.art.http.server.constants.HttpServerExceptionMessages.INTERCEPTION_IS_NULL;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Getter
@AllArgsConstructor(access = PRIVATE)
public class HttpServerInterceptor {
    private final HttpServerInterception interception;
    private final InterceptionStrategy strategy;

    public static HttpServerInterceptor intercept(HttpServerInterception interception) {
        if (isNull(interception)) throw new HttpServerException(INTERCEPTION_IS_NULL);
        return new HttpServerInterceptor(interception, NEXT_INTERCEPTOR);
    }

    public static HttpServerInterceptor interceptAndProcessHandling(HttpServerInterception interception) {
        if (isNull(interception)) throw new HttpServerException(INTERCEPTION_IS_NULL);
        return new HttpServerInterceptor(interception, PROCESS_HANDLING);
    }

    public static HttpServerInterceptor interceptAndStopHandling(HttpServerInterception interception) {
        if (isNull(interception)) throw new HttpServerException(INTERCEPTION_IS_NULL);
        return new HttpServerInterceptor(interception, STOP_HANDLING);
    }


    public InterceptionStrategy intercept(HttpServletRequest request, HttpServletResponse response) {
        try {
            return interception.intercept(request, response);
        } catch (Exception e) {
            throw new HttpServerException(e);
        }
    }
}