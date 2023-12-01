package dev.notkili.aoc.shared.input;

import dev.notkili.aoc.shared.Solution;

import java.util.Objects;

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

    public StringInput asStringInput() {
        return new StringInput(value + "");
    }

    public IntInput asIntInput() {
        return new IntInput((int) value);
    }

    public DoubleInput asDoubleInput() {
        return new DoubleInput(value);
    }

    @Override
    public Solution asSolution() {
        return new Solution(value + "");
    }
}
