package dev.notkili.aoc.shared.misc.collections;

import dev.notkili.aoc.shared.misc.Count;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Counter<T> {
    private final Map<T, Count> counters;

    public Counter() {
        counters = new HashMap<>();
    }

    @SafeVarargs
    public Counter(T... elements) {
        this();
        add(elements);
    }

    public Counter(List<T> elements) {
        this();
        add(elements);
    }

    public Counter(Collection<T> elements) {
        this();
        add(elements);
    }

    public Count getCountFor(T element) {
        return counters.get(element);
    }

    public Counter<T> add(T element) {
        counters.computeIfAbsent(element, k -> Count.empty()).increment();
        return this;
    }

    @SafeVarargs
    public final Counter<T> add(T... elements) {
        for (var element : elements) {
            add(element);
        }

        return this;
    }

    public Counter<T> add(List<T> elements) {
        for (var element : elements) {
            add(element);
        }

        return this;
    }

    public Counter<T> add(Collection<T> elements) {
        for (var element : elements) {
            add(element);
        }

        return this;
    }

    public Counter<T> merge(Counter<T> other) {
        Counter<T> counter = new Counter<>();

        for (var entry : this.counters.entrySet()) {
            counter.counters.merge(entry.getKey(), entry.getValue().copy(), Count::increment);
        }

        for (var entry : other.counters.entrySet()) {
            counter.counters.merge(entry.getKey(), entry.getValue().copy(), Count::increment);
        }

        return counter;
    }
}
