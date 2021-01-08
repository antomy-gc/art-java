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

package io.art.scheduler.executor.periodic;

import lombok.*;
import java.time.*;
import java.util.function.*;

@RequiredArgsConstructor
class RepeatableRunnable implements Runnable {
    private final Runnable action;
    private final Consumer<LocalDateTime> repeat;
    private final LocalDateTime now = LocalDateTime.now();

    @Override
    public void run() {
        action.run();
        repeat.accept(now);
    }
}
