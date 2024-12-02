package dev.notkili.aoc.shared.misc.collections;

import dev.notkili.aoc.shared.misc.Count;
import dev.notkili.aoc.shared.misc.collections.list.IList;
import dev.notkili.aoc.shared.misc.collections.list.List;
import dev.notkili.aoc.shared.misc.tuple.Tuple;

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

    public Counter(IList<T, ?> elements) {
        this();
        add(elements);
    }

    public Counter(Collection<T> elements) {
        this();
        add(elements);
    }

    public Count count(T element) {
        return counters.getOrDefault(element, new Count(0));
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

    public Counter<T> add(IList<T, ?> elements) {
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
    
    public Tuple<T, Integer> max() {
        T t = null;
        var max = 0;
        
        for (var entry : counters.entrySet()) {
            if (max < entry.getValue().asInt()) {
                t = entry.getKey();
                max = entry.getValue().asInt();
            }
        }
        
        return new Tuple<>(t, max);
    }
    
    public Tuple<T, Integer> min() {
        T t = null;
        var min = Integer.MAX_VALUE;
        
        for (var entry : counters.entrySet()) {
            if (min > entry.getValue().asInt()) {
                t = entry.getKey();
                min = entry.getValue().asInt();
            }
        }
        
        return new Tuple<>(t, min);
    }
    
    public List<Tuple<T, Count>> entries() {
        return new List<>(counters.entrySet().stream().map(e -> new Tuple<>(e.getKey(), e.getValue())).toList());
    }

    public int size() {
        return counters.size();
    }

    public List<T> keys() {
        return new List<>(counters.keySet());
    }
    
    public Map<T, Count> map() {
        return counters;
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
    
    public Counter<T> copy() {
        Counter<T> counter = new Counter<>();
        counter.counters.putAll(counters);
        return counter;
    }
}
