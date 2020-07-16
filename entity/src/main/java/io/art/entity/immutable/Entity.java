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

package io.art.entity.immutable;

import com.google.common.collect.*;
import io.art.core.caster.*;
import io.art.core.checker.*;
import io.art.core.lazy.*;
import io.art.entity.builder.*;
import io.art.entity.constants.*;
import io.art.entity.constants.ValueType.*;
import io.art.entity.exception.*;
import io.art.entity.mapper.*;
import lombok.*;
import static com.google.common.collect.ImmutableMap.*;
import static io.art.core.caster.Caster.*;
import static io.art.core.constants.StringConstants.*;
import static io.art.core.factory.CollectionsFactory.*;
import static io.art.core.lazy.LazyValue.*;
import static io.art.entity.constants.ExceptionMessages.*;
import static io.art.entity.constants.ValueType.*;
import static io.art.entity.constants.ValueType.PrimitiveType.BOOL;
import static io.art.entity.constants.ValueType.PrimitiveType.DOUBLE;
import static io.art.entity.constants.ValueType.PrimitiveType.FLOAT;
import static io.art.entity.constants.ValueType.PrimitiveType.INT;
import static io.art.entity.constants.ValueType.PrimitiveType.LONG;
import static io.art.entity.constants.ValueType.PrimitiveType.STRING;
import static io.art.entity.factory.PrimitivesFactory.*;
import static io.art.entity.immutable.Value.*;
import static java.util.Objects.*;
import static java.util.function.Function.*;
import static java.util.stream.Collectors.*;
import java.util.*;
import java.util.function.*;

@RequiredArgsConstructor
public class Entity implements Value {
    @Getter
    private final ValueType type = ENTITY;
    private final Set<Primitive> fields;
    private final Function<Primitive, ? extends Value> valueProvider;

    public static EntityBuilder entityBuilder() {
        return new EntityBuilder();
    }

    public <T> ImmutableMap<Primitive, T> map(ValueToModelMapper<T, ? extends Value> mapper) {
        return fields.stream().collect(toImmutableMap(identity(), key -> mapper.map(cast(valueProvider.apply(key)))));
    }


    public <T> ImmutableMap<Primitive, ? extends Value> copyToMap() {
        return fields.stream().collect(toImmutableMap(identity(), key -> cast(valueProvider.apply(key))));
    }


    public <K, V> ImmutableMap<K, V> copyToPrimitiveMap(ValueToModelMapper<V, ? extends Value> mapper) {
        return fields.stream().collect(toImmutableMap(key -> cast(key.getValue()), key -> mapper.map(cast(valueProvider.apply(key)))));
    }

    public <T> ImmutableMap<String, T> copyToStringMap(ValueToModelMapper<T, ? extends Value> mapper) {
        return fields.stream().collect(toImmutableMap(Primitive::getString, key -> mapper.map(cast(valueProvider.apply(key)))));
    }

    public <T> ImmutableMap<Integer, T> copyToIntMap(ValueToModelMapper<T, ? extends Value> mapper) {
        return fields.stream().collect(toImmutableMap(Primitive::getInt, key -> mapper.map(cast(valueProvider.apply(key)))));
    }

    public <T> ImmutableMap<Long, T> copyToLongMap(ValueToModelMapper<T, ? extends Value> mapper) {
        return fields.stream().collect(toImmutableMap(Primitive::getLong, key -> mapper.map(cast(valueProvider.apply(key)))));
    }

    public <T> ImmutableMap<Double, T> copyToDoubleMap(ValueToModelMapper<T, ? extends Value> mapper) {
        return fields.stream().collect(toImmutableMap(Primitive::getDouble, key -> mapper.map(cast(valueProvider.apply(key)))));
    }

    public <T> ImmutableMap<Float, T> copyToFloatMap(ValueToModelMapper<T, ? extends Value> mapper) {
        return fields.stream().collect(toImmutableMap(Primitive::getFloat, key -> mapper.map(cast(valueProvider.apply(key)))));
    }

    public <T> ImmutableMap<Boolean, T> copyToBoolMap(ValueToModelMapper<T, ? extends Value> mapper) {
        return fields.stream().collect(toImmutableMap(Primitive::getBool, key -> mapper.map(cast(valueProvider.apply(key)))));
    }


    public <T> Map<String, T> asStringMap(ValueToModelMapper<T, ? extends Value> mapper) {
        return new ProxyMap<>(STRING, mapper);
    }

    public <T> Map<Integer, T> asIntMap(ValueToModelMapper<T, ? extends Value> mapper) {
        return new ProxyMap<>(INT, mapper);
    }

    public <T> Map<Double, T> asDoubleMap(ValueToModelMapper<T, ? extends Value> mapper) {
        return new ProxyMap<>(DOUBLE, mapper);
    }

    public <T> Map<Float, T> asFloatMap(ValueToModelMapper<T, ? extends Value> mapper) {
        return new ProxyMap<>(FLOAT, mapper);
    }

