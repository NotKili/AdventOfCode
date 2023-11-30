package dev.notkili.aoc.shared.input;

import java.util.Objects;

public class IntInput implements Input<IntInput> {
    private final int value;

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

    public int asInt() {
        return value;
    }

    public long asLong() {
        return value;
    }

    public double asDouble() {
        return value;
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

    public StringInput asStringInput() {
        return new StringInput(value + "");
    }

    public LongInput asLongInput() {
        return new LongInput(value);
    }

    public DoubleInput asDoubleInput() {
        return new DoubleInput(value);
    }
}
