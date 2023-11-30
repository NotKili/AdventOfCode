package dev.notkili.aoc.shared.input;

public interface Input<I extends Input<?>> extends Comparable<I> {
    void print();
}
