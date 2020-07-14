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

package io.art.tarantool.configuration.lua;

import com.mitchellbosecke.pebble.*;
import com.mitchellbosecke.pebble.loader.*;
import lombok.*;
import io.art.tarantool.exception.*;
import static io.art.core.caster.Caster.*;
import static io.art.core.checker.EmptinessChecker.isNotEmpty;
import static io.art.core.factory.CollectionsFactory.*;
import static io.art.tarantool.constants.TarantoolModuleConstants.*;
import static io.art.tarantool.constants.TarantoolModuleConstants.TemplateParameterKeys.*;
import static io.art.tarantool.constants.TarantoolModuleConstants.Templates.*;
import java.io.*;
import java.util.*;

@Getter
@Builder
@SuppressWarnings("Duplicates")
public class TarantoolInitialConfiguration {
    private Boolean background;
    private String customProcTitle;
    private String memtxDir;
    private String vinylDir;
    private String workDir;
    private String username;
    private String pidFile;
    private Boolean readOnly;
    private Long vinylTimeout;
    private Integer workerPoolThreads;
    private Long memtexMaxTupleSize;
    private Long memtxMemory;
    private Integer slabAllocFactor;
    private Long slabAllocMaximal;
    private Integer slabAllocArena;
    @Singular("stringOption")
    private final Map<String, String> stringOptions;
    @Singular("numberOption")
    private final Map<String, Long> numberOptions;

    public String toLua(int port, Set<String> replicas) {
        Map<String, Object> templateContext = cast(mapOf()
                .add(LISTEN, port)
                .add(BACKGROUND, background)
                .add(CUSTOM_PROC_TITLE, customProcTitle)
                .add(MEMTX_DIR, memtxDir)
                .add(VINYL_DIR, vinylDir)
                .add(WORK_DIR, workDir)
                .add(USERNAME, username)
                .add(PID_FILE, pidFile)
                .add(READ_ONLY, readOnly)
                .add(VINYL_TIMEOUT, vinylTimeout)
                .add(MEMTX_MAX_TUPLE_SIZE, memtexMaxTupleSize)
                .add(MEMTX_MEMORY, memtxMemory)
                .add(SLAB_ALLOC_FACTOR, slabAllocFactor)
                .add(SLAB_ALLOC_MAXIMAL, slabAllocMaximal)
                .add(SLAB_ALLOC_ARENA, slabAllocArena)
                .add(REPLICAS, replicas)
                .add(WORKER_POOL_THREADS, workerPoolThreads)
                .add(NUMBER_OPTIONS, numberOptions)
                .add(STRING_OPTIONS, stringOptions));
        StringWriter templateWriter = new StringWriter();
        try {
            new PebbleEngine.Builder()
                    .loader(new ClasspathLoader())
                    .autoEscaping(false)
                    .cacheActive(false)
                    .build()
                    .getTemplate(CONFIGURATION + TWIG_TEMPLATE)
                    .evaluate(templateWriter, templateContext);
            return templateWriter.toString();
        } catch (Throwable e) {
            throw new TarantoolExecutionException(e);
        }
    }
}
