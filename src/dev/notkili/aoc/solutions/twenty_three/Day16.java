package dev.notkili.aoc.solutions.twenty_three;

import dev.notkili.aoc.shared.input.ArrayInput2D;
import dev.notkili.aoc.shared.input.CharInput;
import dev.notkili.aoc.shared.input.IntInput;
import dev.notkili.aoc.shared.misc.TimeTracker;
import dev.notkili.aoc.shared.misc.collections.set.Set;
import dev.notkili.aoc.shared.misc.position.Int2DPos;
import dev.notkili.aoc.shared.misc.tuple.Tuple;
import dev.notkili.aoc.shared.parse.InputParser;

import java.util.HashMap;
import java.util.LinkedList;

public class Day16 {
    public static void main(String[] args) {
        TimeTracker.trackTime(Day16::part1);
        TimeTracker.trackTime(Day16::part2);
    }

    private static void part1() {
        new InputParser(2023, 16).getInput().ifPresent(input -> {
            var tiles = new HashMap<Int2DPos, CharInput>();

            var arr = ArrayInput2D.parse(input).getBackingArray();

            for (int y = 0; y < arr.length; y++) {
                for (int x = 0; x < arr[y].length; x++) {
                    tiles.put(new Int2DPos(x, y), arr[y][x]);
                }
            }

            calcEnergizedTile(tiles, new Int2DPos(-1, 0), new Tuple<>(1, 0)).asSolution().print();
        });
    }

    private static void part2() {
        new InputParser(2023, 16).getInput().ifPresent(input -> {
            var tiles = new HashMap<Int2DPos, CharInput>();

            var arr = ArrayInput2D.parse(input).getBackingArray();

            for (int y = 0; y < arr.length; y++) {
                for (int x = 0; x < arr[y].length; x++) {
                    tiles.put(new Int2DPos(x, y), arr[y][x]);
                }
            }

            IntInput max = new IntInput(0);

            for (int x = 0; x < arr[0].length; x++) {
                max = max.max(calcEnergizedTile(tiles, new Int2DPos(x, -1), new Tuple<>(0, 1)));
            }

            for (int x = 0; x < arr[0].length; x++) {
                max = max.max(calcEnergizedTile(tiles, new Int2DPos(x, arr.length), new Tuple<>(0, -1)));
            }

            for (int y = 0; y < arr.length; y++) {
                max = max.max(calcEnergizedTile(tiles, new Int2DPos(-1, y), new Tuple<>(1, 0)));
            }

            for (int y = 0; y < arr.length; y++) {
                max = max.max(calcEnergizedTile(tiles, new Int2DPos(arr[0].length, y), new Tuple<>(-1, 0)));
            }

            max.asSolution().print();
        });
    }

    private static IntInput calcEnergizedTile(HashMap<Int2DPos, CharInput> tiles, Int2DPos start, Tuple<Integer, Integer> direction) {
        var energizedTiles = new Set<Int2DPos>();
        var encountered = new Set<Tuple<Int2DPos, Tuple<Integer, Integer>>>();
        var queue = new LinkedList<Tuple<Int2DPos, Tuple<Integer, Integer>>>();
        queue.add(new Tuple<>(start, direction));

        while (!queue.isEmpty()) {
            var current = queue.pop();

            if (encountered.contains(current)) {
                continue;
            }

            encountered.add(current);
            energizedTiles.add(current.getA());

            var next = current.getA().add(current.getB().getA(), current.getB().getB());

            if (!tiles.containsKey(next)) {
                continue;
            }

            switch (tiles.get(next).asChar()) {
                case '.':
                    queue.add(new Tuple<>(next, current.getB()));
                    break;
                case '/':
                    queue.add(new Tuple<>(next, new Tuple<>(-current.getB().getB(), -current.getB().getA())));
                    break;
                case '\\':
                    queue.add(new Tuple<>(next, new Tuple<>(current.getB().getB(), current.getB().getA())));
                    break;
                case '-':
                    if (current.getB().equals(new Tuple<>(1, 0)) || current.getB().equals(new Tuple<>(-1, 0))) {
                        queue.add(new Tuple<>(next, current.getB()));
                    } else {
                        queue.add(new Tuple<>(next, new Tuple<>(1, 0)));
                        queue.add(new Tuple<>(next, new Tuple<>(-1, 0)));
                    }
                    break;
                case '|':
                    if (current.getB().equals(new Tuple<>(0, 1)) || current.getB().equals(new Tuple<>(0, -1))) {
                        queue.add(new Tuple<>(next, current.getB()));
                    } else {
                        queue.add(new Tuple<>(next, new Tuple<>(0, 1)));
                        queue.add(new Tuple<>(next, new Tuple<>(0, -1)));
                    }
                    break;
            }
        }

        return new IntInput(energizedTiles.size() - 1);
    }
}
