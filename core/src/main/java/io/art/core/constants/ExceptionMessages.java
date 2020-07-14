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

package io.art.core.constants;

public interface ExceptionMessages {
    String EXCEPTION_WRAPPER_ACTION_IS_NULL = "Exception wrapped action is null";
    String EXCEPTION_WRAPPER_FACTORY_IS_NULL = "ExceptionFactory is null";
    String CONTEXT_INITIAL_CONFIGURATION_IS_NULL = "ContextInitialConfiguration is null";
    String MODULE_ID_IS_NULL = "ModuleId is null";
    String MODULE_HAS_NOT_STATE = "Module ''{0}'' has not state";
    String CUSTOM_MODULE_CONFIGURATION_IS_NULL = "CustomModuleConfiguration is null";
    String BUILDER_VALIDATOR_HAS_NEXT_ERRORS = "Builder validator for ''{0}'' has next error fields:";
    String COULD_NOT_FIND_AVAILABLE_PORT_AFTER_ATTEMPTS = "Could not find an available {0} port in the range [{1,number,#}, {2,number,#}] after {3,number,#} attempts";
    String COULD_NOT_FIND_AVAILABLE_PORTS_IN_THE_RANGE = "Could not find {0,number,#} available {1} ports in the range [{2,number,#}, {3,number,#}]";
    String MIME_TYPE_MUST_NOT_BE_EMPTY = "Type must not be empty";
    String MIME_SUBTYPE_MUST_NOT_BE_EMPTY = "Subtype must not be empty";
    String MIME_DOES_NOT_CONTAIN_SLASH = "Does not contain '/'";
    String MIME_DOES_NOT_CONTAIN_SUBTYPE = "Does not contain subtype after '/'";
    String WILDCARD_TYPE_IS_LEGAL_ONLY_FOR_ALL_MIME_TYPES = "Wildcard type is legal only in '*/*' (all mime types)";
    String INVALID_TOKEN = "Invalid token character ''{0}'' in token ''{1}''";
    String PARAMETER_ATTRIBUTE_MUST_NOT_BE_EMPTY = "Parameter 'attribute' must not be empty";
    String PARAMETER_VALUE_MUST_NOT_BE_EMPTY = "Parameter 'value' must not be empty";
    String EXCEPTION_CONSUMER_IS_NULL = "Exception consumer is null";
    String EXCEPTION_WRAPPER_IS_NULL = "Exception wrapper is null";
    String EXCEPTION_HANDLER_IS_NULL = "Exception handler is null";
}