    public <T> Map<Boolean, T> asBoolMap(ValueToModelMapper<T, ? extends Value> mapper) {
        return new ProxyMap<>(BOOL, mapper);
    }

    public <T> Map<Long, T> asLongMap(ValueToModelMapper<T, ? extends Value> mapper) {
        return new ProxyMap<>(LONG, mapper);
    }


    public Value get(String key) {
        return get(stringPrimitive(key));
    }

    public Value get(Long key) {
        return get(longPrimitive(key));
    }

    public Value get(Integer key) {
        return get(intPrimitive(key));
    }

    public Value get(Boolean key) {
        return get(boolPrimitive(key));
    }

    public Value get(Double key) {
        return get(doublePrimitive(key));
    }

    public Value get(Float key) {
        return get(floatPrimitive(key));
    }

    public Value get(Byte key) {
        return get(bytePrimitive(key));
    }

    public Value get(Primitive primitive) {
        return valueProvider.apply(primitive);
    }


    public <T, V extends Value> T map(String key, ValueToModelMapper<T, V> mapper) {
        return map(stringPrimitive(key), mapper);
    }

    public <T, V extends Value> T map(Long key, ValueToModelMapper<T, V> mapper) {
        return map(longPrimitive(key), mapper);
    }

    public <T, V extends Value> T map(Integer key, ValueToModelMapper<T, V> mapper) {
        return map(intPrimitive(key), mapper);
    }

    public <T, V extends Value> T map(Double key, ValueToModelMapper<T, V> mapper) {
        return map(doublePrimitive(key), mapper);
    }

    public <T, V extends Value> T map(Boolean key, ValueToModelMapper<T, V> mapper) {
        return map(boolPrimitive(key), mapper);
    }

    public <T, V extends Value> T map(Byte key, ValueToModelMapper<T, V> mapper) {
        return map(bytePrimitive(key), mapper);
    }

    public <T, V extends Value> T map(Float key, ValueToModelMapper<T, V> mapper) {
        return map(floatPrimitive(key), mapper);
    }

    public <T, V extends Value> T map(Primitive primitive, ValueToModelMapper<T, V> mapper) {
        if (isNull(mapper)) throw new ValueMappingException(MAPPER_IS_NULL);
        return mapper.map(cast(get(primitive)));
    }


    public Value find(String key) {
        if (EmptinessChecker.isEmpty(key)) {
            return null;
        }
        Queue<String> sections = queueOf(key.split(ESCAPED_DOT));
        Entity entity = this;
        Value value = null;
        String section;
        while ((section = sections.poll()) != null) {
            value = entity.get(section);
            if (Value.isEmpty(value)) return null;
            if (!isEntity(value)) {
                if (sections.size() > 1) return null;
                return value;
            }
            entity = asEntity(value);
        }
        return value;
    }

    public <T, V extends Value> T mapNested(String key, ValueToModelMapper<T, V> mapper) {
        if (isNull(mapper)) throw new ValueMappingException(MAPPER_IS_NULL);
        return mapper.map(cast(find(key)));
    }


    @Override
    public boolean isEmpty() {
        return EmptinessChecker.isEmpty(fields);
    }

    @RequiredArgsConstructor
    public class ProxyMap<K, V> implements Map<K, V> {
        private final PrimitiveType primitiveType;
        private final ValueToModelMapper<V, ? extends Value> mapper;
        private final LazyValue<ImmutableMap<Primitive, V>> evaluated = lazy(() -> Entity.this.map(mapper));

        @Override
        public int size() {
            return fields.size();
        }

        @Override
        public boolean isEmpty() {
            return fields.isEmpty();
        }

        @Override
        public boolean containsKey(Object key) {
            return fields.contains(stringPrimitive(cast(key)));
        }

        @Override
        public boolean containsValue(Object value) {
            return evaluated.get().containsValue(value);
        }

        @Override
        public V get(Object key) {
            return mapper.map(cast(valueProvider.apply(new Primitive(key, primitiveType))));
        }

        @Override
        public V put(K key, V value) {
            throw new ValueMethodNotImplementedException("put");
        }

        @Override
        public V remove(Object key) {
            throw new ValueMethodNotImplementedException("remove");
        }

        @Override
        public void putAll(Map<? extends K, ? extends V> map) {
            throw new ValueMethodNotImplementedException("putAll");
        }

        @Override
        public void clear() {
            throw new ValueMethodNotImplementedException("clear");
        }

        @Override
        public Set<K> keySet() {
            return fields.stream().map(Caster::<K>cast).collect(toSet());
        }

        @Override
        public Collection<V> values() {
            return map(mapper).values();
        }

        @Override
        public Set<Entry<K, V>> entrySet() {
            ImmutableMap<K, V> map = copyToPrimitiveMap(mapper);
            return map.entrySet();
        }
    }
}