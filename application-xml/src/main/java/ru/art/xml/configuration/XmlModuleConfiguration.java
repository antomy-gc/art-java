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

package ru.art.xml.configuration;

import lombok.Getter;
import ru.art.core.module.ModuleConfiguration;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;

public interface XmlModuleConfiguration extends ModuleConfiguration {
    XMLInputFactory getXmlInputFactory();

    XMLOutputFactory getXmlOutputFactory();

    @Getter
    class XmlModuleDefaultConfiguration implements XmlModuleConfiguration {
        private final XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        private final XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newFactory();
    }
}