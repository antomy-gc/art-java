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

package ru.art.generator.mapper.annotation;

import java.lang.annotation.*;

/**
 * Annotation for mapper generator.
 * Fields marked with GenerationException annotation contains parameters
 * that haven't been generated.
 * Variable notGeneratedFields contains names of fields that haven't been generated.
 */
@Target(ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface GenerationException {
    String notGeneratedFields();
}
