package dev.notkili.aoc.solutions.twenty_three;

import dev.notkili.aoc.shared.input.IntInput;
import dev.notkili.aoc.shared.misc.collections.set.Set;
import dev.notkili.aoc.shared.misc.position.Int2DPos;
import dev.notkili.aoc.shared.parse.InputParser;

import java.util.HashMap;
import java.util.Map;

public class Day3 {
    public static void main(String[] args) {
        new InputParser(2023, 3).getInput().ifPresent(input -> {
            var lines = input.asString().split("\n");

            var yLen = lines.length;
            var map = new HashMap<Int2DPos, Character>();
            var numbers = new HashMap<Int2DPos, Integer>();
            var xLen = 0;
            var numBuilder = new StringBuilder();

            for (int y = 0; y < yLen; y++) {
                var line = lines[y];
                var chars = line.split("");
                xLen = chars.length;

                Set<Int2DPos> nums = new Set<>();

                for (int x = 0; x < chars.length; x++) {
                    var c = chars[x].charAt(0);
                    var pos = new Int2DPos(x, y);

                    map.put(pos, c);

                    if (Character.isDigit(c)) {
                        numBuilder.append(c);
                        nums.add(pos);
                    } else {
                        if (numBuilder.isEmpty())
                            continue;

                        var num = Integer.parseInt(numBuilder.toString());
                        numBuilder = new StringBuilder();

                        for (var numPos : nums) {
                            numbers.put(numPos, num);
                        }

                        nums.clear();
                    }
                }

                if (numBuilder.isEmpty())
                    continue;

                var num = Integer.parseInt(numBuilder.toString());
                numBuilder = new StringBuilder();

                for (var numPos : nums) {
                    numbers.put(numPos, num);
                }

                nums.clear();
            }

            Set<Int2DPos> visited = new Set<>();
            IntInput sum = new IntInput(0);

            for (int y = 0; y < yLen; y++) {
                for (int x = 0; x < xLen; x++) {
                    var pos = new Int2DPos(x, y);
                    var c = map.get(pos);

                    if (visited.contains(pos))
                        continue;

                    visited.add(pos);

                    if (Character.isDigit(c)) {
                        for (Int2DPos neighbour : pos.getNeighbours(true)) {

                            if (!map.containsKey(neighbour))
                                continue;

                            var neighbourC = map.get(neighbour);

                            if (!Character.isDigit(neighbourC) && !neighbourC.equals('.')) {
                                var num = numbers.get(pos);

                                var delta = 1;
                                while (true) {
                                    var leftmost = new Int2DPos(pos.getX() - delta, pos.getY());

                                    if (!map.containsKey(leftmost))
                                        break;

                                    var leftmostC = map.get(leftmost);

                                    if (Character.isDigit(leftmostC)) {
                                        visited.add(leftmost);
                                        delta++;
                                        continue;
                                    }

                                    break;
                                }

                                delta = 1;
                                while (true) {
                                    var rightmost = new Int2DPos(pos.getX() + delta, pos.getY());

                                    if (!map.containsKey(rightmost))
                                        break;

                                    var rightmostC = map.get(rightmost);

                                    if (Character.isDigit(rightmostC)) {
                                        visited.add(rightmost);
                                        delta++;
                                        continue;
                                    }

                                    break;
                                }

                                // Add num to sum
                                sum = sum.add(num);
                                break;
                            }
                        }
                    }
                }
            }
            sum.asSolution().print();//.submit(2023, 3, 1);



        });

        new InputParser(2023, 3).getInput().ifPresent(input -> {
            var lines = input.asString().split("\n");

            var yLen = lines.length;
            var map = new HashMap<Int2DPos, Character>();
            var numbers = new HashMap<Int2DPos, Integer>();
            var xLen = 0;
            var numBuilder = new StringBuilder();

            for (int y = 0; y < yLen; y++) {
                var line = lines[y];
                var chars = line.split("");
                xLen = chars.length;

                Set<Int2DPos> nums = new Set<>();

                for (int x = 0; x < chars.length; x++) {
                    var c = chars[x].charAt(0);
                    var pos = new Int2DPos(x, y);

                    map.put(pos, c);

                    if (Character.isDigit(c)) {
                        numBuilder.append(c);
                        nums.add(pos);
                    } else {
                        if (numBuilder.isEmpty())
                            continue;

                        var num = Integer.parseInt(numBuilder.toString());
                        numBuilder = new StringBuilder();

                        for (var numPos : nums) {
                            numbers.put(numPos, num);
                        }

                        nums.clear();
                    }
                }

                if (numBuilder.isEmpty())
                    continue;

                var num = Integer.parseInt(numBuilder.toString());
                numBuilder = new StringBuilder();

                for (var numPos : nums) {
                    numbers.put(numPos, num);
                }

                nums.clear();
            }

            IntInput sum = new IntInput(0);

            for (int y = 0; y < yLen; y++) {
                for (int x = 0; x < xLen; x++) {
                    var pos = new Int2DPos(x, y);

                    var c = map.get(pos);

                    if (c.equals('*')) {
                        Set<Integer> adjacentNums = new Set<>();

                        for (Int2DPos neighbour : pos.getNeighbours(true)) {
                            if (!map.containsKey(neighbour))
                                continue;

                            if (numbers.containsKey(neighbour)) {
                                adjacentNums.add(numbers.get(neighbour));
                            }
                        }

                        if (adjacentNums.size() == 2) {
                            var l = adjacentNums.list();
                            var num = l.get(0) * l.get(1);

                            sum = sum.add(num);
                        }
                    }
                }
            }

            sum.asSolution().print();//.submit(2023, 3, 2);
        });
    }
}
