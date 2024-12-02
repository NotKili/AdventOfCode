package dev.notkili.aoc.shared.input;

import dev.notkili.aoc.shared.Solution;

public interface Input<I extends Input<?>> extends Comparable<I> {
    void print();
    Solution solution();
}
