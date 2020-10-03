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

package io.art.configurator.source;

import io.art.core.parser.*;
import io.art.core.source.*;
import io.art.entity.immutable.*;
import io.art.entity.mapping.*;
import lombok.*;
import static io.art.configurator.constants.ConfiguratorModuleConstants.ConfigurationSourceType.*;
import static io.art.core.checker.NullityChecker.*;
import static io.art.entity.immutable.Value.*;
import static io.art.entity.mapping.PrimitiveMapping.toString;
import static io.art.entity.mapping.PrimitiveMapping.*;
import static java.util.Objects.*;
import static java.util.stream.Collectors.*;
import java.time.*;
import java.util.*;

@Getter
@RequiredArgsConstructor
public class EntityConfigurationSource implements ConfigurationSource {
    private final ModuleConfigurationSourceType type = ENTITY;
    private final Entity entity;

    @Override
    public Integer getInt(String path) {
        return entity.mapNested(path, toInt);
    }

    @Override
    public Long getLong(String path) {
        return entity.mapNested(path, toLong);
    }

    @Override
    public Boolean getBool(String path) {
        return entity.mapNested(path, toBool);
    }

    @Override
    public Double getDouble(String path) {
        return entity.mapNested(path, toDouble);
    }

    @Override
    public Float getFloat(String path) {
        return let(getDouble(path), Number::floatValue);
    }

    @Override
    public String getString(String path) {
        return entity.mapNested(path, toString);
    }

    @Override
    public Duration getDuration(String path) {
        return let(getString(path), DurationParser::parseDuration);
    }

    @Override
    public ConfigurationSource getNested(String path) {
        return new EntityConfigurationSource(asEntity(entity.find(path)));
    }

    @Override
    public List<Integer> getIntList(String path) {
        return entity.mapNested(path, ArrayMapping.toList(toInt));
    }

    @Override
    public List<Long> getLongList(String path) {
        return entity.mapNested(path, ArrayMapping.toList(toLong));
    }

    @Override
    public List<Boolean> getBoolList(String path) {
        return entity.mapNested(path, ArrayMapping.toList(toBool));
    }

    @Override
    public List<Double> getDoubleList(String path) {
        return entity.mapNested(path, ArrayMapping.toList(toDouble));
    }

    @Override
    public List<String> getStringList(String path) {
        return entity.mapNested(path, ArrayMapping.toList(toString));
    }

    @Override
    public List<Duration> getDurationList(String path) {
        return entity.mapNested(path, ArrayMapping.toList(element -> let(toString.map(asPrimitive(element)), DurationParser::parseDuration)));
    }

    @Override
    public List<ConfigurationSource> getNestedList(String path) {
        return asArray(entity.get(path)).asStream().map(value -> new EntityConfigurationSource(asEntity(value))).collect(toList());
    }

    @Override
    public Set<String> getKeys() {
        return entity.asMap().keySet().stream().map(toString::map).collect(toSet());
    }

    @Override
    public boolean has(String path) {
        return nonNull(entity.find(path));
    }
}