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

package io.art.entity.constants;

import io.art.entity.exception.*;
import static io.art.entity.constants.EntityConstants.ExceptionMessages.*;
import static java.text.MessageFormat.*;

public interface EntityConstants {
    interface ExceptionMessages {
        String UNKNOWN_VALUE_TYPE = "Unknown value type: ''{0}''";
        String TUPLE_NOT_SUPPORTED_VALUE_TYPE = "Value type: ''{0}'' not support for tuples";
        String NOT_PRIMITIVE_TYPE = "Not primitive type: ''{0}''";
        String XML_TAG_IS_EMPTY = "Xml tag is empty";
    }

    enum ValueType {
        ENTITY,
        ARRAY,
        XML,
        STRING,
        LONG,
        DOUBLE,
        FLOAT,
        INT,
        BOOL,
        BYTE,
        BINARY;

        public static PrimitiveType asPrimitiveType(ValueType valueType) {
            switch (valueType) {
                case STRING:
                    return PrimitiveType.STRING;
                case LONG:
                    return PrimitiveType.LONG;
                case DOUBLE:
                    return PrimitiveType.DOUBLE;
                case INT:
                    return PrimitiveType.INT;
                case BOOL:
                    return PrimitiveType.BOOL;
                case BYTE:
                    return PrimitiveType.BYTE;
                case FLOAT:
                    return PrimitiveType.FLOAT;
                default:
                    throw new ValueMappingException(format(NOT_PRIMITIVE_TYPE, valueType));
            }
        }

        public enum PrimitiveType {
            STRING(String.class.getName()),
            LONG(Long.class.getName()),
            DOUBLE(Double.class.getName()),
            FLOAT(Float.class.getName()),
            INT(Integer.class.getName()),
            BOOL(Boolean.class.getName()),
            BYTE(Byte.class.getName());

            private final String className;

            PrimitiveType(String className) {
                this.className = className;
            }

            public static PrimitiveType parseClassName(String className) {
                if (STRING.getClassName().equalsIgnoreCase(className)) return STRING;
                if (LONG.getClassName().equalsIgnoreCase(className)) return LONG;
                if (DOUBLE.getClassName().equalsIgnoreCase(className)) return DOUBLE;
                if (INT.getClassName().equalsIgnoreCase(className)) return INT;
                if (BOOL.getClassName().equalsIgnoreCase(className)) return BOOL;
                if (BYTE.getClassName().equalsIgnoreCase(className)) return BYTE;
                if (FLOAT.getClassName().equalsIgnoreCase(className)) return FLOAT;
                throw new ValueMappingException(format(NOT_PRIMITIVE_TYPE, className));
            }

            public static ValueType asValueType(PrimitiveType primitiveType) {
                switch (primitiveType) {
                    case STRING:
                        return ValueType.STRING;
                    case LONG:
                        return ValueType.LONG;
                    case DOUBLE:
                        return ValueType.DOUBLE;
                    case INT:
                        return ValueType.INT;
                    case BOOL:
                        return ValueType.BOOL;
                    case BYTE:
                        return ValueType.BYTE;
                    case FLOAT:
                        return ValueType.FLOAT;
                    default:
                        throw new ValueMappingException(format(NOT_PRIMITIVE_TYPE, primitiveType));
                }
            }

            public String getClassName() {
                return className;
            }
        }

        public enum XmlValueType {
            STRING,
            CDATA
        }
    }

}