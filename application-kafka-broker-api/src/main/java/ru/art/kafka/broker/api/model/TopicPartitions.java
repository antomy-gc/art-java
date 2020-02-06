package ru.art.kafka.broker.api.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import ru.art.service.validation.Validatable;
import ru.art.service.validation.Validator;

import static ru.art.service.validation.ValidationExpressions.moreThanInt;
import static ru.art.service.validation.ValidationExpressions.notEmptyString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class TopicPartitions implements Validatable {
    private String topic;
    private Integer numberOfPartitions;

    @Override
    public void onValidating(Validator validator) {
        validator.validate("topic", topic, notEmptyString());
        validator.validate("numberOfPartitions", numberOfPartitions, moreThanInt(0));
    }
}
