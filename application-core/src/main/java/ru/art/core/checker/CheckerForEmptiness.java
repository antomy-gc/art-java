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

package ru.art.core.checker;

import java.util.*;

import static java.util.Objects.*;

public interface CheckerForEmptiness {
    static <T> boolean isNotEmpty(T val) {
        return !isEmpty(val);
    }

    static <T> boolean isEmpty(T val) {
        return isNull(val) || val.toString().trim().isEmpty();
    }

    static <T> boolean isEmpty(T[] content) {
        return isNull(content) || content.length == 0;
    }

    static boolean isEmpty(byte[] content) {
        return isNull(content) || content.length == 0;
    }

    static boolean isEmpty(short[] content) {
        return isNull(content) || content.length == 0;
    }

    static boolean isEmpty(int[] content) {
        return isNull(content) || content.length == 0;
    }

    static boolean isEmpty(long[] content) {
        return isNull(content) || content.length == 0;
    }

    static boolean isEmpty(float[] content) {
        return isNull(content) || content.length == 0;
    }

    static boolean isEmpty(double[] content) {
        return isNull(content) || content.length == 0;
    }

    static boolean isEmpty(boolean[] content) {
        return isNull(content) || content.length == 0;
    }

    static boolean isEmpty(char[] content) {
        return isNull(content) || content.length == 0;
    }

    static boolean isEmpty(Map<?, ?> map) {
        return isNull(map) || map.isEmpty();
    }

    static boolean isEmpty(Collection<?> collection) {
        return isNull(collection) || collection.isEmpty();
    }

    static <T> T ifEmpty(T val, T ifEmpty) {
        return isEmpty(val) ? ifEmpty : val;
    }
}
