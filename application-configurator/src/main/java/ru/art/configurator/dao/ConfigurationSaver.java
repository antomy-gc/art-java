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

package ru.art.configurator.dao;

import lombok.NoArgsConstructor;
import ru.art.entity.Entity;
import ru.art.entity.Entity.EntityBuilder;
import ru.art.entity.Value;
import ru.art.rocks.db.dao.RocksDbPrimitiveDao;
import static java.lang.String.join;
import static lombok.AccessLevel.PRIVATE;
import static ru.art.configurator.constants.ConfiguratorDbConstants.MODULE_KEYS;
import static ru.art.configurator.constants.ConfiguratorDbConstants.PROFILE_KEYS;
import static ru.art.core.constants.StringConstants.COLON;
import static ru.art.entity.constants.ValueType.ENTITY;
import static ru.art.rocks.db.dao.RocksDbValueDao.putAsProtobuf;
import java.util.Set;

@NoArgsConstructor(access = PRIVATE)
class ConfigurationSaver {
    static void saveProfileModulesConfiguration(String profileId, Entity profileConfigEntity) {
        RocksDbPrimitiveDao.add(PROFILE_KEYS, profileId);
        EntityBuilder profileConfigBuilder = Entity.entityBuilder();
        Set<String> profileFields = profileConfigEntity.getFieldNames();
        for (String profileField : profileFields) {
            Value profileFieldConfig = profileConfigEntity.getValue(profileField);
            if (profileFieldConfig.getType().equals(ENTITY)) {
                String moduleKey = join(COLON, profileId, profileField);
                RocksDbPrimitiveDao.add(MODULE_KEYS, moduleKey);
                saveModuleConfiguration(moduleKey, profileConfigEntity, profileField);
                continue;
            }
            profileConfigBuilder = profileConfigBuilder.valueField(profileField, profileFieldConfig);
        }

        Entity profileConfig = profileConfigBuilder.build();
        if (profileConfig.isEmpty()) {
            return;
        }

        putAsProtobuf(profileId, profileConfig);
    }

    private static void saveModuleConfiguration(String moduleKey, Entity profileConfigEntity, String profileId) {
        Entity moduleConfig = profileConfigEntity.getEntity(profileId);
        if (moduleConfig.isEmpty()) {
            return;
        }
        putAsProtobuf(moduleKey, moduleConfig);
    }
}