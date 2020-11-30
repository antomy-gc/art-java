package io.art.core.lazy;

import lombok.*;
import static java.util.Objects.*;
import java.util.concurrent.atomic.*;
import java.util.function.*;

@RequiredArgsConstructor
public class LazyValue<T> {
    private final AtomicReference<T> value = new AtomicReference<>();
    private final Supplier<T> loader;

    public T get() {
        T value;
        if (nonNull(value = this.value.get())) {
            return value;
        }
        if (this.value.compareAndSet(null, value = loader.get())) {
            return value;
        }
        return this.value.get();
    }

    public <R> LazyValue<R> map(Function<T, R> mapper) {
        return new LazyValue<>(() -> mapper.apply(get()));
    }

    public LazyValue<T> initialize() {
        get();
        return this;
    }

    public boolean initialized() {
        return value.get() != null;
    }

    public static <T> LazyValue<T> lazy(Supplier<T> factory) {
        return new LazyValue<>(factory);
    }
}
