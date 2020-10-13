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

package io.art.rsocket.payload;

import io.art.entity.constants.EntityConstants.*;
import io.art.rsocket.exception.*;
import io.art.rsocket.model.*;
import io.netty.buffer.*;
import io.rsocket.*;
import lombok.*;
import static io.art.json.descriptor.JsonEntityReader.*;
import static io.art.message.pack.descriptor.MessagePackEntityReader.*;
import static io.art.protobuf.descriptor.ProtobufEntityReader.*;
import static io.art.rsocket.constants.RsocketModuleConstants.ExceptionMessages.*;
import static io.art.rsocket.module.RsocketModule.*;
import static io.art.xml.descriptor.XmlEntityReader.*;
import static java.text.MessageFormat.*;

@RequiredArgsConstructor
public class RsocketPayloadReader {
    private final DataFormat dataFormat;

    public RsocketPayloadValue readPayloadData(Payload payload) {
        ByteBuf data = payload.sliceData();
        if (data.capacity() == 0) {
            return null;
        }
        switch (dataFormat) {
            case PROTOBUF:
                return new RsocketPayloadValue(payload, readProtobuf(data));
            case JSON:
                return new RsocketPayloadValue(payload, readJson(data));
            case XML:
                return new RsocketPayloadValue(payload, readXml(data));
            case MESSAGE_PACK:
                return new RsocketPayloadValue(payload, readMessagePack(data));
        }
        throw new RsocketException(format(UNSUPPORTED_DATA_FORMAT, rsocketModule().configuration().getDefaultDataFormat()));
    }

    public RsocketPayloadValue readPayloadMetaData(Payload payload) {
        ByteBuf data = payload.sliceMetadata();
        if (data.capacity() == 0) {
            return null;
        }
        switch (dataFormat) {
            case PROTOBUF:
                return new RsocketPayloadValue(payload, readProtobuf(data));
            case JSON:
                return new RsocketPayloadValue(payload, readJson(data));
            case XML:
                return new RsocketPayloadValue(payload, readXml(data));
            case MESSAGE_PACK:
                return new RsocketPayloadValue(payload, readMessagePack(data));
        }
        throw new RsocketException(format(UNSUPPORTED_DATA_FORMAT, rsocketModule().configuration().getDefaultDataFormat()));
    }

    public static RsocketPayloadValue readPayloadData(Payload payload,  DataFormat dataFormat) {
        return new RsocketPayloadReader(dataFormat).readPayloadData(payload);
    }

    public RsocketPayloadValue readPayloadMetaData(Payload payload,  DataFormat dataFormat) {
        return new RsocketPayloadReader(dataFormat).readPayloadMetaData(payload);
    }
}