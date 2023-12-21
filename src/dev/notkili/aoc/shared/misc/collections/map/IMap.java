package dev.notkili.aoc.shared.misc.collections.map;

import dev.notkili.aoc.shared.misc.collections.list.List;
import dev.notkili.aoc.shared.misc.collections.set.Set;

import java.util.Collection;
import java.util.Map;
import java.util.function.*;

public interface IMap<K, V, M extends IMap<K, V, M>> {
    boolean empty();

    int size();

    IMap<K, V, M> copy();

    M clear();

    M put(K key, V value);

    M put(Collection<V> values, Function<V, K> keyMapper);

    M put(List<V> values, Function<V, K> keyMapper);

    M put(Set<V> values, Function<V, K> keyMapper);

    <E> M put(Collection<E> values, Function<E, K> keyMapper, Function<E, V> valueMapper);

    <E> M put(List<E> values, Function<E, K> keyMapper, Function<E, V> valueMapper);

    <E> M put(Set<E> values, Function<E, K> keyMapper, Function<E, V> valueMapper);

    M join(IMap<K, V, ?> other);

    M rm(K key);

    M rm(Collection<K> keys);

    M rm(List<K> keys);

    M rm(Set<K> keys);

    M rmIf(Predicate<K> predicate);

    M rmIfV(Predicate<V> predicate);

    V get(K key);

    V getOr(K key, V defaultValue);

    V getOrP(K key, V defaultValue);

    V getOrP(K key, Supplier<V> defaultValue);

    V getOrP(K key, Function<K, V> defaultValue);

    K key(V value);

    K keyOr(V value, K defaultValue);

    K keyOrP(V value, K defaultValue);

    K keyOrP(V value, Supplier<K> defaultValue);

    K keyOrP(V value, Function<V, K> defaultValue);

    Set<K> keys();

    List<V> values();

    boolean ex(K key);

    boolean ex(K... keys);

    boolean ex(Collection<K> keys);

    boolean ex(List<K> keys);

    boolean ex(Set<K> keys);

    boolean exAny(K... keys);

    boolean exAny(Collection<K> keys);

    boolean exAny(List<K> keys);

    boolean exAny(Set<K> keys);

    boolean exVal(V value);

    boolean exVal(V... values);

    boolean exVal(Collection<V> values);

    boolean exVal(List<V> values);

    boolean exVal(Set<V> values);

    boolean exAnyVal(V... values);

    boolean exAnyVal(Collection<V> values);

    boolean exAnyVal(List<V> values);

    boolean exAnyVal(Set<V> values);

    M replace(K key, Function<V, V> mapper);

    M replace(Collection<K> keys, Function<V, V> mapper);

    M replace(List<K> keys, Function<V, V> mapper);

    M replace(Set<K> keys, Function<V, V> mapper);

    M replace(Function<V, V> mapper);

    <KNew, VNew> IMap<KNew, VNew, ?> map(Function<K, KNew> keyMapper, Function<V, VNew> valueMapper);

    <E> List<E> flat(BiFunction<K, V, E> flattener);

    void each(BiConsumer<K, V> consumer);

    void eachKey(Consumer<K> consumer);

    void eachValue(Consumer<V> consumer);
}
