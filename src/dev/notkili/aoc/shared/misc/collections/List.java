package dev.notkili.aoc.shared.misc.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Predicate;

public class List<I> implements Iterable<I> {
    private final ArrayList<I> backingList;

    public static <I> List<I> of() {
        return new List<>();
    }

    public static <I> List<I> of(I element) {
        return new List<>(java.util.List.of(element));
    }

    public static <I> List<I> of(Collection<I> initialElements) {
        return new List<>(initialElements);
    }

    public List() {
        backingList = new ArrayList<>();
    }

    public List(ArrayList<I> backingList) {
        this.backingList = backingList;
    }

    public List(Collection<I> initialElements) {
        backingList = new ArrayList<>(initialElements);
    }

    public I get(int index) {
        return backingList.get(index);
    }

    public List<I> add(I element) {
        backingList.add(element);
        return this;
    }

    public List<I> addAll(Collection<I> elements) {
        backingList.addAll(elements);
        return this;
    }

    public boolean contains(I element) {
        return backingList.contains(element);
    }

    public boolean containsAll(Collection<I> elements) {
        return backingList.containsAll(elements);
    }

    public boolean containsAny(Collection<I> elements) {
        return elements.stream().anyMatch(backingList::contains);
    }

    public List<I> remove(int index) {
        backingList.remove(index);
        return this;
    }

    public List<I> remove(I element) {
        backingList.remove(element);
        return this;
    }

    public List<I> removeAll(Collection<I> elements) {
        backingList.removeAll(elements);
        return this;
    }

    public List<I> removeIf(Predicate<I> predicate) {
        backingList.removeIf(predicate);
        return this;
    }

    public List<I> replace(int index, I element) {
        if (index < 0 || index >= backingList.size()) {
            throw new IndexOutOfBoundsException("Replace: Index " + index + " is out of bounds for list of size " + backingList.size());
        }

        backingList.set(index, element);
        return this;
    }

    public List<I> replace(int index, Function<I, I> modifier) {
        if (index < 0 || index >= backingList.size()) {
            throw new IndexOutOfBoundsException("Replace: Index " + index + " is out of bounds for list of size " + backingList.size());
        }

        backingList.set(index, modifier.apply(backingList.get(index)));
        return this;
    }

    public List<I> replaceFirst(I oldElement, I newElement) {
        int index = backingList.indexOf(oldElement);

        if (index == -1) {
            throw new IllegalArgumentException("Replace: Element " + oldElement + " is not in list");
        }

        backingList.set(index, newElement);
        return this;
    }

    public List<I> replaceAll(I oldElement, I newElement) {
        Set<Integer> indices = new Set<>();

        for (int i = 0; i < backingList.size(); i++) {
            if (backingList.get(i).equals(oldElement)) {
                indices.add(i);
            }
        }

        if (indices.isEmpty()) {
            return this;
        }

        for (int index : indices) {
            backingList.set(index, newElement);
        }

        return this;
    }

    public List<I> clear() {
        backingList.clear();
        return this;
    }

    public int size() {
        return backingList.size();
    }

    public boolean isEmpty() {
        return backingList.isEmpty();
    }

    public ArrayList<I> toArrayList() {
        return new ArrayList<>(backingList);
    }

    public Set<I> toSet() {
        return new Set<>(backingList);
    }

    @Override
    public Iterator<I> iterator() {
        return backingList.iterator();
    }
}
