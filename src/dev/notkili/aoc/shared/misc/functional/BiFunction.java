package dev.notkili.aoc.shared.misc.functional;

@FunctionalInterface
public interface BiFunction<K1, K2, V> {
    V apply(K1 key1, K2 key2);
}
