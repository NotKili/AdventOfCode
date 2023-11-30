package dev.notkili.aoc.shared.misc.position;

import dev.notkili.aoc.shared.input.IntInput;
import dev.notkili.aoc.shared.misc.tuple.Triple;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Int3DPos {
    private final int x;
    private final int y;
    private final int z;

    public Int3DPos(IntInput x, IntInput y, IntInput z) {
        this.x = x.asInt();
        this.y = y.asInt();
        this.z = z.asInt();
    }

    public Int3DPos(Triple<IntInput, IntInput, IntInput> triple) {
        this.x = triple.getA().asInt();
        this.y = triple.getB().asInt();
        this.z = triple.getC().asInt();
    }

    public Int3DPos(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Int3DPos(Int3DPos pos) {
        this.x = pos.x;
        this.y = pos.y;
        this.z = pos.z;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public Int3DPos add(Int3DPos pos) {
        return new Int3DPos(x + pos.x, y + pos.y, z + pos.z);
    }

    public Int3DPos add(int x, int y, int z) {
        return new Int3DPos(this.x + x, this.y + y, this.z + z);
    }

    public Int3DPos sub(Int3DPos pos) {
        return new Int3DPos(x - pos.x, y - pos.y, z - pos.z);
    }

    public Int3DPos sub(int x, int y, int z) {
        return new Int3DPos(this.x - x, this.y - y, this.z - z);
    }

    public List<Int3DPos> getNeighbours(boolean includeDiagonal) {
        List<Int3DPos> neighbours = new ArrayList<>();

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                for (int dz = -1; dz <= 1; dz++) {
                    if (!(dx == 0 && dy == 0 && dz == 0)) {
                        if (!includeDiagonal && (dx != 0 && dy != 0 && dz != 0)) {
                            continue;
                        }
                        neighbours.add(new Int3DPos(x + dx, y + dy, z + dz));
                    }
                }
            }
        }

        return neighbours;
    }

    public double distance(Int3DPos pos) {
        return Math.sqrt(Math.pow(x - pos.getX(), 2) + Math.pow(y - pos.getY(), 2) + Math.pow(z - pos.getZ(), 2));
    }

    public double distance(int x, int y, int z) {
        return Math.sqrt(Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2) + Math.pow(this.z - z, 2));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Int3DPos int3DPos = (Int3DPos) o;
        return x == int3DPos.x && y == int3DPos.y && z == int3DPos.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }
}
