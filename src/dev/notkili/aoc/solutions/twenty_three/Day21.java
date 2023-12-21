package dev.notkili.aoc.solutions.twenty_three;

import dev.notkili.aoc.shared.input.ArrayInput2D;
import dev.notkili.aoc.shared.input.LongInput;
import dev.notkili.aoc.shared.misc.TimeTracker;
import dev.notkili.aoc.shared.misc.collections.list.List;
import dev.notkili.aoc.shared.misc.collections.set.Set;
import dev.notkili.aoc.shared.misc.position.Direction;
import dev.notkili.aoc.shared.misc.position.Int2DPos;
import dev.notkili.aoc.shared.misc.tuple.Tuple;
import dev.notkili.aoc.shared.parse.InputParser;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Day21 {
    public static void main(String[] args) {
        TimeTracker.trackTime(Day21::part1);
        TimeTracker.trackTime(Day21::part2);
    }

    public static void part1() {
        new InputParser(2023, 21).getInput().ifPresent(input -> {
            HashMap<Int2DPos, String> map = new HashMap<>();

            var backing = ArrayInput2D.parse(input).getBackingArray();
            Int2DPos start = null;

            for (int y = 0; y < backing.length; y++) {
                for (int x = 0; x < backing[y].length; x++) {
                    map.put(new Int2DPos(x, y), backing[y][x].asChar() + "");

                    if (backing[y][x].asChar() == 'S') {
                        start = new Int2DPos(x, y);
                    }
                }
            }

            calculateMaxReachable(64, start, map, false, backing.length, backing[0].length).asSolution().print(); //.submit(2023, 21, 1);
        });
    }

    public static List<Long> numbers = new List<>();

    public static void part2() {
        new InputParser(2023, 21).getInput().ifPresent(input -> {
            HashMap<Int2DPos, String> map = new HashMap<>();

            var backing = ArrayInput2D.parse(input).getBackingArray();
            Int2DPos start = null;

            for (int y = 0; y < backing.length; y++) {
                for (int x = 0; x < backing[y].length; x++) {
                    map.put(new Int2DPos(x, y), backing[y][x].asChar() + "");

                    if (backing[y][x].asChar() == 'S') {
                        start = new Int2DPos(x, y);
                        map.put(start, ".");
                    }
                }
            }

            var list = new List<Long>();

            calculateMaxReachable(394, start, map, true, backing.length, backing[0].length); //.submit(2023, 21, 2);

            for (int i = 0; i < 394; i++) {
                if (i % 131 == 65) {
                    list.add(numbers.get(i));
                }
            }

            var x = 26501365 / 131;
            var l1 = list.get(0);
            var l2 = list.get(1);
            var l3 = list.get(2);

            // ax^2 + bx + c
            // c = l1
            // a = (l3 + c - 2*l2) / 2
            // b = l2 - c - a

            new LongInput((long) (((l3 + l1 - 2*l2) / 2) * Math.pow(x, 2) + (l2 - l1 - (l3 + l1 - 2*l2) / 2) * x + l1)).asSolution().print(); //submit(2023, 21, 2);
        });
    }

    private static LongInput calculateMaxReachable(int dist, Int2DPos start, HashMap<Int2DPos, String> map, boolean resize, int yLen, int xLen) {
        var queue = new PriorityQueue<Tuple<Int2DPos, Integer>>(Comparator.comparing(Tuple::getB));
        var visited = new Set<Int2DPos>();
        var offset = 1;

        var sizesByStep = new List<Long>();

        queue.add(new Tuple<>(start, 0));
        var currentDist = 0;

        while (!queue.isEmpty()) {
            var current = queue.poll();

            if (currentDist != current.getB()) {
                currentDist = current.getB();

                if (resize) {
                    sizesByStep.add((long) visited.size());
                }

                visited = new Set<>();
            }

            if (visited.contains(current.getA())) {
                continue;
            }

            visited.add(current.getA());

            if (current.getB() == dist)
                continue;

            for (var neigh : current.getA().getNeighbours(Direction.CardArr)) {
                // Resize
                if (!map.containsKey(neigh)) {
                    if (!resize)
                        continue;

                    var minX = map.keySet().stream().mapToInt(Int2DPos::getX).min().getAsInt();
                    var maxX = map.keySet().stream().mapToInt(Int2DPos::getX).max().getAsInt();
                    var minY = map.keySet().stream().mapToInt(Int2DPos::getY).min().getAsInt();
                    var maxY = map.keySet().stream().mapToInt(Int2DPos::getY).max().getAsInt();

                    for (int y = minY; y <= maxY; y++) {
                        for (int x = minX; x <= maxX; x++) {
                            var pos = new Int2DPos(x, y);

                            for (var dir : Direction.AllArr) {
                                var newPos = pos.add(dir.getDelta().mul(offset * xLen));

                                if (map.containsKey(newPos))
                                    continue;

                                map.put(newPos, map.get(pos));
                            }
                        }
                    }

                    offset++;
                }

                if (map.get(neigh).equals("#")) {
                    continue;
                }

                if (visited.contains(neigh)) {
                    continue;
                }

                queue.add(new Tuple<>(neigh, current.getB() + 1));
            }
        }

        numbers = sizesByStep;
        return new LongInput(visited.size());
    }
}
