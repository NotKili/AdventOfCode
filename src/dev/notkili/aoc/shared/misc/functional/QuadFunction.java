package dev.notkili.aoc.shared.misc.functional;

@FunctionalInterface
public interface QuadFunction<K1, K2, K3, K4, V> {
    V apply(K1 key1, K2 key2, K3 key3, K4 key4);
}
