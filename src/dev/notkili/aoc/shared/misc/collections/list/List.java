package dev.notkili.aoc.shared.misc.collections.list;

import dev.notkili.aoc.shared.misc.collections.InfiniteList;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;

public class List<T> implements IList<T, List<T>> {
    protected final ArrayList<T> backingList;

    public static <I> List<I> of() {
        return new List<>();
    }

    public static <I> List<I> of(I element) {
        return new List<>(java.util.List.of(element));
    }

    @SafeVarargs
    public static <I> List<I> of(I... elements) {
        return new List<>(java.util.List.of(elements));
    }

    public static <I> List<I> of(Collection<I> initialElements) {
        return new List<>(initialElements);
    }

    public List() {
        backingList = new ArrayList<>();
    }

    @SafeVarargs
    public List(T... initialElements) {
        backingList = new ArrayList<>(Arrays.asList(initialElements));
    }

    public List(Collection<T> initialElements) {
        backingList = new ArrayList<>(initialElements);
    }

    @Override
    public boolean empty() {
        return backingList.isEmpty();
    }

    @Override
    public int size() {
        return backingList.size();
    }

    /**
     * Supports negative indices for reverse access
     * @param index index of element to get
     * @return Element at index
     */
    @Override
    public T get(int index) {
        return backingList.get(index < 0 ? (index + size()) : index);
    }

    @Override
    public T get(long index) {
        if (index >= Integer.MAX_VALUE)
            throw new IndexOutOfBoundsException("Index too large for int");

        return get((int) index);
    }

    public T last() {
        return get(size() - 1);
    }

    @Override
    public List<T> add(T element) {
        backingList.add(element);
        return this;
    }

    @Override
    public List<T> add(int index, T element) {
        backingList.add(index, element);
        return this;
    }

    @Override
    public List<T> add(long index, T element) {
        if (index >= Integer.MAX_VALUE)
            throw new IndexOutOfBoundsException("Index too large for int");

        return add((int) index, element);
    }

    @Override
    public List<T> addAll(Collection<T> elements) {
        backingList.addAll(elements);
        return this;
    }

    @Override
    public List<T> addAll(List<T> other) {
        backingList.addAll(other.backingList);
        return this;
    }

    @Override
    public boolean contains(T element) {
        return backingList.contains(element);
    }

    @Override
    public boolean containsAll(Collection<T> elements) {
        return backingList.containsAll(elements);
    }

    @Override
    public boolean containsAny(Collection<T> elements) {
        for (T element : elements) {
            if (contains(element))
                return true;
        }

        return false;
    }

    @Override
    public boolean containsAny(Predicate<T> predicate) {
        for (T element : backingList) {
            if (predicate.test(element))
                return true;
        }

        return false;
    }

    @Override
    public boolean containsAll(List<T> other) {
        return backingList.containsAll(other.backingList);
    }

    @Override
    public Optional<T> find(Predicate<T> predicate) {
        return backingList.stream().filter(predicate).findFirst();
    }

    @Override
    public List<T> findAll(Predicate<T> predicate) {
        return new List<>(backingList.stream().filter(predicate).toList());
    }

    @Override
    public List<T> remove(int index) {
        this.backingList.remove(index);
        return this;
    }

    @Override
    public List<T> remove(long index) {
        if (index >= Integer.MAX_VALUE)
            throw new IndexOutOfBoundsException("Index too large for int");

        return remove((int) index);
    }

    @Override
    public List<T> remove(T element) {
        this.backingList.remove(element);
        return this;
    }

    @Override
    public List<T> removeAll(Collection<T> elements) {
        this.backingList.removeAll(elements);
        return this;
    }

    @Override
    public List<T> removeIf(Predicate<T> predicate) {
        this.backingList.removeIf(predicate);
        return this;
    }

    @Override
    public List<T> sublist(int start, int end) {
        return new List<>(this.backingList.subList(start, end));
    }

    @Override
    public List<T> sublist(long start, long end) {
        if (start >= Integer.MAX_VALUE)
            throw new IndexOutOfBoundsException("Index '" + start + "' too large for int");

        if (end >= Integer.MAX_VALUE)
            throw new IndexOutOfBoundsException("Index '" + end + "' too large for int");

        return sublist((int) start, (int) end);
    }

