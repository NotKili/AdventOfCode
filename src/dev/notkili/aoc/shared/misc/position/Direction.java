package dev.notkili.aoc.shared.misc.position;

import dev.notkili.aoc.shared.misc.collections.list.List;
import dev.notkili.aoc.shared.misc.collections.set.Set;

public enum Direction {
    NORTH(0, -1),
    EAST(1, 0),
    SOUTH(0, 1),
    WEST(-1, 0),
    NORTH_EAST(1, -1),
    SOUTH_EAST(1, 1),
    SOUTH_WEST(-1, 1),
    NORTH_WEST(-1, -1);

    public static final Direction[] CardArr = {NORTH, EAST, SOUTH, WEST};

    public static final Set<Direction> CardSet = Set.of(NORTH, EAST, SOUTH, WEST);

    public static final List<Direction> CardList = List.of(NORTH, EAST, SOUTH, WEST);

    public static final Direction[] DiagArr = {NORTH_EAST, SOUTH_EAST, SOUTH_WEST, NORTH_WEST};

    public static final Set<Direction> DiagSet = Set.of(NORTH_EAST, SOUTH_EAST, SOUTH_WEST, NORTH_WEST);

    public static final List<Direction> DiagList = List.of(NORTH_EAST, SOUTH_EAST, SOUTH_WEST, NORTH_WEST);

    public static final Direction[] AllArr = {NORTH, EAST, SOUTH, WEST, NORTH_EAST, SOUTH_EAST, SOUTH_WEST, NORTH_WEST};

    public static final Set<Direction> AllSet = Set.of(NORTH, EAST, SOUTH, WEST, NORTH_EAST, SOUTH_EAST, SOUTH_WEST, NORTH_WEST);

    public static final List<Direction> AllList = List.of(NORTH, EAST, SOUTH, WEST, NORTH_EAST, SOUTH_EAST, SOUTH_WEST, NORTH_WEST);

    private final Int2DPos delta;

    Direction(int x, int y) {
        this.delta = new Int2DPos(x, y);
    }

    public Direction nextClockwise(boolean diag) {
        return switch (this) {
            case NORTH -> diag ? NORTH_EAST : EAST;
            case EAST -> diag ? SOUTH_EAST : SOUTH;
            case SOUTH -> diag ? SOUTH_WEST : WEST;
            case WEST -> diag ? NORTH_WEST : NORTH;
            case NORTH_EAST -> diag ? EAST : SOUTH_EAST;
            case SOUTH_EAST -> diag ? SOUTH : SOUTH_WEST;
            case SOUTH_WEST -> diag ? WEST : NORTH_WEST;
            case NORTH_WEST -> diag ? NORTH : NORTH_EAST;
        };
    }

    public Direction nextCounterClockwise(boolean diag) {
        return switch (this) {
            case NORTH -> diag ? NORTH_WEST : WEST;
            case EAST -> diag ? NORTH_EAST : NORTH;
            case SOUTH -> diag ? SOUTH_EAST : EAST;
            case WEST -> diag ? SOUTH_WEST : SOUTH;
            case NORTH_EAST -> diag ? NORTH : NORTH_WEST;
            case SOUTH_EAST -> diag ? EAST : NORTH_EAST;
            case SOUTH_WEST -> diag ? SOUTH : SOUTH_EAST;
            case NORTH_WEST -> diag ? WEST : SOUTH_WEST;
        };
    }

    public Int2DPos getDelta() {
        return delta;
    }
}
