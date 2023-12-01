package dev.notkili.aoc.shared.misc.collections;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.function.Predicate;

public class Set<I> implements Iterable<I> {
    private final HashSet<I> backingSet;

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

    public Set(HashSet<I> backingSet) {
        this.backingSet = backingSet;
    }

    public Set(Collection<I> initialElements) {
        backingSet = new HashSet<>(initialElements);
    }

    public Set(Set<I> other) {
        backingSet = new HashSet<>(other.backingSet);
    }

    public Set<I> add(I element) {
        backingSet.add(element);
        return this;
    }

    public Set<I> addAll(Collection<I> elements) {
        backingSet.addAll(elements);
        return this;
    }

    public Set<I> addAll(Set<I> other) {
        backingSet.addAll(other.backingSet);
        return this;
    }

    public Set<I> union(I element) {
        return new Set<>(backingSet).add(element);
    }

    public Set<I> union(Collection<I> other) {
        return new Set<>(backingSet).addAll(other);
    }

    public Set<I> union(Set<I> other) {
        return new Set<>(backingSet).addAll(other.backingSet);
    }

    public Set<I> subtract(I element) {
        return new Set<>(backingSet).remove(element);
    }

    public Set<I> subtract(Collection<I> other) {
        return new Set<>(backingSet).removeAll(other);
    }

    public Set<I> subtract(Set<I> other) {
        return new Set<>(backingSet).removeAll(other.backingSet);
    }

    public Set<I> intersection(Collection<I> other) {
        return new Set<>(backingSet).retainAll(other);
    }

    public Set<I> intersection(Set<I> other) {
        return new Set<>(backingSet).retainAll(other.backingSet);
    }

    public Set<I> complement(Collection<I> universal) {
        return new Set<>(universal).removeAll(this.backingSet);
    }

    public Set<I> complement(Set<I> universal) {
        return new Set<>(universal.backingSet).removeAll(this.backingSet);
    }

    public boolean contains(I element) {
        return backingSet.contains(element);
    }

    public boolean containsAll(Collection<I> elements) {
        return backingSet.containsAll(elements);
    }

    public boolean containsAll(Set<I> other) {
        return backingSet.containsAll(other.backingSet);
    }

    public boolean containsAny(Collection<I> elements) {
        return elements.stream().anyMatch(backingSet::contains);
    }

    public boolean containsAny(Set<I> other) {
        return other.backingSet.stream().anyMatch(backingSet::contains);
    }

    public boolean areDisjoint(Collection<I> other) {
        return !this.containsAny(other);
    }

    public Set<I> remove(I element) {
        backingSet.remove(element);
        return this;
    }

    public Set<I> removeAll(Collection<I> elements) {
        backingSet.removeAll(elements);
        return this;
    }

    public Set<I> removeAll(Predicate<I> predicate) {
        backingSet.removeIf(predicate);
        return this;
    }

    public Set<I> retainAll(Collection<I> elements) {
        backingSet.retainAll(elements);
        return this;
    }

    public Set<I> retainAll(Predicate<I> predicate) {
        backingSet.removeIf(predicate.negate());
        return this;
    }

    public Set<I> clear() {
        backingSet.clear();
        return this;
    }

    public HashSet<I> asHashSet() {
        return new HashSet<>(backingSet);
    }

    public List<I> asList() {
        return new List<>(backingSet);
    }

    public Set<I> copy() {
        return new Set<>(backingSet);
    }

    public int size() {
        return backingSet.size();
    }

    public boolean isEmpty() {
        return backingSet.isEmpty();
    }

    @Override
    public Iterator<I> iterator() {
        return backingSet.iterator();
    }
}
