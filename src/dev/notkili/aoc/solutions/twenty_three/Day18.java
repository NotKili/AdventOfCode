package dev.notkili.aoc.solutions.twenty_three;

import dev.notkili.aoc.shared.input.IntInput;
import dev.notkili.aoc.shared.input.LongInput;
import dev.notkili.aoc.shared.misc.TimeTracker;
import dev.notkili.aoc.shared.misc.collections.list.List;
import dev.notkili.aoc.shared.misc.position.Direction;
import dev.notkili.aoc.shared.misc.position.Long2DPos;
import dev.notkili.aoc.shared.misc.tuple.Tuple;
import dev.notkili.aoc.shared.parse.InputParser;

public class Day18 {
    public static void main(String[] args) {
        TimeTracker.trackTime(Day18::part1);
        TimeTracker.trackTime(Day18::part2);
    }

    private static void part1() {
        new InputParser(2023, 18).getInput().ifPresent(input -> {
            var l = new List<>(
                    input
                            .split("\n")
                            .mapTo(str -> str.triple(" "))
                            .mapTo(tr -> new Tuple<>(mapDir(tr.getA().str()), tr.getB().integer().asLong()))
                            .asList()
            );

            new LongInput(new Digger().dig(l)).solution().print(); //submit(2023, 18, 1);
        });
    }

    private static void part2() {
        new InputParser(2023, 18).getInput().ifPresent(input -> {
            var l = new List<>(
                    input.split("\n")
                            .mapTo(str -> str.triple(" "))
                            .mapTo(tr -> tr.getC().str().replace("(", "").replace(")", ""))
                            .mapTo(s -> new Tuple<>(
                                    mapDir(new IntInput(s.charAt(s.length() - 1) + "").asInt()),
                                    IntInput.hex(s.substring(1, s.length() - 1)).asLong()
                                    )
                            )
                            .asList()
            );

            new LongInput(new Digger().dig(l)).solution().print(); //submit(2023, 18, 2);
        });
    }

    public static class Digger {
        public Digger() {
        }

        public long dig(List<Tuple<Direction, Long>> directions) {
            var corners = new List<Long2DPos>();
            var edgeLen = 0L;

            corners.add(new Long2DPos(0, 0));

            for (var dir : directions) {
                var last = corners.last();
                var len = dir.getB();

                edgeLen += len;
                corners.add(last.add(new Long2DPos(len, len).mul(dir.getA().delta())));
            }

            var area = 0.0;
            corners = corners.infinite();

            for (int i = 0; i < corners.size(); i++) {
                area += ((corners.get(i).getY() + corners.get(i + 1).getY()) * (corners.get(i).getX() - corners.get(i + 1).getX())) / 2.0;
            }

            return (long) (area + edgeLen / 2 + 1);
        }
    }

    private static Direction mapDir(String s) {
        switch (s) {
            case "R":
                return Direction.E;
            case "D":
                return Direction.S;
            case "L":
                return Direction.W;
            case "U":
                return Direction.N;
        }

        throw new IllegalArgumentException("Invalid direction: " + s);
    }

    private static Direction mapDir(int s) {
        switch (s) {
            case 0:
                return Direction.E;
            case 1:
                return Direction.S;
            case 2:
                return Direction.W;
            case 3:
                return Direction.N;
        }

        throw new IllegalArgumentException("Invalid direction: " + s);
    }
}
