package dev.notkili.aoc.shared.misc.collections.set;

import dev.notkili.aoc.shared.misc.collections.list.IList;
import dev.notkili.aoc.shared.misc.collections.list.List;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Predicate;

public class Set<T> implements ISet<T, Set<T>> {
    private final java.util.Set<T> backingSet;
    
    public static <I> Set<I> of() {
        return new Set<>();
    }

    public static <I> Set<I> of(I element) {
        return new Set<>(java.util.List.of(element));
    }

    @SafeVarargs
    public static <I> Set<I> of(I... elements) {
        return new Set<>(java.util.List.of(elements));
    }

    public static <I> Set<I> of(Collection<I> initialElements) {
        return new Set<>(initialElements);
    }

    public Set() {
        backingSet = new HashSet<>();
    }
    
    public Set(Collection<T> initialElements) {
        backingSet = new HashSet<>(initialElements);
    }

    public Set(Set<T> other) {
        backingSet = new HashSet<>(other.backingSet);
    }

    @Override
    public Set<T> copy() {
        return new Set<>(this);
    }

    @Override
    public boolean empty() {
        return backingSet.isEmpty();
    }

    @Override
    public int size() {
        return backingSet.size();
    }

    @Override
    public Set<T> clear() {
        backingSet.clear();
        return this;
    }

    @Override
    public Set<T> add(T element) {
        backingSet.add(element);
        return this;
    }

    @Override
    public Set<T> addAll(Collection<T> elements) {
        backingSet.addAll(elements);
        return this;
    }

    @Override
    public Set<T> addAll(Set<T> other) {
        backingSet.addAll(other.backingSet);
        return this;
    }

    @Override
    public boolean contains(T element) {
        return backingSet.contains(element);
    }

    @Override
    public boolean containsAll(Collection<T> elements) {
        return backingSet.containsAll(elements);
    }

    @Override
    public boolean containsAll(Set<T> other) {
        return backingSet.containsAll(other.backingSet);
    }

    @Override
    public boolean containsAny(Collection<T> elements) {
        for (T element : elements) {
            if (backingSet.contains(element)) {
                return true;
            }
        }
        
        return false;
    }

    @Override
    public boolean containsAny(Set<T> other) {
        return containsAny(other.backingSet);
    }

    @Override
    public Set<T> remove(T element) {
        backingSet.remove(element);
        return this;
    }

    @Override
    public Set<T> removeAll(Collection<T> elements) {
        backingSet.removeAll(elements);
        return this;
    }

    @Override
    public Set<T> removeAll(Predicate<T> predicate) {
        backingSet.removeIf(predicate);
        return this;
    }

    @Override
    public Set<T> retainAll(Collection<T> elements) {
        backingSet.retainAll(elements);
        return this;
    }

    @Override
    public Set<T> retainAll(Predicate<T> predicate) {
        backingSet.removeIf(predicate.negate());
        return this;
    }

    public Set<T> union(T element) {
        return new Set<>(backingSet).add(element);
    }

    public Set<T> union(Collection<T> other) {
        return new Set<>(backingSet).addAll(other);
    }

    public Set<T> union(Set<T> other) {
        return new Set<>(backingSet).addAll(other.backingSet);
    }

    public Set<T> subtract(T element) {
        return new Set<>(backingSet).remove(element);
    }

    public Set<T> subtract(Collection<T> other) {
        return new Set<>(backingSet).removeAll(other);
    }

    public Set<T> subtract(Set<T> other) {
        return new Set<>(backingSet).removeAll(other.backingSet);
    }

    @Override
    public Set<T> intersection(T element) {
        return new Set<>(backingSet).retainAll(java.util.List.of(element));
    }

    public Set<T> intersection(Collection<T> other) {
        return new Set<>(backingSet).retainAll(other);
    }

    public Set<T> intersection(Set<T> other) {
        return new Set<>(backingSet).retainAll(other.backingSet);
    }

    @Override
    public Set<T> complement(T element) {
        return new Set<>(java.util.List.of(element)).removeAll(this.backingSet);
    }

    public Set<T> complement(Collection<T> universal) {
        return new Set<>(universal).removeAll(this.backingSet);
    }

    public Set<T> complement(Set<T> universal) {
        return new Set<>(universal.backingSet).removeAll(this.backingSet);
    }

    @Override
    public boolean areDisjoint(T element) {
        return false;
    }

    @Override
    public boolean areDisjoint(Collection<T> other) {
        return !containsAny(other);
    }

    @Override
    public boolean areDisjoint(Set<T> other) {
        return false;
    }

    @Override
    public <C> Set<C> map(Function<T, C> mapper) {
        Set<C> result = new Set<>();

        for (T element : backingSet) {
            result.add(mapper.apply(element));
        }

        return result;
    }

    @Override
    public <C> Set<C> mapMulti(Function<T, Collection<C>> mapper) {
        Set<C> result = new Set<>();

        for (T element : backingSet) {
            result.addAll(mapper.apply(element));
        }

        return result;
    }

    @Override
    public HashSet<T> set() {
        return new HashSet<>(backingSet);
    }

    @Override
    public List<T> list() {
        return new List<>(backingSet);
    }

    @Override
    public Iterator<T> iterator() {
        return backingSet.iterator();
    }

    @Override
    public int hashCode() {
        return backingSet.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Set<?> set && backingSet.equals(set.backingSet);
    }
}
