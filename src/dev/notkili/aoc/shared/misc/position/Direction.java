package dev.notkili.aoc.shared.misc.position;

import dev.notkili.aoc.shared.input.IntInput;
import dev.notkili.aoc.shared.misc.collections.list.List;
import dev.notkili.aoc.shared.misc.collections.set.Set;

public enum Direction {
    N(0, -1),
    E(1, 0),
    S(0, 1),
    W(-1, 0),
    NE(1, -1),
    SE(1, 1),
    SW(-1, 1),
    NW(-1, -1);

    public static final Direction[] CARD = {N, E, S, W};

    public static final Set<Direction> CARD_SET = Set.of(N, E, S, W);

    public static final List<Direction> CARD_LIST = List.of(N, E, S, W);

    public static final Direction[] DIAG = {NE, SE, SW, NW};

    public static final Set<Direction> DIAG_SET = Set.of(NE, SE, SW, NW);

    public static final List<Direction> DIAG_LIST = List.of(NE, SE, SW, NW);

    public static final Direction[] ALL = {N, E, S, W, NE, SE, SW, NW};

    public static final Set<Direction> ALL_SET = Set.of(N, E, S, W, NE, SE, SW, NW);

    public static final List<Direction> ALL_LIST = List.of(N, E, S, W, NE, SE, SW, NW);

    private final Int2DPos delta;

    Direction(int x, int y) {
        this.delta = new Int2DPos(x, y);
    }
    
    public Direction opposite() {
        return switch (this) {
            case N -> S;
            case E -> W;
            case S -> N;
            case W -> E;
            case NE -> SW;
            case SE -> NW;
            case SW -> NE;
            case NW -> SE;
        };
    }

    public Direction nextClockwise(boolean diag) {
        return switch (this) {
            case N -> diag ? NE : E;
            case E -> diag ? SE : S;
            case S -> diag ? SW : W;
            case W -> diag ? NW : N;
            case NE -> diag ? E : SE;
            case SE -> diag ? S : SW;
            case SW -> diag ? W : NW;
            case NW -> diag ? N : NE;
        };
    }

    public Direction nextCounterClockwise(boolean diag) {
        return switch (this) {
            case N -> diag ? NW : W;
            case E -> diag ? NE : N;
            case S -> diag ? SE : E;
            case W -> diag ? SW : S;
            case NE -> diag ? N : NW;
            case SE -> diag ? E : NE;
            case SW -> diag ? S : SE;
            case NW -> diag ? W : SW;
        };
    }
    
    public int x() {
        return delta.getX();
    }
    
    public IntInput xInput() {
        return new IntInput(x());
    }
    
    public int y() {
        return delta.getY();
    }
    
    public IntInput yInput() {
        return new IntInput(y());
    }
    
    public Int2DPos delta() {
        return delta;
    }
}
