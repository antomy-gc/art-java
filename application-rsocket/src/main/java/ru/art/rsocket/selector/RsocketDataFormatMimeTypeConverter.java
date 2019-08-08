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

package ru.art.rsocket.selector;

import lombok.NoArgsConstructor;
import static io.rsocket.metadata.WellKnownMimeType.*;
import static lombok.AccessLevel.PRIVATE;
import static ru.art.core.extension.NullCheckingExtensions.getOrElse;
import static ru.art.rsocket.constants.RsocketModuleConstants.BINARY_MIME_TYPE;
import static ru.art.rsocket.constants.RsocketModuleConstants.RsocketDataFormat;
import static ru.art.rsocket.constants.RsocketModuleConstants.RsocketDataFormat.*;
import static ru.art.rsocket.module.RsocketModule.rsocketModule;

@NoArgsConstructor(access = PRIVATE)
public class RsocketDataFormatMimeTypeConverter {
    public static RsocketDataFormat fromMimeType(String mimeType) {
        if (APPLICATION_JSON.getString().equalsIgnoreCase(mimeType)) return JSON;
        if (APPLICATION_PROTOBUF.getString().equalsIgnoreCase(mimeType)) return PROTOBUF;
        if (APPLICATION_XML.getString().equalsIgnoreCase(mimeType)) return XML;
        if (TEXT_XML.getString().equalsIgnoreCase(mimeType)) return XML;
        return rsocketModule().getDefaultDataFormat();
    }

    public static String toMimeType(RsocketDataFormat dataFormat) {
        switch (getOrElse(dataFormat, rsocketModule().getDefaultDataFormat())) {
            case PROTOBUF:
                return APPLICATION_PROTOBUF.getString();
            case JSON:
                return APPLICATION_JSON.getString();
            case XML:
                return APPLICATION_XML.getString();
        }
        return BINARY_MIME_TYPE;
    }
}