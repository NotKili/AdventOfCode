package dev.notkili.aoc.shared.input;

import dev.notkili.aoc.shared.misc.collections.Counter;
import dev.notkili.aoc.shared.misc.tuple.Triple;
import dev.notkili.aoc.shared.misc.tuple.Tuple;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ListInput<I> implements Iterable<I> {
    protected final List<I> inputs;

    public ListInput(List<I> inputs) {
        this.inputs = inputs;
    }

    public List<I> asList() {
        return inputs;
    }

    public int indexOf(I value) {
        return inputs.indexOf(value);
    }

    public I get(int index) {
        return inputs.get(index);
    }

    public void replace(int index, I value) {
        if (index < inputs.size())
            inputs.set(index, value);
    }

    public void add(I value) {
        inputs.add(value);
    }

    public ListInput<I> remove(int index) {
        inputs.remove(index);
        return this;
    }
    
    public ListInput<I> removeIf(Predicate<I> pred) {
        inputs.removeIf(pred);
        return this;
    }

    public ListInput<I> remove(I value) {
        inputs.remove(value);
        return this;
    }

    public boolean contains(I value) {
        return inputs.contains(value);
    }

    public boolean containsAll(List<I> values) {
        return inputs.containsAll(values);
    }

    public boolean containsAny(List<I> values) {
        return values.stream().anyMatch(inputs::contains);
    }

    public boolean containsNone(List<I> values) {
        return values.stream().noneMatch(inputs::contains);
    }

    public ListInput<I> reverse() {
        List<I> reversed = new ArrayList<>(inputs);
        java.util.Collections.reverse(reversed);
        return new ListInput<>(reversed);
    }

    public int size() {
        return inputs.size();
    }

    public ListInput<I> copy() {
        return new ListInput<>(new ArrayList<>(inputs));
    }

    public IntListInput mapToInt(Function<I, IntInput> mapper) {
        return new IntListInput(inputs.stream().map(mapper).collect(Collectors.toList()));
    }

    public StringListInput mapToString(Function<I, StringInput> mapper) {
        return new StringListInput(inputs.stream().map(mapper).collect(Collectors.toList()));
    }

    public <T> ListInput<T> mapTo(Function<I, T> mapper) {
        return new ListInput<>(inputs.stream().map(mapper).collect(Collectors.toList()));
    }

    public Optional<I> findFirst(Predicate<I> predicate) {
        return this.inputs.stream().filter(predicate).findFirst();
    }

    public ListInput<I> findAll(Predicate<I> predicate) {
        return new ListInput<>(this.inputs.stream().filter(predicate).collect(Collectors.toList()));
    }

    public Optional<Tuple<I, I>> findFirstPair(Predicate<Tuple<I, I>> predicate, boolean isOrderRelevant) {
        for (int i = 0; i < inputs.size(); i++) {
            for (int j = i + 1; j < inputs.size(); j++) {
                var tuple = new Tuple<>(inputs.get(i), inputs.get(j));
                if (predicate.test(tuple)) {
                    return Optional.of(tuple);
                }

                if (isOrderRelevant) {
                    var tuple2 = new Tuple<>(inputs.get(j), inputs.get(i));
                    if (predicate.test(tuple2)) {
                        return Optional.of(tuple2);
                    }
                }
            }
        }

        return Optional.empty();
    }

    public ListInput<Tuple<I, I>> findAllPair(Predicate<Tuple<I, I>> predicate, boolean isOrderRelevant) {
        List<Tuple<I, I>> pairs = new ArrayList<>();

        for (int i = 0; i < inputs.size(); i++) {
            for (int j = i + 1; j < inputs.size(); j++) {
                var tuple = new Tuple<>(inputs.get(i), inputs.get(j));
                if (predicate.test(tuple)) {
                    pairs.add(tuple);
                }

                if (isOrderRelevant) {
                    var tuple2 = new Tuple<>(inputs.get(j), inputs.get(i));
                    if (predicate.test(tuple2)) {
                        pairs.add(tuple2);
                    }
                }
            }
        }

        return new ListInput<>(pairs);
    }

    public Optional<Triple<I, I, I>> findFirstTriple(Predicate<Triple<I, I, I>> predicate, boolean isOrderRelevant) {
        for (int i = 0; i < inputs.size(); i++) {
            for (int j = i + 1; j < inputs.size(); j++) {
                for (int k = j + 1; k < inputs.size(); k++) {
                    var triple = new Triple<>(inputs.get(i), inputs.get(j), inputs.get(k));
                    if (predicate.test(triple)) {
                        return Optional.of(triple);
                    }

                    if (isOrderRelevant) {
                        var triple2 = new Triple<>(inputs.get(j), inputs.get(k), inputs.get(i));
                        var triple3 = new Triple<>(inputs.get(k), inputs.get(i), inputs.get(j));
                        if (predicate.test(triple2)) {
                            return Optional.of(triple2);
                        }
                        if (predicate.test(triple3)) {
                            return Optional.of(triple3);
                        }
                    }
                }
            }
        }

        return Optional.empty();
    }

    public ListInput<Triple<I, I, I>> findAllTriple(Predicate<Triple<I, I, I>> predicate, boolean isOrderRelevant) {
        List<Triple<I, I, I>> triples = new ArrayList<>();

        for (int i = 0; i < inputs.size(); i++) {
            for (int j = i + 1; j < inputs.size(); j++) {
                for (int k = j + 1; k < inputs.size(); k++) {
                    var triple = new Triple<>(inputs.get(i), inputs.get(j), inputs.get(k));
                    if (predicate.test(triple)) {
                        triples.add(triple);
                    }

                    if (isOrderRelevant) {
                        var triple2 = new Triple<>(inputs.get(j), inputs.get(k), inputs.get(i));
                        var triple3 = new Triple<>(inputs.get(k), inputs.get(i), inputs.get(j));
                        if (predicate.test(triple2)) {
                            triples.add(triple2);
                        }
                        if (predicate.test(triple3)) {
                            triples.add(triple3);
                        }
                    }
                }
            }
        }

        return new ListInput<>(triples);
    }

    public ListInput<ListInput<I>> groupN(int n) {
        if (size() % n != 0) throw new IllegalArgumentException("Cannot group list of size " + size() + " into groups of size " + n + ".");

        List<ListInput<I>> groups = new ArrayList<>();

        for (int i = 0; i < inputs.size(); i += n) {
            groups.add(new ListInput<>(inputs.subList(i, Math.min(i + n, inputs.size()))));
        }

        return new ListInput<>(groups);
    }

    public Optional<I> reduce(BinaryOperator<I> reducer) {
        return inputs.stream().reduce(reducer);
    }

    public IntInput distinctCount() {
        return new IntInput((int) inputs.stream().distinct().count());
    }
    
    public ListInput<I> sort() {
        if (get(0) instanceof Comparable) {
            return sort((Comparator<I>) Comparator.naturalOrder());
        }
        
        return this;
    }
    
    public ListInput<I> sort(Comparator<I> comparator) {
        List<I> sorted = new ArrayList<>(inputs);
        sorted.sort(comparator);
        return new ListInput<>(sorted);
    }
    
    public Counter<I> count() {
        return new Counter<>(inputs);
    }

    @Override
    public Iterator<I> iterator() {
        return inputs.iterator();
    }

    public static class IntListInput extends ListInput<IntInput> {

        public IntListInput() {
            super(new ArrayList<>());
        }
        public IntListInput(List<IntInput> inputs) {
            super(inputs);
        }

        public IntInput min() {
            return new IntInput(inputs.stream().mapToInt(IntInput::asInt).min().orElseThrow());
        }

        public IntInput max() {
            return new IntInput(inputs.stream().mapToInt(IntInput::asInt).max().orElseThrow());
        }

        public IntInput average() {
            return new IntInput((int) Math.round(inputs.stream().mapToInt(IntInput::asInt).average().orElseThrow()));
        }

        public IntInput sum() {
            return new IntInput(this.inputs.stream().mapToInt(IntInput::asInt).sum());
        }

        public IntListInput mapToInt(Function<IntInput, IntInput> mapper) {
            return new IntListInput(this.inputs.stream().map(mapper).collect(Collectors.toList()));
        }

        public IntListInput addToEach(IntInput input) {
            return mapToInt(i -> new IntInput(i.asInt() + input.asInt()));
        }

        public IntListInput addToEach(int input) {
            return mapToInt(i -> new IntInput(i.asInt() + input));
        }

        public IntListInput subtractFromEach(IntInput input) {
            return mapToInt(i -> new IntInput(i.asInt() - input.asInt()));
        }

        public IntListInput subtractFromEach(int input) {
            return mapToInt(i -> new IntInput(i.asInt() - input));
        }

        public IntListInput multiplyEach(IntInput input) {
            return mapToInt(i -> new IntInput(i.asInt() * input.asInt()));
        }

        public IntListInput multiplyEach(int input) {
            return mapToInt(i -> new IntInput(i.asInt() * input));
        }

        public IntListInput divideEach(IntInput input) {
            return mapToInt(i -> new IntInput(i.asInt() / input.asInt()));
        }

        public IntListInput divideEach(int input) {
            return mapToInt(i -> new IntInput(i.asInt() / input));
        }

        public LongInput product() {
            return new LongInput(this.inputs.stream().mapToLong(IntInput::asInt).reduce(1, (a, b) -> a * b));
        }
    }

    public static class StringListInput extends ListInput<StringInput> {
        public StringListInput(List<StringInput> inputs) {
            super(inputs);
        }

        public ListInput<StringListInput> splitAt(String regex) {
            return new ListInput<>(this.inputs.stream().map(s -> s.splitAt(regex)).collect(Collectors.toList()));
        }

        public IntListInput toInts() {
            return new IntListInput(this.inputs.stream().map(StringInput::asInt).map(IntInput::new).collect(Collectors.toList()));
        }

        public IntListInput mapToInt(Function<StringInput, IntInput> mapper) {
            return new IntListInput(this.inputs.stream().map(mapper).collect(Collectors.toList()));
        }
    }
}
