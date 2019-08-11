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

package ru.art.configurator.configuration;

import lombok.Getter;
import ru.art.grpc.server.configuration.GrpcServerModuleConfiguration.GrpcServerModuleDefaultConfiguration;
import static ru.art.config.ConfigProvider.config;
import static ru.art.configurator.api.constants.ConfiguratorServiceConstants.CONFIGURATOR_PATH;
import static ru.art.configurator.constants.ConfiguratorModuleConstants.ConfiguratorLocalConfigKeys.CONFIGURATOR_GRPC_PORT_PROPERTY;
import static ru.art.configurator.constants.ConfiguratorModuleConstants.ConfiguratorLocalConfigKeys.CONFIGURATOR_SECTION_ID;
import static ru.art.core.extension.ExceptionExtensions.ifExceptionOrEmpty;
import static ru.art.core.network.selector.PortSelector.findAvailableTcpPort;

@Getter
public class ConfiguratorGrpcServerConfiguration extends GrpcServerModuleDefaultConfiguration {
    private final String path = CONFIGURATOR_PATH;
    private final int port = ifExceptionOrEmpty(() -> config(CONFIGURATOR_SECTION_ID).getInt(CONFIGURATOR_GRPC_PORT_PROPERTY), findAvailableTcpPort());
}
