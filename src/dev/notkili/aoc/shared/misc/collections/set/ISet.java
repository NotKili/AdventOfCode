package dev.notkili.aoc.shared.misc.collections.set;

import dev.notkili.aoc.shared.input.IntInput;
import dev.notkili.aoc.shared.misc.collections.list.IList;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public interface ISet<T, S extends ISet<T, S>> extends Iterable<T> {
    S copy();

    boolean empty();
    
    int size();
    
    default IntInput sizeInp() {
        return new IntInput(size());
    }

    S clear();

    S add(T element);

    S addAll(Collection<T> elements);

    S addAll(S other);

    boolean contains(T element);

    boolean containsAll(Collection<T> elements);

    boolean containsAll(S other);

    boolean containsAny(Collection<T> elements);

    boolean containsAny(S other);

    S remove(T element);

    S removeAll(Collection<T> elements);

    S removeAll(Predicate<T> predicate);

    S retainAll(Collection<T> elements);

    S retainAll(Predicate<T> predicate);

    S union(T element);

    default S union(T... elements) {
        return union(List.of(elements));
    }

    S union(Collection<T> other);

    S union(S other);

    S subtract(T element);

    default S subtract(T... elements) {
        return subtract(List.of(elements));
    }

    S subtract(Collection<T> other);

    S subtract(S other);

    S intersection(T element);

    default S intersection(T... elements) {
        return intersection(List.of(elements));
    }

    S intersection(Collection<T> other);

    S intersection(S other);

    S complement(T element);

    default S complement(T... elements) {
        return complement(List.of(elements));
    }

    S complement(Collection<T> universal);

    S complement(S universal);

    boolean areDisjoint(T element);

    default boolean areDisjoint(T... elements) {
        return areDisjoint(List.of(elements));
    }

    boolean areDisjoint(Collection<T> other);

    boolean areDisjoint(S other);

    public <C> ISet<C, ?> map(Function<T, C> mapper);

    public <C> ISet<C, ?> mapMulti(Function<T, Collection<C>> mapper);

    java.util.Set<T> set();

    IList<T, ?> list();
}
