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

package ru.art.kafka.consumer.launcher;

import lombok.experimental.UtilityClass;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.Deserializer;
import ru.art.kafka.consumer.configuration.KafkaConsumerConfiguration;
import ru.art.kafka.consumer.configuration.KafkaConsumerModuleConfiguration;
import ru.art.kafka.consumer.specification.KafkaConsumerServiceSpecification;
import static java.lang.String.join;
import static org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG;
import static ru.art.core.checker.CheckerForEmptiness.isEmpty;
import static ru.art.core.constants.StringConstants.COMMA;
import static ru.art.kafka.consumer.module.KafkaConsumerModule.kafkaConsumerModule;
import static ru.art.kafka.consumer.module.KafkaConsumerModule.kafkaConsumerServices;
import static ru.art.service.ServiceController.executeServiceMethod;
import java.util.List;
import java.util.Properties;

@UtilityClass
public class KafkaConsumerLauncher {
    public static void launchKafkaConsumer(String serviceId) {
        KafkaConsumerModuleConfiguration moduleConfiguration = kafkaConsumerModule();
        if (isEmpty(moduleConfiguration.getKafkaConsumerConfiguration())) {
            return;
        }
        awaitKafkaConsumer(moduleConfiguration.getKafkaConsumerConfiguration(), serviceId);
    }

    private static void awaitKafkaConsumer(KafkaConsumerConfiguration configuration, String serviceId) {
        configuration.getExecutor().submit(() -> startKafkaConsumer(configuration, serviceId));
    }

    private static void startKafkaConsumer(KafkaConsumerConfiguration configuration, String serviceId) {
        List<KafkaConsumerServiceSpecification> kafkaConsumerServiceSpecifications = kafkaConsumerServices();
        Deserializer<Object> keyDeserializer = configuration.getKeyDeserializer();
        Deserializer<Object> valueDeserializer = configuration.getValueDeserializer();
        KafkaConsumer<?, ?> consumer = new KafkaConsumer<>(createProperties(configuration), keyDeserializer, valueDeserializer);
        consumer.subscribe(configuration.getTopics());
        try {
            //noinspection InfiniteLoopStatement
            while (true) {
                ConsumerRecords<?, ?> poll = consumer.poll(configuration.getPollTimeout());
                for (ConsumerRecord<?, ?> record : poll) {
                    kafkaConsumerServiceSpecifications.stream()
                            .filter(specification -> specification.getServiceId().equals(serviceId))
                            .forEach(specification -> executeServiceMethod(serviceId, record.topic(), record));
                }
            }
        } finally {
            consumer.close();
        }
    }

    private static Properties createProperties(KafkaConsumerConfiguration configuration) {
        Properties properties = new Properties();
        properties.put(BOOTSTRAP_SERVERS_CONFIG, join(COMMA, configuration.getBrokers()));
        properties.put(GROUP_ID_CONFIG, configuration.getGroupId());
        properties.putAll(configuration.getAdditionalProperties());
        return properties;
    }
}
