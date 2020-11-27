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

package io.art.value.tuple.schema;

import com.google.common.collect.*;
import io.art.core.builder.*;
import io.art.core.factory.*;
import io.art.value.constants.ValueConstants.*;
import io.art.value.exception.*;
import io.art.value.immutable.*;
import io.art.value.immutable.Value;
import lombok.*;
import static com.google.common.collect.ImmutableList.*;
import static io.art.core.caster.Caster.*;
import static io.art.core.checker.NullityChecker.*;
import static io.art.core.factory.ArrayFactory.dynamicArrayOf;
import static io.art.core.factory.ArrayFactory.immutableArrayBuilder;
import static io.art.value.constants.ValueConstants.ExceptionMessages.*;
import static io.art.value.constants.ValueConstants.ValueType.*;
import static io.art.value.immutable.Value.*;
import static java.text.MessageFormat.format;
import java.util.*;

@Getter
public class EntitySchema extends ValueSchema {
    private final List<EntityFieldSchema> fieldsSchema;

    EntitySchema(Entity entity) {
        super(ENTITY);
        Set<Primitive> keys = entity.asMap().keySet();
        ImmutableListBuilder<EntityFieldSchema> schemaBuilder = immutableArrayBuilder();
        for (Primitive key : keys) {
            if (valueIsNull(key)) continue;
            Value value = entity.get(key);
            if (valueIsNull(value)) continue;
            let(fromValue(value), schema -> schemaBuilder.add(new EntityFieldSchema(value.getType(), key.getString(), schema)));
        }
        fieldsSchema = schemaBuilder.build();
    }

    private EntitySchema(ImmutableList<EntityFieldSchema> fieldsSchema) {
        super(ENTITY);
        this.fieldsSchema = fieldsSchema;
    }

    @Override
    public List<?> toTuple() {
        List<?> tuple = ArrayFactory.dynamicArray(getType().ordinal());
        fieldsSchema.stream().map(EntityFieldSchema::toTuple).forEach(value -> tuple.add(cast(value)));
        return tuple;
    }

    public static EntitySchema fromTuple(List<?> tuple) {
        return new EntitySchema(tuple.stream()
                .skip(1)
                .map(element -> (List<?>) element)
                .map(EntityFieldSchema::fromTuple)
                .collect(toImmutableList()));
    }

    @Getter
    @EqualsAndHashCode
    public static class EntityFieldSchema {
        private final String name;
        private final ValueType type;
        private final ValueSchema schema;

        EntityFieldSchema(ValueType type, String name, ValueSchema schema) {
            this.type = type;
            this.name = name;
            this.schema = schema;
        }

        public List<?> toTuple() {
            return dynamicArrayOf(type.ordinal(), name, schema.toTuple());
        }

        public static EntityFieldSchema fromTuple(List<?> tuple) {
            ValueType type = ValueType.values()[((Integer) tuple.get(0))];
            String name = (String) tuple.get(1);

            if (isPrimitiveType(type)) {
                return new EntityFieldSchema(type, name, new ValueSchema(type));
            }

            switch (type) {
                case ENTITY:
                    return new EntityFieldSchema(type, name, EntitySchema.fromTuple((List<?>) tuple.get(2)));
                case ARRAY:
                    return new EntityFieldSchema(type, name, ArraySchema.fromTuple((List<?>) tuple.get(2)));
                case BINARY:
                    return new EntityFieldSchema(type, name, new ValueSchema(type));
            }

            throw new ValueMappingException(format(type.name(), TUPLE_NOT_SUPPORTED_VALUE_TYPE));
        }
    }
}
