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

package io.art.value.mapping;

import io.art.core.annotation.*;
import io.art.core.lazy.*;
import io.art.value.immutable.*;
import io.art.value.mapper.*;
import io.art.value.mapper.ValueFromModelMapper.*;
import io.art.value.mapper.ValueToModelMapper.*;
import lombok.experimental.*;
import static io.art.core.lazy.LazyValue.*;

@UtilityClass
@UsedByGenerator
public class LazyValueMapping {
    public static <T> ValueFromModelMapper<LazyValue<T>, Value> fromLazy(ValueFromModelMapper<T, Value> valueMapper) {
        return lazy -> valueMapper.map(lazy.get());
    }

    public static <T> ValueToModelMapper<LazyValue<T>, Value> toLazy(ValueToModelMapper<T, Value> valueMapper) {
        return value -> lazy(() -> valueMapper.map(value));
    }

    public static <T> PrimitiveFromModelMapper<LazyValue<T>> fromLazy(PrimitiveFromModelMapper<T> valueMapper) {
        return lazy -> valueMapper.map(lazy.get());
    }

    public static <T> PrimitiveToModelMapper<LazyValue<T>> toLazy(PrimitiveToModelMapper<T> valueMapper) {
        return value -> lazy(() -> valueMapper.map(value));
    }

    public static <T> ArrayFromModelMapper<LazyValue<T>> fromLazy(ArrayFromModelMapper<T> valueMapper) {
        return lazy -> valueMapper.map(lazy.get());
    }

    public static <T> ArrayToModelMapper<LazyValue<T>> toLazy(ArrayToModelMapper<T> valueMapper) {
        return value -> lazy(() -> valueMapper.map(value));
    }

    public static <T> EntityFromModelMapper<LazyValue<T>> fromLazy(EntityFromModelMapper<T> valueMapper) {
        return lazy -> valueMapper.map(lazy.get());
    }

    public static <T> EntityToModelMapper<LazyValue<T>> toLazy(EntityToModelMapper<T> valueMapper) {
        return value -> lazy(() -> valueMapper.map(value));
    }

    public static <T> BinaryFromModelMapper<LazyValue<T>> fromLazy(BinaryFromModelMapper<T> valueMapper) {
        return lazy -> valueMapper.map(lazy.get());
    }

    public static <T> BinaryToModelMapper<LazyValue<T>> toLazy(BinaryToModelMapper<T> valueMapper) {
        return value -> lazy(() -> valueMapper.map(value));
    }
}