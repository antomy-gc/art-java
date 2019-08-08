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

package ru.art.rsocket.model;

import io.rsocket.Payload;
import lombok.Builder;
import lombok.Getter;
import ru.art.entity.Entity;
import ru.art.entity.Value;
import ru.art.entity.mapper.ValueToModelMapper;
import ru.art.service.model.ServiceMethodCommand;
import static java.util.Objects.isNull;
import static ru.art.core.caster.Caster.cast;
import static ru.art.core.checker.CheckerForEmptiness.isEmpty;
import static ru.art.entity.Value.asEntity;
import static ru.art.rsocket.constants.RsocketModuleConstants.REQUEST_DATA;
import static ru.art.rsocket.constants.RsocketModuleConstants.RsocketDataFormat;
import static ru.art.rsocket.extractor.EntityServiceCommandExtractor.extractServiceMethodCommand;
import static ru.art.rsocket.model.RsocketReactiveMethods.fromCommand;
import static ru.art.rsocket.reader.RsocketPayloadReader.readPayload;

@Getter
@Builder
public class RsocketRequestReactiveContext {
    private final Object requestData;
    private final RsocketReactiveGroupKey rsocketReactiveGroupKey;
    private final RsocketReactiveMethods rsocketReactiveMethods;

    @SuppressWarnings("Duplicates")
    public static RsocketRequestReactiveContext fromPayload(Payload payload, RsocketDataFormat dataFormat) {
        Entity serviceRequestEntity = asEntity(readPayload(payload, dataFormat));
        ServiceMethodCommand command = extractServiceMethodCommand(serviceRequestEntity);
        RsocketReactiveMethods rsocketServiceMethods = fromCommand(command);
        ValueToModelMapper<?, ?> requestMapper;
        Value requestDataValue;
        if (isNull(requestMapper = rsocketServiceMethods.getRsocketMethod().requestMapper()) || isEmpty(requestDataValue = serviceRequestEntity.getValue(REQUEST_DATA))) {
            return RsocketRequestReactiveContext.builder()
                    .rsocketReactiveGroupKey(RsocketReactiveGroupKey.builder()
                            .serviceMethodCommand(command)
                            .responseMapper(rsocketServiceMethods.getRsocketMethod().responseMapper())
                            .validationPolicy(rsocketServiceMethods.getRsocketMethod().validationPolicy())
                            .build())
                    .rsocketReactiveMethods(rsocketServiceMethods)
                    .build();
        }
        return RsocketRequestReactiveContext.builder()
                .requestData(requestMapper.map(cast(requestDataValue)))
                .rsocketReactiveGroupKey(RsocketReactiveGroupKey.builder()
                        .serviceMethodCommand(command)
                        .responseMapper(rsocketServiceMethods.getRsocketMethod().responseMapper())
                        .validationPolicy(rsocketServiceMethods.getRsocketMethod().validationPolicy())
                        .build())
                .rsocketReactiveMethods(rsocketServiceMethods)
                .build();
    }
}