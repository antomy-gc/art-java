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

package io.art.core.configuration;

import io.art.core.context.*;
import io.art.core.network.provider.*;
import io.art.core.provider.*;
import lombok.*;
import static io.art.core.constants.ContextConstants.*;
import static java.nio.charset.StandardCharsets.*;
import static java.util.Locale.Category.*;
import static java.util.Locale.*;
import static java.util.Optional.*;
import java.io.*;
import java.net.*;
import java.nio.charset.*;
import java.security.*;
import java.util.*;

public interface ContextInitialConfiguration {
    String getMainModuleId();

    String getModuleJarName();

    Charset getCharset();

    boolean isUnloadModulesOnShutdown();

    String getPrimaryIpAddress();

    Map<String, String> getIpAddresses();

    Locale getLocale();

    Optional<PreconfiguredModuleProvider> getPreconfiguredModulesProvider();

    class ContextInitialDefaultConfiguration implements ContextInitialConfiguration {
        @Getter
        private final String mainModuleId;
        @Getter
        private final Charset charset = UTF_8;
        @Getter
        private final boolean unloadModulesOnShutdown = true;
        @Getter
        private final String primaryIpAddress = IpAddressProvider.getIpAddress();
        @Getter
        private final Map<String, String> ipAddresses = IpAddressProvider.getIpAddresses();
        @Getter
        private final Locale locale = getDefault(FORMAT);
        @Getter
        private final String moduleJarName = ofNullable(Context.class.getProtectionDomain())
                .map(ProtectionDomain::getCodeSource)
                .map(CodeSource::getLocation)
                .map(URL::getPath)
                .map(File::new)
                .map(File::getPath)
                .orElse(DEFAULT_MODULE_JAR);
        private PreconfiguredModuleProvider preconfiguredModulesProvider;

        public ContextInitialDefaultConfiguration() {
            this.mainModuleId = DEFAULT_MAIN_MODULE_ID;
        }

        public ContextInitialDefaultConfiguration(PreconfiguredModuleProvider preconfiguredModulesProvider) {
            this.mainModuleId = DEFAULT_MAIN_MODULE_ID;
        }

        public ContextInitialDefaultConfiguration(String applicationModuleId, PreconfiguredModuleProvider preconfiguredModulesProvider) {
            this.mainModuleId = applicationModuleId;
            this.preconfiguredModulesProvider = preconfiguredModulesProvider;
        }

        @Override
        public Optional<PreconfiguredModuleProvider> getPreconfiguredModulesProvider() {
            return ofNullable(preconfiguredModulesProvider);
        }
    }
}