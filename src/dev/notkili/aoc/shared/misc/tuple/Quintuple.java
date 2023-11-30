package dev.notkili.aoc.shared.misc.tuple;

import java.util.Objects;

public class Quintuple<A, B, C, D, E> {
    private final A a;
    private final B b;
    private final C c;
    private final D d;
    private final E e;

    public Quintuple(A a, B b, C c, D d, E e) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
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

    public D getD() {
        return d;
    }

    public E getE() {
        return e;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Quintuple<?, ?, ?, ?, ?> quintuple)) return false;
        return a.equals(quintuple.a) && b.equals(quintuple.b) && c.equals(quintuple.c) && d.equals(quintuple.d) && e.equals(quintuple.e);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b, c, d, e);
    }
}
