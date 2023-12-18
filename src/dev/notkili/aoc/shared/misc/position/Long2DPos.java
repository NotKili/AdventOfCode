package dev.notkili.aoc.shared.misc.position;

import dev.notkili.aoc.shared.input.IntInput;
import dev.notkili.aoc.shared.input.LongInput;
import dev.notkili.aoc.shared.misc.collections.list.List;
import dev.notkili.aoc.shared.misc.collections.set.Set;
import dev.notkili.aoc.shared.misc.tuple.Tuple;

import java.util.Objects;

public class Long2DPos implements Comparable<Long2DPos> {
    private final long x;
    private final long y;

    public Long2DPos(LongInput x, LongInput y) {
        this.x = x.asLong();
        this.y = y.asLong();
    }

    public Long2DPos(Tuple<LongInput, LongInput> tuple) {
        this.x = tuple.getA().asLong();
        this.y = tuple.getB().asLong();
    }

    public Long2DPos(long x, long y) {
        this.x = x;
        this.y = y;
    }

    public Long2DPos(Long2DPos pos) {
        this.x = pos.x;
        this.y = pos.y;
    }

    public long getX() {
        return x;
    }

    public long getY() {
        return y;
    }

    public Long2DPos add(Int2DPos pos) {
        return new Long2DPos(x + pos.getX(), y + pos.getY());
    }

    public Long2DPos add(Long2DPos pos) {
        return new Long2DPos(x + pos.x, y + pos.y);
    }

    public Long2DPos add(int x, int y) {
        return new Long2DPos(this.x + x, this.y + y);
    }

    public Long2DPos add(long x, long y) {
        return new Long2DPos(this.x + x, this.y + y);
    }

    public Long2DPos sub(Int2DPos pos) {
        return new Long2DPos(x - pos.getX(), y - pos.getY());
    }

    public Long2DPos sub(Long2DPos pos) {
        return new Long2DPos(x - pos.x, y - pos.y);
    }

    public Long2DPos sub(int x, int y) {
        return new Long2DPos(this.x - x, this.y - y);
    }

    public Long2DPos sub(long x, long y) {
        return new Long2DPos(this.x - x, this.y - y);
    }

    public Long2DPos mul(Int2DPos pos) {
        return new Long2DPos(x * pos.getX(), y * pos.getY());
    }

    public Long2DPos mul(Long2DPos pos) {
        return new Long2DPos(x * pos.x, y * pos.y);
    }

    public Long2DPos mul(int x, int y) {
        return new Long2DPos(this.x * x, this.y * y);
    }

    public Long2DPos mul(long x, long y) {
        return new Long2DPos(this.x * x, this.y * y);
    }

    public Long2DPos mul(int n) {
        return new Long2DPos(x * n, y * n);
    }

    public Long2DPos mul(long n) {
        return new Long2DPos(x * n, y * n);
    }

    public Long2DPos rotate90() {
        return new Long2DPos(y, -x);
    }

    public Long2DPos rotate90(int n) {
        var pos = new Long2DPos(this);
        for (int i = 0; i < n; i++) {
            pos = pos.rotate90();
        }
        return pos;
    }

    public Long2DPos rotate180() {
        return new Long2DPos(-x, -y);
    }

    public Long2DPos north() {
        return add(Direction.NORTH.getDelta());
    }

    public Long2DPos north(int n) {
        return add(Direction.NORTH.getDelta().mul(n));
    }

    public Long2DPos northEast() {
        return add(Direction.NORTH_EAST.getDelta());
    }

    public Long2DPos northEast(int n) {
        return add(Direction.NORTH_EAST.getDelta().mul(n));
    }

    public Long2DPos northEast(int east, int north) {
        return add(Direction.NORTH_EAST.getDelta().mul(east, north));
    }

    public Long2DPos east() {
        return add(Direction.EAST.getDelta());
    }

    public Long2DPos east(int n) {
        return add(Direction.EAST.getDelta().mul(n));
    }

    public Long2DPos southEast() {
        return add(Direction.SOUTH_EAST.getDelta());
    }

    public Long2DPos southEast(int n) {
        return add(Direction.SOUTH_EAST.getDelta().mul(n));
    }

    public Long2DPos southEast(int east, int south) {
        return add(Direction.SOUTH_EAST.getDelta().mul(east, south));
    }

    public Long2DPos south() {
        return add(Direction.SOUTH.getDelta());
    }

    public Long2DPos south(int n) {
        return add(Direction.SOUTH.getDelta().mul(n));
    }

    public Long2DPos southWest() {
        return add(Direction.SOUTH_WEST.getDelta());
    }

    public Long2DPos southWest(int n) {
        return add(Direction.SOUTH_WEST.getDelta().mul(n));
    }

    public Long2DPos southWest(int west, int south) {
        return add(Direction.SOUTH_WEST.getDelta().mul(west, south));
    }

    public Long2DPos west() {
        return add(Direction.WEST.getDelta());
    }

    public Long2DPos west(int n) {
        return add(Direction.WEST.getDelta().mul(n));
    }

    public Long2DPos northWest() {
        return add(Direction.NORTH_WEST.getDelta());
    }

    public Long2DPos northWest(int n) {
        return add(Direction.NORTH_WEST.getDelta().mul(n));
    }

    public Long2DPos northWest(int west, int north) {
        return add(Direction.NORTH_WEST.getDelta().mul(west, north));
    }

    public Set<Long2DPos> getNeighbours(Direction... directions) {
        var set = new Set<Long2DPos>();


        for (Direction direction : directions) {
            set.add(add(direction.getDelta()));
        }

        return set;
    }

    public Set<Long2DPos> getNeighbours(List<Direction> directions) {
        var set = new Set<Long2DPos>();


        for (Direction direction : directions) {
            set.add(add(direction.getDelta()));
        }

        return set;
    }

    public Set<Long2DPos> getNeighbours(Set<Direction> directions) {
        var set = new Set<Long2DPos>();


        for (Direction direction : directions) {
            set.add(add(direction.getDelta()));
        }

        return set;
    }

    public double distance(Long2DPos pos) {
        return Math.sqrt(Math.pow(pos.x - x, 2) + Math.pow(pos.y - y, 2));
    }

    public double distance(int x, int y) {
        return Math.sqrt(Math.pow(x - this.x, 2) + Math.pow(y - this.y, 2));
    }

    public double manhattan(Long2DPos pos) {
        return Math.abs(pos.x - x) + Math.abs(pos.y - y);
    }

    public double manhattan(int x, int y) {
        return Math.abs(x - this.x) + Math.abs(y - this.y);
    }

    public boolean isNegative() {
        return x < 0 || y < 0;
    }

    public boolean isOutside(int n) {
        return x > n || y > n;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Long2DPos Long2DPos = (Long2DPos) o;
        return x == Long2DPos.x && y == Long2DPos.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Long2DPos{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public int compareTo(Long2DPos o) {
        if (x == o.x) {
            return Long.compare(y, o.y);
        }
        return Long.compare(x, o.x);
    }
}
