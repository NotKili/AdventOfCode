package dev.notkili.aoc.shared.input;

import dev.notkili.aoc.shared.Solution;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class LongInput implements Input<LongInput> {
    private final long value;

    public LongInput(int value) {
        this.value = value;
    }

    public LongInput(long value) {
        this.value = value;
    }

    public LongInput(String value) {
        this.value = Long.parseLong(value);
    }

    public int asInt() {
        return (int) value;
    }

    public long asLong() {
        return value;
    }

    public double asDouble() {
        return value;
    }

    public StringInput asStringInput() {
        return new StringInput(value + "");
    }

    public IntInput asIntInput() {
        return new IntInput((int) value);
    }

    public DoubleInput asDoubleInput() {
        return new DoubleInput(value);
    }

    public LongInput add(LongInput other) {
        return new LongInput(value + other.value);
    }

    public LongInput add(long x) {
        return new LongInput(value + x);
    }

    public LongInput subtract(LongInput other) {
        return new LongInput(value - other.value);
    }

    public LongInput subtract(long x) {
        return new LongInput(value - x);
    }

    public LongInput multiply(LongInput other) {
        return new LongInput(value * other.value);
    }

    public LongInput multiply(long x) {
        return new LongInput(value * x);
    }

    public LongInput divide(LongInput other) {
        return new LongInput(value / other.value);
    }

    public LongInput divide(long x) {
        return new LongInput(value / x);
    }

    public LongInput pow(LongInput other) {
        return new LongInput((long) Math.pow(value, other.value));
    }

    public LongInput pow(long x) {
        return new LongInput((long) Math.pow(value, x));
    }

    public LongInput mod(LongInput other) {
        return new LongInput(value % other.value);
    }

    public LongInput mod(long x) {
        return new LongInput(value % x);
    }

    public LongInput abs() {
        return new LongInput(Math.abs(value));
    }

    public LongInput negate() {
        return new LongInput(-value);
    }

    public LongInput max(LongInput other) {
        return new LongInput(Math.max(value, other.value));
    }

    public LongInput max(LongInput... others) {
        long max = value;
        for (LongInput other : others) {
            max = Math.max(max, other.value);
        }
        return new LongInput(max);
    }

    public LongInput max(long x) {
        return new LongInput(Math.max(value, x));
    }

    public LongInput max(long... x) {
        long max = value;
        for (long i : x) {
            max = Math.max(max, i);
        }
        return new LongInput(max);
    }

    public LongInput min(LongInput other) {
        return new LongInput(Math.min(value, other.value));
    }

    public LongInput min(LongInput... others) {
        long min = value;
        for (LongInput other : others) {
            min = Math.min(min, other.value);
        }
        return new LongInput(min);
    }

    public LongInput min(long x) {
        return new LongInput(Math.min(value, x));
    }

    public LongInput min(long... x) {
        long min = value;
        for (long i : x) {
            min = Math.min(min, i);
        }
        return new LongInput(min);
    }

    public LongInput aggregateUntil(BiFunction<LongInput, LongInput, LongInput> aggregator, Function<LongInput, LongInput> translator, Predicate<LongInput> until) {
        LongInput toReturn = new LongInput(0L);
        LongInput current = this;

        while (!until.test(current)) {
            current = translator.apply(current);
            toReturn = aggregator.apply(toReturn, current);
        }

        return toReturn;
    }

    @Override
    public int compareTo(LongInput o) {
        return Long.compare(value, o.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LongInput longInput = (LongInput) o;
        return value == longInput.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value + "";
    }

    @Override
    public void print() {
        System.out.print(value);
    }

    @Override
    public Solution solution() {
        return new Solution(value + "");
    }
}
