package dev.notkili.aoc.solutions.twenty_three;

import dev.notkili.aoc.shared.input.LongInput;
import dev.notkili.aoc.shared.input.StringInput;
import dev.notkili.aoc.shared.misc.collections.set.Set;
import dev.notkili.aoc.shared.misc.position.Int2DPos;
import dev.notkili.aoc.shared.misc.tuple.Tuple;
import dev.notkili.aoc.shared.parse.InputParser;

import java.util.HashMap;

public class Day11 {
    public static void main(String[] args) {
        part1();

        part2();
    }

    // 9157600
    private static void part1() {
        new InputParser(2023, 11).getInput().ifPresent(input -> {
            calculateMinDistanceSum(calculateGalaxies(inputToArray(input), 2)).solution().print();//.submit(2023, 11, 2);
        });
    }

    private static void part2() {
        new InputParser(2023, 11).getInput().ifPresent(input -> {
            calculateMinDistanceSum(calculateGalaxies(inputToArray(input), 1000000)).solution().print();//.submit(2023, 11, 2);
        });
    }

    private static String[][] inputToArray(StringInput input) {
        var split = input.splitAt("\n");
        var array = new String[split.size()][split.get(0).length().asInt()];

        for (int i = 0; i < split.size(); i++) {
            array[i] = split.get(i).asString().split("");
        }

        return array;
    }

    private static Set<Int2DPos> calculateGalaxies(String[][] array, int expandBy) {
        Set<Int2DPos> galaxies = new Set<>();
        HashMap<Integer, Integer> xOffsets = new HashMap<>();
        HashMap<Integer, Integer> yOffsets = new HashMap<>();

        int offset = 0;
        for (int rows = 0; rows < array.length; rows++) {
            if (!containsAnyRow(array, rows)) {
                offset += expandBy;
                continue;
            }

            yOffsets.put(rows, offset);
            offset++;
        }

        offset = 0;
        for (int cols = 0; cols < array[0].length; cols++) {
            if (!containsAnyCol(array, cols)) {
                offset += expandBy;
                continue;
            }

            xOffsets.put(cols, offset);
            offset++;
        }

        for (int y = 0; y < array.length; y++) {
            var row = array[y];
            for (int x = 0; x < row.length; x++) {
                if (row[x].equals("#")) {
                    galaxies.add(new Int2DPos(xOffsets.get(x), yOffsets.get(y)));
                }
            }
        }

        return galaxies;
    }

    private static boolean containsAnyCol(String[][] array, int colum) {
        for (String[] strings : array) {
            if (strings[colum].equals("#"))
                return true;
        }

        return false;
    }

    private static boolean containsAnyRow(String[][] array, int row) {
        for (int i = 0; i < array[0].length; i++) {
            if (array[row][i].equals("#"))
                return true;
        }

        return false;
    }

    private static LongInput calculateMinDistanceSum(Set<Int2DPos> galaxies) {
        Set<Tuple<Int2DPos, Int2DPos>> checkedPairs = new Set<>();
        LongInput sum = new LongInput(0);

        for (var elem : galaxies) {
            for (var elem2 : galaxies) {
                if (elem == elem2)
                    continue;

                if (checkedPairs.contains(new Tuple<>(elem, elem2)) || checkedPairs.contains(new Tuple<>(elem2, elem)))
                    continue;

                checkedPairs.add(new Tuple<>(elem, elem2));
                checkedPairs.add(new Tuple<>(elem2, elem));

                sum = sum.add((long) elem.manhattan(elem2));
            }
        }
        return sum;
    }
}
