package dev.notkili.aoc.solutions.twenty_four;

import dev.notkili.aoc.shared.input.Input;
import dev.notkili.aoc.shared.input.IntInput;
import dev.notkili.aoc.shared.input.StringInput;
import dev.notkili.aoc.shared.misc.collections.Counter;
import dev.notkili.aoc.shared.parse.InputParser;

import java.util.Comparator;

public class Day1 {
    public static void main(String[] args) {
        System.out.println("Solution 1:");
        var result = new InputParser(2024, 1).getInput().map(strInput -> {
            var lines = strInput.splitAt("\n");
            var firstNums = lines.mapToString(str -> str.asTuple("   ").getA()).mapToInt(StringInput::asInt).mapTo(IntInput::asInt).asList();
            firstNums.sort(Comparator.naturalOrder());
            var secondNums = lines.mapToString(str -> str.asTuple("   ").getB()).mapToInt(StringInput::asInt).mapTo(IntInput::asInt).asList();
            secondNums.sort(Comparator.naturalOrder());
            
            IntInput sum = new IntInput(0);
            
            for (int i = 0; i < firstNums.size(); i++) {
               sum = sum.add(Math.abs(firstNums.get(i) - secondNums.get(i)));
            }
            
            return sum;
        });
        
        // result.ifPresent(r -> r.asSolution().submit(2024, 1, 1));
        result.ifPresent(Input::print);

        System.out.println("\nSolution 2:");
        var result2 = new InputParser(2024, 1).getInput().map(strInput -> {
            var lines = strInput.splitAt("\n");
            var firstNums = lines.mapToString(str -> str.asTuple("   ").getA()).mapToInt(StringInput::asInt).mapTo(IntInput::asInt).asList();
            var secondNums = lines.mapToString(str -> str.asTuple("   ").getB()).mapToInt(StringInput::asInt).mapTo(IntInput::asInt).asList();
            var counts = new Counter<>(secondNums);
            
            IntInput sum = new IntInput(0);

            for (Integer firstNum : firstNums) {
                sum = sum.add(
                        firstNum * counts.getCountFor(firstNum).asInt()
                );
            }
            
            return sum;
        });

        // result2.ifPresent(r -> r.asSolution().submit(2024, 1, 2));
        result2.ifPresent(Input::print);
    }
}
