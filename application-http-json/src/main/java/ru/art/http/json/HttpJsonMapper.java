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

package ru.art.http.json;

import ru.art.core.mime.*;
import ru.art.entity.*;
import ru.art.http.mapper.HttpContentMapper.*;
import static java.util.Objects.*;
import static ru.art.core.checker.CheckerForEmptiness.*;
import static ru.art.core.constants.ArrayConstants.*;
import static ru.art.http.constants.HttpExceptionsMessages.*;
import static ru.art.json.descriptor.JsonEntityReader.*;
import static ru.art.json.descriptor.JsonEntityWriter.*;
import java.nio.charset.*;

public class HttpJsonMapper implements HttpEntityToContentMapper, HttpContentToValueMapper {
    @Override
    public byte[] mapToBytes(Value value, MimeType mimeType, Charset charset) {
        if (isNull(mimeType)) throw new HttpJsonMapperException(CONTENT_TYPE_IS_NULL);
        if (isNull(value)) return EMPTY_BYTES;
        return writeJson(value).getBytes(charset);
    }

    @Override
    public Value mapFromBytes(byte[] content, MimeType mimeType, Charset charset) {
        if (isNull(mimeType)) throw new HttpJsonMapperException(CONTENT_TYPE_IS_NULL);
        if (isEmpty(content)) return null;
        String jsonString = new String(content, charset);
        return readJson(jsonString);
    }
}
