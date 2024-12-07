package dev.notkili.aoc.solutions.twenty_four;

import dev.notkili.aoc.shared.input.IntInput;
import dev.notkili.aoc.shared.input.ListInput;
import dev.notkili.aoc.shared.input.LongInput;
import dev.notkili.aoc.shared.input.StringInput;
import dev.notkili.aoc.shared.misc.tuple.Tuple;
import dev.notkili.aoc.shared.parse.InputParser;

public class Day7 {
    public static void main(String[] args) {
        System.out.println("Solution 1:");

        new InputParser(2024, 7).getInput().ifPresent(strInput -> {
            var lines = strInput.split("\n");
            
            var tuples = lines.mapTo(t -> t.tuple(": "));
            
            var tuples2 = tuples.mapTo(t -> new Tuple<>(t.getA(), t.getB().split(" ")));
            
            tuples2.mapTo(t -> {
                if (isRightEquation(t.getA().longInteger(), t.getB().toInts())) {
                    return t.getA().longInteger();
                }
                
                return IntInput.zero().asLongInput();
            }).reduce(LongInput::add).get().solution().print();
        });

        System.out.println("Solution 2:");

        new InputParser(2024, 7).getInput().ifPresent(strInput -> {
            var lines = strInput.split("\n");

            var tuples = lines.mapTo(t -> t.tuple(": "));

            var tuples2 = tuples.mapTo(t -> new Tuple<>(t.getA(), t.getB().split(" ")));

            tuples2.mapTo(t -> {
                if (isRightEquation2(t.getA().longInteger(), t.getB().toInts())) {
                    return t.getA().longInteger();
                }

                return IntInput.zero().asLongInput();
            }).reduce(LongInput::add).get().solution().print();
        });
    }
    
    private static boolean isRightEquation(LongInput goal, ListInput.IntListInput nums) {
        int operator = 0;
        
        while (operator < (int) Math.pow(2, nums.size() - 1)) {
            var bin = Integer.toBinaryString(operator);
            
            if (bin.length() < nums.size() - 1) {
                bin = StringInput.of("0").join(nums.size() - bin.length() - 1).str() + bin;
            }
            
            var binArr = bin.split("");
            var acc = nums.get(0).asLong();
            
            int i = 1;
            for (var op : binArr) {
                if (op.equals("0")) {
                    acc *= nums.get(i).asLong();
                } else {
                    acc += nums.get(i).asLong();
                }
                
                i++;
                
                if (i - 1 >= binArr.length) {
                    break;
                }
            }
            
            if (goal.asLong() == acc) {
                return true;
            }
            
            operator++;
        }
        
        return false;
    }

    private static boolean isRightEquation2(LongInput goal, ListInput.IntListInput nums) {
        int operator = 0;

        while (operator < (int) Math.pow(3, nums.size() - 1)) {
            var repr = getRepr(operator);
            
            if (repr.length() < nums.size() - 1) {
                repr = StringInput.of("0").join(nums.size() - repr.length() - 1).str() + repr;
            }
            
            var binArr = repr.split("");
            var acc = nums.get(0).asLong();

            int i = 1;
            for (var op : binArr) {
                if (op.equals("0")) {
                    acc *= nums.get(i).asLong();
                } else if (op.equals("1")) {
                    acc += nums.get(i).asLong();
                } else {
                    acc = new StringInput(Long.toString(acc)).concat(nums.get(i).toString()).longInteger().asLong();
                }

                i++;

                if (i - 1 >= binArr.length) {
                    break;
                }
            }

            if (goal.asLong() == acc) {
                return true;
            }

            operator++;
        }
        
        return false;
    }
    
    private static String getRepr(int num) {
        if (num == 0) {
            return "0";
        }
        
        StringBuilder sb = new StringBuilder();
        
        while (num > 2) {
            sb.append(num % 3);
            num /= 3;
        }

        sb.append(num % 3);
        
        return sb.reverse().toString();
    }
}
