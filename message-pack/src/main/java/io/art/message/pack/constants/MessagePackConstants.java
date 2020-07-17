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

package io.art.message.pack.constants;

import io.art.core.mime.*;
import static java.nio.charset.StandardCharsets.*;
import static io.art.core.mime.MimeType.*;

public interface MessagePackConstants {
    MimeType APPLICATION_MESSAGE_PACK = mimeType("application", "message-pack");
    MimeType APPLICATION_MESSAGE_PACK_UTF_8 = mimeType("application", "message-pack", UTF_8);

    interface ExceptionMessages {
        String UNKNOWN_VALUE_TYPE = "Unknown MessagePack value type: ''{0}''";
    }
}
