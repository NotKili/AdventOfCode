package dev.notkili.aoc.shared.input;

import java.util.Objects;

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

    public StringInput asStringInput() {
        return new StringInput(value + "");
    }

    public IntInput asIntInput() {
        return new IntInput((int) value);
    }

    public LongInput asLongInput() {
        return new LongInput((long) value);
    }
}
