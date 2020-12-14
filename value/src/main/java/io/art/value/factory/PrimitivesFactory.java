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

package io.art.value.factory;

import io.art.value.immutable.*;
import lombok.experimental.*;
import static io.art.core.checker.NullityChecker.let;
import static io.art.core.constants.StringConstants.EMPTY_STRING;
import static io.art.value.constants.ValueConstants.ValueType.PrimitiveType.*;

@UtilityClass
public class PrimitivesFactory {
    public static Primitive stringPrimitive(String value) {
        return new Primitive(value, STRING);
    }

    public static Primitive longPrimitive(Long value) {
        return new Primitive(value, LONG);
    }

    public static Primitive intPrimitive(Integer value) {
        return new Primitive(value, INT);
    }
    
    public static Primitive shortPrimitive(Short value) {
        return new Primitive(value, INT);
    }
    
    public static Primitive charPrimitive(Character value) {
        return new Primitive(let(value, character -> EMPTY_STRING + character, (String) null) , STRING);
    }

    public static Primitive boolPrimitive(Boolean value) {
        return new Primitive(value, BOOL);
    }

    public static Primitive doublePrimitive(Double value) {
        return new Primitive(value, DOUBLE);
    }

    public static Primitive bytePrimitive(Byte value) {
        return new Primitive(value, BYTE);
    }

    public static Primitive floatPrimitive(Float value) {
        return new Primitive(value, FLOAT);
    }
}
