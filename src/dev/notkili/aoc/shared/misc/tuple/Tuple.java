package dev.notkili.aoc.shared.misc.tuple;

import java.util.Objects;
import java.util.function.Function;

public class Tuple<A, B> {
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
