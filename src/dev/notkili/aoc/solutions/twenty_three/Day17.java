package dev.notkili.aoc.solutions.twenty_three;

import dev.notkili.aoc.shared.input.ArrayInput2D;
import dev.notkili.aoc.shared.input.IntInput;
import dev.notkili.aoc.shared.misc.TimeTracker;
import dev.notkili.aoc.shared.misc.collections.set.Set;
import dev.notkili.aoc.shared.misc.position.Direction;
import dev.notkili.aoc.shared.misc.position.Int2DPos;
import dev.notkili.aoc.shared.misc.tuple.Triple;
import dev.notkili.aoc.shared.misc.tuple.Tuple;
import dev.notkili.aoc.shared.parse.InputParser;

import java.util.*;

public class Day17 {
    public static void main(String[] args) {
        TimeTracker.trackTime(Day17::part1);
        TimeTracker.trackTime(Day17::part2);
    }

    private static void part1() {
        new InputParser(2023, 17).getInput().ifPresent(input -> {
            var tiles = ArrayInput2D.parse(input);
            var backing = tiles.getBackingArray();

            var map = new HashMap<Int2DPos, Integer>();

            for (int y = 0; y < backing.length; y++) {
                for (int x = 0; x < backing[y].length; x++) {
                    map.put(new Int2DPos(x, y), new IntInput(backing[y][x].asChar() + "").asInt());
                }
            }

            var end = new Int2DPos(backing[0].length - 1, backing.length - 1);

            calculateSP(map, end, 1, 3).solution().print(); //.submit(2023, 17, 1);
        });
    }

    private static void part2() {
        new InputParser(2023, 17).getInput().ifPresent(input -> {
            var tiles = ArrayInput2D.parse(input);
            var backing = tiles.getBackingArray();

            var map = new HashMap<Int2DPos, Integer>();

            for (int y = 0; y < backing.length; y++) {
                for (int x = 0; x < backing[y].length; x++) {
                    map.put(new Int2DPos(x, y), new IntInput(backing[y][x].asChar() + "").asInt());
                }
            }

            var end = new Int2DPos(backing[0].length - 1, backing.length - 1);
            calculateSP(map, end, 4, 10).solution().print(); //.submit(2023, 17, 2);
        });
    }

    private static IntInput calculateSP(Map<Int2DPos, Integer> blocks, Int2DPos destination, int minMovement, int maxMovement) {
        var start = new Triple<Int2DPos, Int2DPos, Integer>(new Int2DPos(0, 0), null, 0);
        var visited = new Set<Tuple<Int2DPos, Int2DPos>>();
        var costs = new HashMap<Tuple<Int2DPos, Int2DPos>, Integer>();
        var queue = new PriorityQueue<Triple<Int2DPos, Int2DPos, Integer>>(Comparator.comparingInt(Triple::getC));

        queue.add(start);

        while (!queue.isEmpty()) {
            var element = queue.poll();

            if (element.getA().equals(destination)) {
                return new IntInput(element.getC());
            }

            var tmp = new Tuple<>(element.getA(), element.getB());
            if (visited.contains(tmp)) {
                continue;
            }

            visited.add(tmp);

            for (var dir : Direction.CARD) {
                var direction = dir.delta();
                var pathCosts = 0;

                if (element.getB() != null && (direction.equals(element.getB()) || direction.equals(element.getB().rotate180()))) {
                    continue;
                }

                for (int i = 1; i <= maxMovement; i++) {
                    var newPos = element.getA().add(direction.mul(i));

                    if (!blocks.containsKey(newPos)) {
                        break;
                    }

                    pathCosts += blocks.get(newPos);

                    if (i < minMovement)
                        continue;

                    var subPathCosts = element.getC() + pathCosts;

                    if (costs.containsKey(new Tuple<>(newPos, direction)) && costs.get(new Tuple<>(newPos, direction)) <= subPathCosts) {
                        continue;
                    }

                    costs.put(new Tuple<>(newPos, direction), subPathCosts);
                    queue.add(new Triple<>(newPos, direction, subPathCosts));
                }
            }
        }

        throw new NoSuchElementException("No path found");
    }
}
