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
        if (o == null) return false;
        
        switch (o) {
            case CharInput ci -> {
                return c == ci.c;
            }
            case Character ch -> {
                return this.c == ch;
            }
            case IntInput oi -> {
                return c == (char) oi.asInt();
            }
            case Integer i -> {
                return c == (char) i.intValue();
            }
            case StringInput si -> {
                return si.length().asInt() == 1 && c == si.str().charAt(0);
            }
            case String s -> {
                return s.length() == 1 && c == s.charAt(0);
            }
            default -> {
                return false;
            }
        }
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
    public Solution solution() {
        return new Solution(c + "");
    }
}
