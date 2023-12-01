package dev.notkili.aoc.shared.input;

import dev.notkili.aoc.shared.Solution;

import java.util.Locale;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class IntInput implements Input<IntInput> {
    private final int value;

    public static IntInput parseRadix(String value, int radix) {
        return new IntInput(Integer.parseInt(value, radix));
    }

    public static IntInput parseHex(String value) {
        return new IntInput(Integer.parseInt(value, 16));
    }

    public static IntInput parseOctal(String value) {
        return new IntInput(Integer.parseInt(value, 8));
    }

    public static IntInput parseBinary(String value) {
        return new IntInput(Integer.parseInt(value, 2));
    }

    public static IntInput parseLiteral(String value) {
        return switch (value.toLowerCase(Locale.ROOT)) {
            case "zero" -> new IntInput(0);
            case "one" -> new IntInput(1);
            case "two" -> new IntInput(2);
            case "three" -> new IntInput(3);
            case "four" -> new IntInput(4);
            case "five" -> new IntInput(5);
            case "six" -> new IntInput(6);
            case "seven" -> new IntInput(7);
            case "eight" -> new IntInput(8);
            case "nine" -> new IntInput(9);
            default -> throw new IllegalArgumentException("Unknown literal: " + value);
        };
    }

    public IntInput(String value) {
        this.value = Integer.parseInt(value);
    }

    public IntInput(int value) {
        this.value = value;
    }

    public IntInput(long value) {
        this.value = (int) value;
    }

    public IntInput(double value) {
        this.value = (int) value;
    }

    public IntInput(IntInput other) {
        this.value = other.value;
    }

    public int asInt() {
        return value;
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

    public LongInput asLongInput() {
        return new LongInput(value);
    }

    public DoubleInput asDoubleInput() {
        return new DoubleInput(value);
    }

    public IntInput add(IntInput other) {
        return new IntInput(value + other.value);
    }

    public IntInput add(int x) {
        return new IntInput(value + x);
    }

    public IntInput subtract(IntInput other) {
        return new IntInput(value - other.value);
    }

    public IntInput subtract(int x) {
        return new IntInput(value - x);
    }

    public IntInput multiply(IntInput other) {
        return new IntInput(value * other.value);
    }

    public IntInput multiply(int x) {
        return new IntInput(value * x);
    }

    public IntInput divide(IntInput other) {
        return new IntInput(value / other.value);
    }

    public IntInput divide(int x) {
        return new IntInput(value / x);
    }

    public IntInput aggregateUntil(BiFunction<IntInput, IntInput, IntInput> aggregator, Function<IntInput, IntInput> translator, Predicate<IntInput> until) {
        IntInput toReturn = new IntInput(0);
        IntInput current = this;

        while (!until.test(current)) {
            current = translator.apply(current);
            toReturn = aggregator.apply(toReturn, current);
        }

        return toReturn;
    }

    @Override
    public int compareTo(IntInput o) {
        return Integer.compare(value, o.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntInput intInput = (IntInput) o;
        return value == intInput.value;
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
