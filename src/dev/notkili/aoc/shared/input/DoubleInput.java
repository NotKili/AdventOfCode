package dev.notkili.aoc.shared.input;

import dev.notkili.aoc.shared.Solution;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class DoubleInput implements Input<DoubleInput> {
    private final double value;

    public DoubleInput(int value) {
        this.value = value;
    }

    public DoubleInput(long value) {
        this.value = value;
    }

    public DoubleInput(double value) {
        this.value = value;
    }

    public DoubleInput(String value) {
        this.value = Double.parseDouble(value);
    }

    public int asInt() {
        return (int) value;
    }

    public long asLong() {
        return (long) value;
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

    public LongInput asLongInput() {
        return new LongInput((long) value);
    }

    public DoubleInput add(DoubleInput other) {
        return new DoubleInput(value + other.value);
    }

    public DoubleInput add(double x) {
        return new DoubleInput(value + x);
    }

    public DoubleInput subtract(DoubleInput other) {
        return new DoubleInput(value - other.value);
    }

    public DoubleInput subtract(double x) {
        return new DoubleInput(value - x);
    }

    public DoubleInput multiply(DoubleInput other) {
        return new DoubleInput(value * other.value);
    }

    public DoubleInput multiply(double x) {
        return new DoubleInput(value * x);
    }

    public DoubleInput divide(DoubleInput other) {
        return new DoubleInput(value / other.value);
    }

    public DoubleInput divide(double x) {
        return new DoubleInput(value / x);
    }

    public DoubleInput aggregateUntil(BiFunction<DoubleInput, DoubleInput, DoubleInput> aggregator, Function<DoubleInput, DoubleInput> translator, Predicate<DoubleInput> until) {
        DoubleInput toReturn = new DoubleInput(0.0d);
        DoubleInput current = this;

        while (!until.test(current)) {
            current = translator.apply(current);
            toReturn = aggregator.apply(toReturn, current);
        }

        return toReturn;
    }

    public LongInput floor() {
        return new LongInput((long) Math.floor(value));
    }

    public LongInput ceil() {
        return new LongInput((long) Math.ceil(value));
    }

    public LongInput round() {
        return new LongInput(Math.round(value));
    }

    public DoubleInput abs() {
        return new DoubleInput(Math.abs(value));
    }

    public DoubleInput sqrt() {
        return new DoubleInput(Math.sqrt(value));
    }

    public DoubleInput pow(DoubleInput other) {
        return new DoubleInput(Math.pow(value, other.value));
    }

    public DoubleInput pow(double x) {
        return new DoubleInput(Math.pow(value, x));
    }

    @Override
    public int compareTo(DoubleInput o) {
        return Double.compare(value, o.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DoubleInput doubleInput = (DoubleInput) o;
        return value == doubleInput.value;
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
    public Solution asSolution() {
        return new Solution(value + "");
    }
}
