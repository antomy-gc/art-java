/*
 *    Copyright 2019 ART
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package ru.art.task.deferred.executor;

import static java.lang.Runtime.getRuntime;
import static java.lang.Thread.UncaughtExceptionHandler;
import static java.util.Objects.isNull;

/**
 * Строитель конфигурации для {@link DeferredExecutorImpl} и {@link DeferredEventObserver}
 */
public class DeferredExecutorBuilder {
    private ExceptionHandler exceptionHandler;
    private UncaughtExceptionHandler threadPoolExceptionHandler;
    private Integer eventsQueueMaxSize;
    private Integer threadPoolCoreSize;
    private Boolean awaitAllTasksTerminationOnShutdown;
    private Long threadPoolTerminationTimeoutMillis;

    public DeferredExecutorBuilder withExceptionHandler(ExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
        return this;
    }

    public DeferredExecutorBuilder withThreadPoolExceptionHandler(UncaughtExceptionHandler threadPoolExceptionHandler) {
        this.threadPoolExceptionHandler = threadPoolExceptionHandler;
        return this;
    }

    public DeferredExecutorBuilder eventsQueueMaxSize(Integer eventsQueueMaxSize) {
        this.eventsQueueMaxSize = eventsQueueMaxSize;
        return this;
    }

    public DeferredExecutorBuilder threadPoolCoreSize(Integer threadPoolCoreSize) {
        this.threadPoolCoreSize = threadPoolCoreSize;
        return this;
    }

    public DeferredExecutorBuilder awaitAllTasksTerminationOnShutdown(Boolean awaitAllTasksTerminationOnShutdown) {
        this.awaitAllTasksTerminationOnShutdown = awaitAllTasksTerminationOnShutdown;
        return this;
    }

    public DeferredExecutorBuilder threadPoolTerminationTimeoutMillis(Long threadPoolTerminationTimeoutMillis) {
        this.threadPoolTerminationTimeoutMillis = threadPoolTerminationTimeoutMillis;
        return this;
    }

    public DeferredExecutor build() {
        DeferredExecutorConfiguration executorConfiguration = new DeferredExecutorConfiguration();
        executorConfiguration.setExceptionHandler(isNull(exceptionHandler) ? new DeferredExecutorExceptionHandler() : exceptionHandler);
        executorConfiguration.setEventsQueueMaxSize(isNull(eventsQueueMaxSize) ? DeferredExecutorImpl.DeferredExecutorConstants.DEFAULT_MAX_QUEUE_SIZE : eventsQueueMaxSize);
        executorConfiguration.setThreadPoolCoreSize(isNull(threadPoolCoreSize) ? getRuntime().availableProcessors() - 1 : threadPoolCoreSize);
        executorConfiguration.setThreadPoolExceptionHandler(isNull(threadPoolExceptionHandler) ? new DeferredExecutorThreadPoolExceptionHandler() : threadPoolExceptionHandler);
        executorConfiguration.setAwaitAllTasksTerminationOnShutdown(isNull(awaitAllTasksTerminationOnShutdown) ? false : awaitAllTasksTerminationOnShutdown);
        executorConfiguration.setThreadPoolTerminationTimeoutMillis(isNull(threadPoolTerminationTimeoutMillis) ? DeferredExecutorImpl.DeferredExecutorConstants.DEFAULT_SHUTDOWN_TIMEOUT : threadPoolTerminationTimeoutMillis);
        return new DeferredExecutorImpl(executorConfiguration);
    }
}