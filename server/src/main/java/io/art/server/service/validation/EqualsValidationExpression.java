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

package io.art.server.service.validation;

import static io.art.server.constants.ServerModuleConstants.ExceptionsMessages.*;
import static io.art.server.constants.ServerModuleConstants.ValidationExpressionType.*;
import static java.text.MessageFormat.*;

class EqualsValidationExpression extends ValidationExpression<Object> {
    private final Object other;

    EqualsValidationExpression(Object other) {
        super(EQUALS);
        this.other = other;
    }

    EqualsValidationExpression(Object other, String pattern) {
        super(EQUALS);
        this.other = other;
        this.pattern = pattern;
    }

    @Override
    public boolean evaluate(String fieldName, Object value) {
        return super.evaluate(fieldName, value) && value.equals(other);
    }

    @Override
    public String getValidationErrorMessage() {
        return format(NOT_EQUALS_VALIDATION_ERROR, fieldName, value, other);
    }
}