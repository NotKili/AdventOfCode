package dev.notkili.aoc.shared.misc.tuple;

import java.util.Objects;

public class Triple<A, B, C> {
    private final A a;
    private final B b;
    private final C c;

    public Triple(A a, B b, C c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public A getA() {
        return a;
    }

    public B getB() {
        return b;
    }

    public C getC() {
        return c;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Triple<?, ?, ?> triple)) return false;
        return a.equals(triple.a) && b.equals(triple.b) && c.equals(triple.c);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b, c);
    }
}
