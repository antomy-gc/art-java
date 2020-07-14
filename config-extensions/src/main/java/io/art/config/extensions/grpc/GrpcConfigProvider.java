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

package io.art.config.extensions.grpc;

import io.art.config.*;
import static io.art.config.extensions.ConfigExtensions.*;
import static io.art.config.extensions.grpc.GrpcConfigKeys.*;

public class GrpcConfigProvider {
    public static Config grpcServerConfig() {
        return configInner(GRPC_SERVER_CONFIG_SECTION_ID);
    }

    public static Config grpcCommunicationConfig() {
        return configInner(GRPC_COMMUNICATION_SECTION_ID);
    }
}