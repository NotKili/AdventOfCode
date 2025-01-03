package dev.notkili.aoc.shared.misc.functional;

@FunctionalInterface
public interface TriConsumer<A, B, C> {
    void accept(A a, B b, C c);
}
