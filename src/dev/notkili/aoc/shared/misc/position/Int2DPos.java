package dev.notkili.aoc.shared.misc.position;

import dev.notkili.aoc.shared.input.IntInput;
import dev.notkili.aoc.shared.misc.collections.Set;
import dev.notkili.aoc.shared.misc.tuple.Tuple;

import java.util.Objects;

public class Int2DPos {
    private final int x;
    private final int y;

    public Int2DPos(IntInput x, IntInput y) {
        this.x = x.asInt();
        this.y = y.asInt();
    }

    public Int2DPos(Tuple<IntInput, IntInput> tuple) {
        this.x = tuple.getA().asInt();
        this.y = tuple.getB().asInt();
    }

    public Int2DPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Int2DPos(Int2DPos pos) {
        this.x = pos.x;
        this.y = pos.y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Int2DPos add(Int2DPos pos) {
        return new Int2DPos(x + pos.x, y + pos.y);
    }

    public Int2DPos add(int x, int y) {
        return new Int2DPos(this.x + x, this.y + y);
    }

    public Int2DPos sub(Int2DPos pos) {
        return new Int2DPos(x - pos.x, y - pos.y);
    }

    public Int2DPos sub(int x, int y) {
        return new Int2DPos(this.x - x, this.y - y);
    }

    public Int2DPos north() {
        return new Int2DPos(x, y + 1);
    }

    public Int2DPos north(int n) {
        return new Int2DPos(x, y + n);
    }

    public Int2DPos northEast() {
        return new Int2DPos(x + 1, y + 1);
    }

    public Int2DPos northEast(int n) {
        return new Int2DPos(x + n, y + n);
    }

    public Int2DPos northEast(int east, int north) {
        return new Int2DPos(this.x + east, this.y + north);
    }

    public Int2DPos east() {
        return new Int2DPos(x + 1, y);
    }

    public Int2DPos east(int n) {
        return new Int2DPos(x + n, y);
    }

    public Int2DPos southEast() {
        return new Int2DPos(x + 1, y - 1);
    }

    public Int2DPos southEast(int n) {
        return new Int2DPos(x + n, y - n);
    }

    public Int2DPos southEast(int east, int south) {
        return new Int2DPos(this.x + east, this.y - south);
    }

    public Int2DPos south() {
        return new Int2DPos(x, y - 1);
    }

    public Int2DPos south(int n) {
        return new Int2DPos(x, y - n);
    }

    public Int2DPos southWest() {
        return new Int2DPos(x - 1, y - 1);
    }

    public Int2DPos southWest(int n) {
        return new Int2DPos(x - n, y - n);
    }

    public Int2DPos southWest(int west, int south) {
        return new Int2DPos(this.x - west, this.y - south);
    }

    public Int2DPos west() {
        return new Int2DPos(x - 1, y);
    }

    public Int2DPos west(int n) {
        return new Int2DPos(x - n, y);
    }

    public Int2DPos northWest() {
        return new Int2DPos(x - 1, y + 1);
    }

    public Int2DPos northWest(int n) {
        return new Int2DPos(x - n, y + n);
    }

    public Int2DPos northWest(int west, int north) {
        return new Int2DPos(this.x - west, this.y + north);
    }

    public Set<Int2DPos> getNeighbours(boolean includeDiagonals) {
        if(includeDiagonals) {
            return Set.of(
                    new Int2DPos(x + 1, y),
                    new Int2DPos(x - 1, y),
                    new Int2DPos(x, y + 1),
                    new Int2DPos(x, y - 1),
                    new Int2DPos(x + 1, y + 1),
                    new Int2DPos(x + 1, y - 1),
                    new Int2DPos(x - 1, y + 1),
                    new Int2DPos(x - 1, y - 1)
            );
        }

        return Set.of(
                new Int2DPos(x + 1, y),
                new Int2DPos(x - 1, y),
                new Int2DPos(x, y + 1),
                new Int2DPos(x, y - 1)
        );
    }

    public double distance(Int2DPos pos) {
        return Math.sqrt(Math.pow(pos.x - x, 2) + Math.pow(pos.y - y, 2));
    }

    public double distance(int x, int y) {
        return Math.sqrt(Math.pow(x - this.x, 2) + Math.pow(y - this.y, 2));
    }

    public double manhattan(Int2DPos pos) {
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
        Int2DPos int2DPos = (Int2DPos) o;
        return x == int2DPos.x && y == int2DPos.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Int2DPos{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
