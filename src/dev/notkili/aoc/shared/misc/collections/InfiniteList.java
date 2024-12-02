package dev.notkili.aoc.shared.misc.collections;

import dev.notkili.aoc.shared.misc.collections.list.List;

import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Function;

public class InfiniteList<T> extends List<T> {
    public InfiniteList() {
    }

    @SafeVarargs
    public InfiniteList(T... initialElements) {
        super(initialElements);
    }

    public InfiniteList(Collection<T> initialElements) {
        super(initialElements);
    }

    @Override
    public T get(int index) {
        return super.get(mapIndex(index));
    }

    @Override
    public T get(long index) {
        return super.get(mapIndex(index));
    }

    @Override
    public List<T> add(int index, T element) {
        return super.add(mapIndex(index), element);
    }

    @Override
    public List<T> add(long index, T element) {
        return super.add(mapIndex(index), element);
    }

    @Override
    public List<T> rem(int index) {
        return super.rem(mapIndex(index));
    }

    @Override
    public List<T> rem(long index) {
        return super.rem(mapIndex(index));
    }

    @Override
    public List<T> repl(int index, T element) {
        return super.repl(mapIndex(index), element);
    }

    @Override
    public List<T> repl(long index, T element) {
        return super.repl(mapIndex(index), element);
    }

    @Override
    public List<T> repl(int index, Function<T, T> modifier) {
        return super.repl(mapIndex(index), modifier);
    }

    @Override
    public List<T> repl(long index, Function<T, T> modifier) {
        return super.repl(mapIndex(index), modifier);
    }

    @Override
    public InfiniteList<T> infinite() {
        return this;
    }

    @Override
    public <I> InfiniteList<I> map(Function<T, I> mapper) {
        return new InfiniteList<>(this.backingList.stream().map(mapper).toList());
    }

    @Override
    public Iterator<T> iterator() {
        return new AscItr();
    }

    public AscItr ascendingIterator() {
        return new AscItr();
    }

    public DescItr descendingIterator() {
        return new DescItr();
    }

    private int mapIndex(int index) {
        var size = size();
        return (index % size + size) % size;
    }

    private int mapIndex(long index) {
        return mapIndex((int) (index % Integer.MAX_VALUE));
    }

    public class AscItr implements Iterator<T> {
        int cursor;
        int expectedSize = size();
        InfiniteList<T> backing = InfiniteList.this;

        AscItr() {}

        @Override
        public boolean hasNext() {
            return true;
        }

        /**
         * Returns the element the iterator is currently pointing to
         * @return The current element
         */
        public T thisElement() {
            return backing.get(cursor);
        }

        /**
         * Returns the element the cursor is currently pointing to and increments the cursor
         * @return The current element
         */
        @Override
        public T next() {
            checkForCoModification();
            return get(cursor++);
        }

        /**
         * Removes the element the cursor is currently pointing to, the cursor now points to the next element
         */
        @Override
        public void remove() {
            checkForCoModification();
            expectedSize--;
            InfiniteList.this.rem(cursor);
        }

        @Override
        public void forEachRemaining(Consumer<? super T> action) {
            throw new UnsupportedOperationException("This will never terminate in an infinite list");
        }

        final void checkForCoModification() {
            if (expectedSize != size())
                throw new ConcurrentModificationException();
        }
    }

    public class DescItr extends AscItr {
        DescItr() {
            cursor = size() - 1;
        }

        /**
         * Returns the element the cursor is currently pointing to and decrements the cursor
         * @return The current element
         */
        @Override
        public T next() {
            checkForCoModification();
            return get(cursor--);
        }

        /**
         * Removes the element the cursor is currently pointing to, the cursor now points to the previous element
         */
        @Override
        public void remove() {
            checkForCoModification();
            expectedSize--;
            InfiniteList.this.rem(cursor);
            cursor--;
        }
    }
}
