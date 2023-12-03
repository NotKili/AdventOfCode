package dev.notkili.aoc.shared.input;

import dev.notkili.aoc.shared.Solution;

import java.util.Objects;

public class CharInput implements Input<CharInput> {
    private final char c;

    public CharInput(char c) {
        this.c = c;
    }

    public char asChar() {
        return c;
    }

    public int asInt() {
        return c;
    }

    public IntInput asIntInput() {
        return new IntInput(c);
    }

    @Override
    public int compareTo(CharInput o) {
        return Character.compare(c, o.c);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CharInput charInput = (CharInput) o;
        return c == charInput.c;
    }

    @Override
    public int hashCode() {
        return Objects.hash(c);
    }

    @Override
    public String toString() {
        return c + "";
    }

    public void print() {
        System.out.print(c);
    }

    @Override
    public Solution asSolution() {
        return new Solution(c + "");
    }
}
