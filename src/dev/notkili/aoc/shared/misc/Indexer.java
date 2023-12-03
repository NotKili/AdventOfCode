package dev.notkili.aoc.shared.misc;

import dev.notkili.aoc.shared.input.LongInput;
import dev.notkili.aoc.shared.misc.collections.List;
import dev.notkili.aoc.shared.misc.tuple.Tuple;

import java.util.Collection;
import java.util.HashMap;

public class Indexer<T> {
    private final HashMap<T, Count> counters = new HashMap<>();

    public Tuple<LongInput, T> getIndexFor(T element) {
        return new Tuple<>(new LongInput(getIndexForInternal(element)), element);
    }

    public List<Tuple<LongInput, T>> getIndexFor(Collection<T> elements) {
        List<Tuple<LongInput, T>> list = new List<>();

        for (var element : elements) {
            list.add(getIndexFor(element));
        }

        return list;
    }

    private long getIndexForInternal(T element) {
        if (counters.containsKey(element)) {
            return counters.get(element).increment().getCount();
        }

        counters.put(element, new Count(0));
        return 0;
    }
}
