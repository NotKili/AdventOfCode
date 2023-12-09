package dev.notkili.aoc.shared.misc.collections.list;

import dev.notkili.aoc.shared.input.LongInput;
import dev.notkili.aoc.shared.misc.collections.Set;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * A list that supports custom helpful operations and allows method chaining by returning itself in any method without a return type.
 * @param <T> The type of the elements in the list
 * @param <L> The type of the list itself
 */
public interface IList<T, L extends IList<T, L>> extends Iterable<T> {
    boolean empty();

    int size();

    default LongInput sizeInp() {
        return new LongInput(size());
    }

    T get(int index);

    T get(long index);

    L add(T element);

    L add(int index, T element);

    L add(long index, T element);

    L addAll(Collection<T> elements);

    L addAll(L other);

    boolean contains(T element);

    boolean containsAll(Collection<T> elements);

    boolean containsAny(Collection<T> elements);

    boolean containsAny(Predicate<T> predicate);

    boolean containsAll(L other);

    Optional<T> find(Predicate<T> predicate);

    L findAll(Predicate<T> predicate);

    L remove(int index);

    L remove(long index);

    L remove(T element);

    L removeAll(Collection<T> elements);

    L removeIf(Predicate<T> predicate);

    L replace(int index, T element);

    L replace(long index, T element);

    L replace(int index, Function<T, T> modifier);

    L replace(long index, Function<T, T> modifier);

    L replaceFirst(T oldElement, T newElement);

    L replaceAll(T oldElement, T newElement);

    L clear();

    L sort();

    L sort(Comparator<T> comparator);

    L copy();

    L distinct();

    <I> IList<I, ?> map(Function<T, I> mapper);

    Optional<T> max(Comparator<T> comparator);

    Optional<T> min(Comparator<T> comparator);

    ArrayList<T> arrayList();

    Set<T> set();

    void print();
}
