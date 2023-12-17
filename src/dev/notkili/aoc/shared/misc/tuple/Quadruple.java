package dev.notkili.aoc.shared.misc.tuple;

import java.util.Objects;

public class Quadruple<A, B, C, D> {
    private final A a;
    private final B b;
    private final C c;
    private final D d;

    public Quadruple(A a, B b, C c, D d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Quadruple<?, ?, ?, ?> quadruple)) return false;
        return a.equals(quadruple.a) && b.equals(quadruple.b) && c.equals(quadruple.c) && d.equals(quadruple.d);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b, c, d);
    }

    @Override
    public String toString() {
        return "(" + a + ", " + b + ", " + c + ", " + d + ')';
    }
}
