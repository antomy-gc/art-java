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

package io.art.core.extension;

import lombok.experimental.*;
import java.util.*;
import java.util.function.*;

@UtilityClass
public class EqualsCheckingExtensions {
    public static <T> T ifEquals(T val, T pattern, T ifEquals) {
        return Objects.equals(val, pattern) ? ifEquals : val;
    }

    public static <T> T ifNotEquals(T val, T pattern, T ifNotEquals) {
        return !Objects.equals(val, pattern) ? ifNotEquals : val;
    }

    public static <T> T ifEquals(T val, T pattern, Supplier<T> ifEquals) {
        return Objects.equals(val, pattern) ? ifEquals.get() : val;
    }

    public static <T> T ifNotEquals(T val, T pattern, Supplier<T> ifNotEquals) {
        return !Objects.equals(val, pattern) ? ifNotEquals.get() : val;
    }
}
