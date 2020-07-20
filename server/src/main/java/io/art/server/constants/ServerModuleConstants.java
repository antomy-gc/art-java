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

package io.art.server.constants;

public interface ServerModuleConstants {
    String REQUEST_EVENT = "serviceRequest";
    String RESPONSE_EVENT = "serviceResponse";

    enum RequestValidationPolicy {
        VALIDATABLE,
        NOT_NULL,
        NON_VALIDATABLE
    }

    enum ServiceExecutionFeatureTarget {
        SERVICE,
        METHOD
    }

    enum ServiceMethodImplementationMode {
        CONSUMER,
        PRODUCER,
        HANDLER
    }

    enum ServiceMethodProcessingMode {
        BLOCKING,
        REACTIVE_MONO,
        REACTIVE_FLUX
    }

    interface ValidationExpressionType {
        String BETWEEN_DOUBLE = "BETWEEN_DOUBLE";
        String BETWEEN_INT = "BETWEEN_INT";
        String BETWEEN_LONG = "BETWEEN_LONG";
        String CONTAINS = "CONTAINS";
        String EQUALS = "EQUALS";
        String NOT_EMPTY_COLLECTION = "NOT_EMPTY_COLLECTION";
        String NOT_EMPTY_MAP = "NOT_EMPTY_MAP";
        String NOT_EMPTY_STRING = "NOT_EMPTY_STRING";
        String NOT_EQUALS = "NOT_EQUALS";
        String NOT_NULL = "NOT_NULL";
    }

    interface LoggingMessages {
        String EXECUTION_SERVICE_MESSAGE = "Executing service: ''{0}.{1}'' with request: ''{2}''";
        String SERVICE_FAILED_MESSAGE = "Service ''{0}.{1}'' execution failed with error: ''{2}: {3}\n{4}''";
        String SERVICE_REGISTRATION_MESSAGE = "Registering service: ''{0}'' with specification class: ''{1}''";
    }

    interface ExceptionsMessages {
        String UNKNOWN_SERVICE_METHOD_IMPLEMENTATION_MODE = "Unknown service method implementation mode: ''{0}''";
        String UNKNOWN_PROCESSING_MODE = "Unknown processing mode: ''{0}''";
        String INVALID_CHANNEL_PROCESSING_MODE = "Invalid processing mode: ''{0}'' for channel processing";
        String NOT_BETWEEN_VALIDATION_ERROR = "Validation error. ''{0}'' = ''{1}'' not between ''{2,number,#}'' and ''{3,number,#}''";
        String NOT_EQUALS_VALIDATION_ERROR = "Validation error. ''{0}'' = ''{1}'' is not equals to ''{2}''";
        String NOT_CONTAINS_VALIDATION_ERROR = "Validation error. ''{0}'' = ''{1}'' is not contains to ''{2}''";
        String EQUALS_VALIDATION_ERROR = "Validation error. ''{0}'' = ''{1}'' is equals to ''{2}''";
        String EMPTY_VALIDATION_ERROR = "Validation error. ''{0}'' is empty";
        String NULL_VALIDATION_ERROR = "Validation error. ''{0}'' is null";
    }

}