package dev.notkili.aoc.shared.misc.collections.list;

import dev.notkili.aoc.shared.input.LongInput;
import dev.notkili.aoc.shared.misc.collections.Counter;
import dev.notkili.aoc.shared.misc.collections.set.ISet;

import java.util.*;
import java.util.function.BinaryOperator;
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

    T first();

    T last();

    L add(T element);
    
    default L addC(T element) {
        return copy().add(element);
    }

    L add(int index, T element);

    default L addC(int index, T element) {
        return copy().add(index, element);
    }
    
    L add(long index, T element);
    
    default L addC(long index, T element) {
        return copy().add(index, element);
    }

    L add(Collection<T> elements);
    
    default L addC(Collection<T> elements) {
        return copy().add(elements);
    }

    L add(L other);
    
    default L addC(L other) {
        return copy().add(other);
    }

    boolean contains(T element);

    boolean containsAll(Collection<T> elements);

    boolean containsAny(Collection<T> elements);

    boolean containsAny(Predicate<T> predicate);

    boolean containsAll(L other);

    Optional<T> find(Predicate<T> predicate);

    L findAll(Predicate<T> predicate);

    L rem(int index);
    
    default L remC(int index) {
        return copy().rem(index);
    }

    L rem(long index);
    
    default L remC(long index) {
        return copy().rem(index);
    }

    L rem(T element);
    
    default L remC(T element) {
        return copy().rem(element);
    }

    L rem(Collection<T> elements);
    
    default L remC(Collection<T> elements) {
        return copy().rem(elements);
    }

    L rem(Predicate<T> predicate);
    
    default L remC(Predicate<T> predicate) {
        return copy().rem(predicate);
    }

    L sublist(int start, int end);

    L sublist(long start, long end);

    L sublist(int start);

    L sublist(long start);

    IList<L, ?> group(int n);

    L repl(int index, T element);
    
    default L replC(int index, T element) {
        return copy().repl(index, element);
    }

    L repl(long index, T element);
    
    default L replC(long index, T element) {
        return copy().repl(index, element);
    }

    L repl(int index, Function<T, T> modifier);
    
    default L replC(int index, Function<T, T> modifier) {
        return copy().repl(index, modifier);
    }

    L repl(long index, Function<T, T> modifier);
    
    default L replC(long index, Function<T, T> modifier) {
        return copy().repl(index, modifier);
    }

    L replFirst(T oldElement, T newElement);
    
    default L replFC(T oldElement, T newElement) {
        return copy().replFirst(oldElement, newElement);
    }

    L repl(T oldElement, T newElement);
    
    default L replC(T oldElement, T newElement) {
        return copy().repl(oldElement, newElement);
    }
    
    L swap(int aInd, int bInd);
    
    default L swapC(int aInd, int bInd) {
        return copy().swap(aInd, bInd);
    }

    L clear();

    default boolean sorted() {
        return this.equals(copy().sort());
    }
    
    default boolean sorted(Comparator<T> comparator) {
        return this.equals(copy().sort(comparator));
    }
    
    L sort();

    L sort(Comparator<T> comparator);

    L copy();

    Optional<T> reduce(BinaryOperator<T> accumulator);

    L distinct();

    <I> IList<I, ?> map(Function<T, I> mapper);

    Optional<T> max(Comparator<T> comparator);

    Optional<T> min(Comparator<T> comparator);

    ArrayList<T> arrayList();

    ISet<T, ?> set();
    
    default Counter<T> count() {
        return new Counter<>(this);
    }

    L multiply(long times);

    void print();
}
