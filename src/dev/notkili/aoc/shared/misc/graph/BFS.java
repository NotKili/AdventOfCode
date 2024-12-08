package dev.notkili.aoc.shared.misc.graph;

import dev.notkili.aoc.shared.misc.functional.TriConsumer;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class BFS<T, A> {
    private final Queue<T> queue;
    private final A accumulator;
    private final TriConsumer<T, A, Queue<T>> transformer;
    
    public A search() {
        while (!queue.isEmpty()) {
            transformer.accept(queue.poll(), accumulator, queue);
        }
        
        return accumulator;
    }
    
    public BFS(Queue<T> queue, A accumulator, TriConsumer<T, A, Queue<T>> transformer) {
        this.queue = queue;
        this.accumulator = accumulator;
        this.transformer = transformer;
    }

    @SafeVarargs
    public static <T, A> Builder<T, A> with(A accumulator, T... initial) {
        return new Builder<>(accumulator, initial);
    }
    
    public static class Builder<T, A> {
        private Queue<T> queue = new LinkedList<>();
        private A accumulator;
        private TriConsumer<T, A, Queue<T>> transformer;

        @SafeVarargs
        public Builder(A accumulator, T... initial) {
            with(accumulator);
            with(initial);
        }

        public Builder<T, A> queue(Queue<T> queue) {
            this.queue = queue;
            return this;
        }
        
        @SafeVarargs
        public final Builder<T, A> with(T... elems) {
            queue.addAll(Arrays.asList(elems));
            return this;
        }
        
        public Builder<T, A> with(A accumulator) {
            this.accumulator = accumulator;
            return this;
        }
        
        public Builder<T, A> with(TriConsumer<T, A, Queue<T>> transformer) {
            this.transformer = transformer;
            return this;
        }
        
        public A search() {
            Objects.requireNonNull(queue, "Can not bsf without a queue");
            Objects.requireNonNull(accumulator, "Can not bsf without an accumulator");
            Objects.requireNonNull(transformer, "Can not bsf without a transformer");
            
            return new BFS<>(queue, accumulator, transformer).search();
        }
    }
}