    @Override
    public List<T> sublist(int start) {
        return new List<>(this.backingList.subList(start, size()));
    }

    @Override
    public List<T> sublist(long start) {
        if (start >= Integer.MAX_VALUE)
            throw new IndexOutOfBoundsException("Index '" + start + "' too large for int");

        return sublist((int) start);
    }

    @Override
    public List<List<T>> group(int n) {
        if (size() % n != 0) throw new IllegalArgumentException("Cannot group list of size " + size() + " into groups of size " + n + ".");

        List<List<T>> groups = new List<>();

        for (int i = 0; i < size(); i += n) {
            groups.add(sublist(i, i + n));
        }

        return groups;
    }

    @Override
    public List<T> replace(int index, T element) {
        this.backingList.set(index, element);
        return this;
    }

    @Override
    public List<T> replace(long index, T element) {
        if (index >= Integer.MAX_VALUE)
            throw new IndexOutOfBoundsException("Index too large for int");

        return replace((int) index, element);
    }

    @Override
    public List<T> replace(int index, Function<T, T> modifier) {
        this.backingList.set(index, modifier.apply(this.backingList.get(index)));
        return this;
    }

    @Override
    public List<T> replace(long index, Function<T, T> modifier) {
        if (index >= Integer.MAX_VALUE)
            throw new IndexOutOfBoundsException("Index too large for int");

        return replace((int) index, modifier);
    }

    @Override
    public List<T> replaceFirst(T oldElement, T newElement) {
        for (int i = 0; i < size(); i++) {
            if (get(i).equals(oldElement)) {
                replace(i, newElement);
                break;
            }
        }

        return this;
    }

    @Override
    public List<T> replaceAll(T oldElement, T newElement) {
        for (int i = 0; i < size(); i++) {
            if (get(i).equals(oldElement)) {
                replace(i, newElement);
            }
        }

        return this;
    }

    @Override
    public List<T> clear() {
        this.backingList.clear();
        return this;
    }

    @Override
    public List<T> sort() {
        this.backingList.sort(null);
        return this;
    }

    @Override
    public List<T> sort(Comparator<T> comparator) {
        this.backingList.sort(comparator);
        return this;
    }

    @Override
    public List<T> copy() {
        return new List<>(this.backingList);
    }

    @Override
    public Optional<T> reduce(BinaryOperator<T> accumulator) {
        return this.backingList.stream().reduce(accumulator);
    }

    @Override
    public List<T> distinct() {
        return new List<>(this.backingList.stream().distinct().toList());
    }

    @Override
    public <I> List<I> map(Function<T, I> mapper) {
        return new List<>(this.backingList.stream().map(mapper).toList());
    }

    @Override
    public Optional<T> max(Comparator<T> comparator) {
        return this.backingList.stream().max(comparator);
    }

    @Override
    public Optional<T> min(Comparator<T> comparator) {
        return this.backingList.stream().min(comparator);
    }

    @Override
    public ArrayList<T> arrayList() {
        return new ArrayList<>(this.backingList);
    }

    public InfiniteList<T> infinite() {
        return new InfiniteList<>(this.backingList);
    }

    @Override
    public dev.notkili.aoc.shared.misc.collections.set.Set<T> set() {
        return new dev.notkili.aoc.shared.misc.collections.set.Set<>(this.backingList);
    }

    @Override
    public List<T> multiply(long times) {
        List<T> result = new List<>();

        for (long i = 0; i < times; i++) {
            result.addAll(this);
        }

        return result;
    }

    @Override
    public void print() {
        System.out.println("List: " + this.backingList);
    }

    @Override
    public String toString() {
        return "List: " + backingList;
    }

    @Override
    public Iterator<T> iterator() {
        return this.backingList.iterator();
    }

    @Override
    public int hashCode() {
        return Objects.hash(backingList);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof List) {
            return this.backingList.equals(((List<?>) obj).backingList);
        }

        return false;
    }
}
