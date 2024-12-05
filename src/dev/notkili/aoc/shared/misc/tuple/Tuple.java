package dev.notkili.aoc.shared.misc.tuple;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Tuple<A, B> {
    public static <A, B> Tuple<A, B> of(A a, B b) {
        return new Tuple<>(a, b);
    }
    
    private final A a;
    private final B b;

    public Tuple(A a, B b) {
        this.a = a;
        this.b = b;
    }

    public A getA() {
        return a;
    }

    public B getB() {
        return b;
    }

    public Tuple<B, A> swap() {
        return new Tuple<>(b, a);
    }

    public <C> Tuple<A, C> swapB(C c) {
        return new Tuple<>(a, c);
    }

    public <C> Tuple<C, B> swapA(C c) {
        return new Tuple<>(c, b);
    }

    public <C> Tuple<C, B> mapA(Function<A, C> mapper) {
        return new Tuple<>(mapper.apply(a), b);
    }

    public <C> Tuple<A, C> mapB(Function<B, C> mapper) {
        return new Tuple<>(a, mapper.apply(b));
    }

    public <C, D> Tuple<C, D> map(Function<A, C> mapperA, Function<B, D> mapperB) {
        return new Tuple<>(mapperA.apply(a), mapperB.apply(b));
    }

    public void then(BiConsumer<A, B> consumer) {
        consumer.accept(a, b);
    }

    public <C> C then(BiFunction<A, B, C> function) {
        return function.apply(a, b);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tuple<?, ?> tuple = (Tuple<?, ?>) o;
        return Objects.equals(a, tuple.a) && Objects.equals(b, tuple.b);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }

    @Override
    public String toString() {
        return "(" + a + ", " + b + ")";
    }
}
