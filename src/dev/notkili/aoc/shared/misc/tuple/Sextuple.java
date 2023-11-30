package dev.notkili.aoc.shared.misc.tuple;

import java.util.Objects;

public class Sextuple<A, B, C, D, E, F> {
    private final A a;
    private final B b;
    private final C c;
    private final D d;
    private final E e;
    private final F f;

    public Sextuple(A a, B b, C c, D d, E e, F f) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
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

    public F getF() {
        return f;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sextuple<?, ?, ?, ?, ?, ?> sextuple)) return false;
        return a.equals(sextuple.a) && b.equals(sextuple.b) && c.equals(sextuple.c) && d.equals(sextuple.d) && e.equals(sextuple.e) && f.equals(sextuple.f);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b, c, d, e, f);
    }
}
