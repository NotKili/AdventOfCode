package dev.notkili.aoc.solutions.twenty_three;

import dev.notkili.aoc.shared.input.LongInput;
import dev.notkili.aoc.shared.input.StringInput;
import dev.notkili.aoc.shared.misc.TimeTracker;
import dev.notkili.aoc.shared.misc.collections.list.List;
import dev.notkili.aoc.shared.misc.tuple.Triple;
import dev.notkili.aoc.shared.misc.tuple.Tuple;
import dev.notkili.aoc.shared.parse.InputParser;

import java.util.Arrays;

public class Day13 {
    public static void main(String[] args) {
        TimeTracker.trackTime(Day13::part1);
        TimeTracker.trackTime(Day13::part2);
    }

    private static void part1() {
        new InputParser(2023, 13).getInput().ifPresent(input -> {
            var reflections = input.splitAt("\n\n").mapTo(Day13::parseMap).mapTo(d -> findReflection(d, null));

            var sumRow = reflections.findAll(Triple::getC).mapTo(Triple::getA).reduce(Integer::sum).orElse(0) * 100L;
            var sumCol = reflections.findAll(t -> !t.getC()).mapTo(Triple::getA).reduce(Integer::sum).orElse(0);

            new LongInput(sumRow + sumCol).solution().print();
        });
    }

    private static void part2() {
         new InputParser(2023, 13).getInput().ifPresent(input -> {
             var reflections = input.splitAt("\n\n").mapTo(Day13::parseMap).mapTo(map -> new Tuple<>(findReflection(map, null), createMaps(map))).mapTo(maps -> {
                 var results = new List<Triple<Integer, Integer, Boolean>>();

                 for (var map : maps.getB()) {
                        var result = findReflection(map, maps.getA());

                        if (result != null)
                            results.add(result);
                 }

                 return results.distinct().get(0);
             });

             var sumRow = reflections.findAll(Triple::getC).mapTo(Triple::getA).reduce(Integer::sum).orElse(0) * 100L;
             var sumCol = reflections.findAll(t -> !t.getC()).mapTo(Triple::getA).reduce(Integer::sum).orElse(0);

             new LongInput(sumRow + sumCol).solution().print();
         });
    }

    // True -> row, False -> column
    private static Triple<Integer, Integer, Boolean> findReflection(char[][] map, Triple<Integer, Integer, Boolean> skip) {
        for (int y = 0; y < map.length - 1; y++) {
            if (areMirroring(map[y], map[y + 1])) {
                var result = checkAllReflectionFromRow(new Tuple<>(y, y + 1), map);

                if (result != null && (skip == null || !skip.equals(new Triple<>(result.getA() + 1, result.getB() + 1, true)))) {
                    return new Triple<>(result.getA() + 1, result.getB() + 1, true);
                }
            }
        }

        for (int x = 0; x < map[0].length - 1; x++) {
            var column = extractColumn(map, x);
            var nextColumn = extractColumn(map, x + 1);

            if (areMirroring(column, nextColumn)) {
                var result = checkAllReflectionFromColumn(new Tuple<>(x, x + 1), map);

                if (result != null && (skip == null || !skip.equals(new Triple<>(result.getA() + 1, result.getB() + 1, false)))) {
                    return new Triple<>(result.getA() + 1, result.getB() + 1, false);
                }
            }
        }

        return null;
    }

    private static Tuple<Integer, Integer> checkAllReflectionFromRow(Tuple<Integer, Integer> from, char[][] map) {
        var y1 = from.getA();
        var y2 = from.getB();

        var maxDist = Math.max(map.length - y2, y1);

        for (int d = 1; d < maxDist; d++) {
            var y1d = y1 - d;
            var y2d = y2 + d;

            if (y1d < 0 || y2d >= map.length)
                return from;

            if (!areMirroring(map[y1d], map[y2d]))
                return null;
        }

        return from;
    }

    private static Tuple<Integer, Integer> checkAllReflectionFromColumn(Tuple<Integer, Integer> from, char[][] map) {
        var x1 = from.getA();
        var x2 = from.getB();

        var maxDist = Math.max(map[0].length - x2, x1);

        for (int d = 1; d < maxDist; d++) {
            var x1d = x1 - d;
            var x2d = x2 + d;

            if (x1d < 0 || x2d >= map[0].length)
                return from;

            if (!areMirroring(extractColumn(map, x1d), extractColumn(map, x2d)))
                return null;
        }

        return from;
    }

    private static boolean areMirroring(char[] a, char[] b) {
        if ((a == null || b == null) && a != b)
            return true;

        if (a.length != b.length)
            return false;

        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i])
                return false;
        }

        return true;
    }

    private static char[] extractColumn(char[][] map, int at) {
        var column = new char[map.length];

        for (int y = 0; y < map.length; y++) {
            column[y] = map[y][at];
        }

        return column;
    }

    private static char[][] parseMap(StringInput input) {
        return input.splitAt("\n").mapTo(StringInput::chars).asList().toArray(char[][]::new);
    }

    private static List<char[][]> createMaps(char[][] original) {
        var list = new List<char[][]>();

        for (int y = 0; y < original.length; y++) {
            for (int x = 0; x < original[y].length; x++) {

                char[][] map = new char[original.length][original[y].length];

                for (int yd = 0; yd < original.length; yd++) {
                    map[yd] = Arrays.copyOf(original[yd], original[yd].length);
                }

                map[y][x] = map[y][x] == '#' ? '.' : '#';

                list.add(map);
            }
        }

        return list;
    }
}
