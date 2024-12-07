package dev.notkili.aoc.solutions.twenty_four;

import dev.notkili.aoc.shared.input.IntInput;
import dev.notkili.aoc.shared.input.ListInput;
import dev.notkili.aoc.shared.input.LongInput;
import dev.notkili.aoc.shared.misc.TimeTracker;
import dev.notkili.aoc.shared.misc.collections.IterTools;
import dev.notkili.aoc.shared.misc.collections.list.List;
import dev.notkili.aoc.shared.parse.InputParser;

public class Day7 {
    public static void main(String[] args) {
        System.out.println("Solution 1:");

        TimeTracker.trackTime(() -> {
            new InputParser(2024, 7).getInput().ifPresent(strInput -> {
                var input = strInput
                        .split("\n")
                        .mapTo(t -> t.tuple(": "))
                        .mapTo(t -> t.mapB(b -> b.split(" ")));

                input.mapTo(t -> {
                    if (isRightEquation(t.getA().longInteger(), t.getB().toInts())) {
                        return t.getA().longInteger();
                    }

                    return IntInput.zero().asLongInput();
                }).reduce(LongInput::add).get().solution().print();
            });
        });
        
        System.out.println("Solution 2:");
        
        TimeTracker.trackTime(() -> {
            new InputParser(2024, 7).getInput().ifPresent(strInput -> {
                var input = strInput
                        .split("\n")
                        .mapTo(t -> t.tuple(": "))
                        .mapTo(t -> t.mapB(b -> b.split(" ")));

                input.mapTo(t -> {
                    if (isRightEquation2(t.getA().longInteger(), t.getB().toInts())) {
                        return t.getA().longInteger();
                    }

                    return IntInput.zero().asLongInput();
                }).reduce(LongInput::add).get().solution().print();
            });
        });
    }
    
    private static boolean isRightEquation(LongInput goal, ListInput.IntListInput nums) {
        for (var ops : IterTools.product(List.of(0, 1), nums.size() - 1)) {
            var acc = nums.get(0).asLong();
            int i = 1;
            
            for (var op : ops) {
                if (op == 0) {
                    acc *= nums.get(i).asLong();
                } else {
                    acc += nums.get(i).asLong();
                }

                i++;

                if (i - 1 >= ops.size()) {
                    break;
                }
            }

            if (goal.asLong() == acc) {
                return true;
            }
        }
        
        return false;
    }

    private static boolean isRightEquation2(LongInput goal, ListInput.IntListInput nums) {
        for (var ops : IterTools.product(List.of(0L, 1L, 2L), nums.size() - 1)) {
            var acc = nums.get(0).asLong();
            int i = 1;
            
            for (var op : ops) {
                if (op == 0) {
                    acc *= nums.get(i).asLong();
                } else if (op == 1) {
                    acc += nums.get(i).asLong();
                } else {
                    var asL = nums.get(i).asLong();
                    acc = acc * (long) Math.pow(10, (long) (Math.log10(asL)) + 1) + asL;
                }

                i++;

                if (i - 1 >= ops.size()) {
                    break;
                }
            }

            if (goal.asLong() == acc) {
                return true;
            }
        }
        
        return false;
    }
}
