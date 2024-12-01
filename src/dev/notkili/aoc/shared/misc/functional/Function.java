package dev.notkili.aoc.shared.misc.functional;

@FunctionalInterface
public interface Function<K1, V> {
    V apply(K1 key);
}
