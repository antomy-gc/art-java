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

package ru.art.xml.descriptor;

import lombok.*;
import ru.art.entity.*;
import ru.art.entity.XmlEntity.*;
import ru.art.xml.exception.*;
import static java.nio.charset.StandardCharsets.*;
import static java.util.Collections.*;
import static java.util.Objects.*;
import static javax.xml.stream.XMLStreamConstants.*;
import static lombok.AccessLevel.*;
import static ru.art.core.checker.CheckerForEmptiness.isEmpty;
import static ru.art.core.extension.FileExtensions.*;
import static ru.art.core.factory.CollectionsFactory.*;
import static ru.art.entity.XmlEntity.*;
import static ru.art.xml.constants.XmlMappingExceptionMessages.*;
import static ru.art.xml.module.XmlModule.*;
import javax.xml.stream.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;


@NoArgsConstructor(access = PRIVATE)
public class XmlEntityReader {
    public static XmlEntity readXml(XMLInputFactory xmlInputFactory, String xml) {
        if (isNull(xmlInputFactory)) throw new XmlMappingException(XML_FACTORY_IS_NULL);
        if (isEmpty(xml)) return xmlEntityBuilder().create();
        try {
            InputStream is = new ByteArrayInputStream(xml.getBytes(UTF_8));
            XMLStreamReader parser = xmlInputFactory.createXMLStreamReader(is);
            XmlEntityBuilder root = getRootElement(parser);
            return root.create();
        } catch (Throwable e) {
            throw new XmlMappingException(e);
        }
    }

    public static XmlEntity readXml(String xml) {
        return readXml(xmlModule().getXmlInputFactory(), xml);
    }

    public static XmlEntity readXml(Path path) {
        return readXml(readFile(path));
    }

    public static XmlEntity readXml(File file) {
        return readXml(readFile(file.getAbsolutePath()));
    }

    private static XmlEntityBuilder getRootElement(XMLStreamReader parser) throws XMLStreamException {
        while (parser.next() == COMMENT) {
            if (!parser.hasNext()) {
                throw new XmlMappingException(INCORRECT_XML_STRUCTURE);
            }
        }
        String prefix = parser.getPrefix();
        Map<String, String> attributes = getAttributes(parser);
        Map<String, String> namespaces = getNamespaces(parser);
        XmlEntityBuilder rootElement = parseXml(parser);
        rootElement.stringAttributeFields(attributes);
        rootElement.namespaceFields(namespaces);
        rootElement.prefix(prefix);
        return rootElement;
    }

    private static XmlEntityBuilder parseXml(XMLStreamReader parser) throws XMLStreamException {
        XmlEntityBuilder xmlEntityBuilder = xmlEntityBuilder();

        int eventType;

        while (parser.hasNext()) {

            eventType = parser.next();

            if (parser.isWhiteSpace()) {
                continue;
            }

            switch (eventType) {
                case START_ELEMENT:
                    Map<String, String> attributes = getAttributes(parser);
                    Map<String, String> namespaces = getNamespaces(parser);
                    XmlEntityBuilder child = parseXml(parser);
                    child.stringAttributeFields(attributes);
                    child.namespaceFields(namespaces);
                    xmlEntityBuilder.child(child.create());
                    break;

                case CDATA:
                case SPACE:
                case CHARACTERS:
                    xmlEntityBuilder.value(parser.getText());
                    break;

                case END_ELEMENT:
                    xmlEntityBuilder.tag(parser.getLocalName());
                    xmlEntityBuilder.prefix(parser.getPrefix());
                    xmlEntityBuilder.namespace(parser.getNamespaceURI());
                    return xmlEntityBuilder;

                case END_DOCUMENT:
                    return xmlEntityBuilder;
            }
        }

        throw new XmlMappingException(XML_FILE_HASNT_END_DOCUMENT_TAG);
    }

    private static Map<String, String> getAttributes(XMLStreamReader parser) {
        int attributeCount = parser.getAttributeCount();
        if (attributeCount == 0) {
            return emptyMap();
        }

        Map<String, String> attributes = mapOf(attributeCount);
        for (int i = 0; i < attributeCount; i++) {
            attributes.put(parser.getAttributeLocalName(i), parser.getAttributeValue(i));
        }
        return attributes;
    }

    private static Map<String, String> getNamespaces(XMLStreamReader parser) {
        int namespaceCount = parser.getNamespaceCount();
        if (namespaceCount == 0) {
            return emptyMap();
        }

        Map<String, String> namespaces = mapOf(namespaceCount);
        for (int i = 0; i < namespaceCount; i++) {
            namespaces.put(parser.getNamespacePrefix(i), parser.getNamespaceURI(i));
        }
        return namespaces;
    }
}