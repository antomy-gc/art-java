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

package ru.art.configurator.api.specification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.art.configurator.api.entity.Configuration;
import ru.art.configurator.api.entity.ModuleKey;
import ru.art.grpc.client.communicator.GrpcCommunicator;
import ru.art.grpc.client.specification.GrpcCommunicationSpecification;
import ru.art.service.exception.UnknownServiceMethodException;
import static java.lang.System.getProperty;
import static ru.art.configurator.api.constants.ConfiguratorCommunicationConstants.CONFIGURATOR_COMMUNICATION_SERVICE_ID;
import static ru.art.configurator.api.constants.ConfiguratorServiceConstants.CONFIGURATOR_SERVICE_ID;
import static ru.art.configurator.api.constants.ConfiguratorServiceConstants.Methods.GET_PROTOBUF_CONFIG;
import static ru.art.configurator.api.mapping.ConfigurationMapping.configurationMapper;
import static ru.art.configurator.api.mapping.ModuleKeyMapping.moduleKeyMapper;
import static ru.art.core.caster.Caster.cast;
import static ru.art.core.checker.CheckerForEmptiness.isEmpty;
import static ru.art.core.constants.ContextConstants.LOCAL_PROFILE;
import static ru.art.core.constants.SystemProperties.PROFILE_PROPERTY;
import static ru.art.core.context.Context.contextConfiguration;
import static ru.art.core.extension.NullCheckingExtensions.getOrElse;
import static ru.art.entity.Entity.entityBuilder;
import static ru.art.grpc.client.communicator.GrpcCommunicator.grpcCommunicator;

@Getter
@AllArgsConstructor
public class ConfiguratorCommunicationSpecification implements GrpcCommunicationSpecification {
    private final String host;
    private final Integer port;
    private final String path;
    private final String serviceId = CONFIGURATOR_COMMUNICATION_SERVICE_ID;
    private final String profileId = getProperty(PROFILE_PROPERTY);
    private final ModuleKey moduleKey = new ModuleKey(isEmpty(profileId) ? LOCAL_PROFILE : profileId, contextConfiguration().getMainModuleId());

    @Getter(lazy = true)
    private final GrpcCommunicator getProtobufConfig = grpcCommunicator(host, port, path)
            .serviceId(CONFIGURATOR_SERVICE_ID)
            .methodId(GET_PROTOBUF_CONFIG)
            .requestMapper(moduleKeyMapper.getFromModel())
            .responseMapper(configurationMapper.getToModel());

    private Configuration getConfiguration() {
        try {
            return getOrElse((Configuration) getGetProtobufConfig().execute(moduleKey).getResponseData(), Configuration.builder().build());
        } catch (Exception e) {
            return Configuration.builder()
                    .configuration(entityBuilder().build())
                    .build();
        }
    }

    @Override
    public <P, R> R executeMethod(String methodId, P request) {
        if (GET_PROTOBUF_CONFIG.equals(methodId)) {
            return cast(getConfiguration());
        }
        throw new UnknownServiceMethodException(getServiceId(), methodId);
    }
}
