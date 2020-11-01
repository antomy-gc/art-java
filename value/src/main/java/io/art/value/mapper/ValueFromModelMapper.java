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

package io.art.value.mapper;

import io.art.value.immutable.*;
import java.io.*;

public interface ValueFromModelMapper<T, V extends Value> extends Serializable {
    V map(T model);

    interface EntityFromModelMapper<T> extends ValueFromModelMapper<T, Entity> {
    }

    interface ArrayFromModelMapper<T> extends ValueFromModelMapper<T, ArrayValue> {
    }

    interface PrimitiveFromModelMapper<T> extends ValueFromModelMapper<T, Primitive> {
    }

    interface BinaryFromModelMapper<T> extends ValueFromModelMapper<T, BinaryValue> {
    }

    interface XmlFromModelMapper<T> extends ValueFromModelMapper<T, XmlEntity> {
    }

    static <V extends Value> ValueFromModelMapper<V, V> identity() {
        return value -> value;
    }
}