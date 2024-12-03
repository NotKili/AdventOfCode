package dev.notkili.aoc.solutions.twenty_four;

import dev.notkili.aoc.shared.Pattern;
import dev.notkili.aoc.shared.input.LongInput;
import dev.notkili.aoc.shared.input.StringInput;
import dev.notkili.aoc.shared.parse.InputParser;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;

public class Day3 {
    public static void main(String[] args) {
        System.out.println("Solution 1:");

        new InputParser(2024, 3).getInput().ifPresent(strInput -> {
            strInput
                    .all(Pattern.of("mul\\(\\d+,\\d+\\)"))
                    .mapTo(str -> {
                        return str.firstNumber(0).asLong() * str.firstNumber(1).asLong();
                    })
                    .reduce(Long::sum)
                    .map(LongInput::new)
                    .orElseThrow()
                    .solution()
                    .print();
        });

        System.out.println("\nSolution 2:");

        new InputParser(2024, 3).getInput().ifPresent(strInput -> {
            var list = strInput
                    .all(
                            Pattern.builder()
                                    .then("mul\\(\\d+,\\d+\\)")
                                    .or("don't\\(\\)")
                                    .or("do\\(\\)")
                                    .build()
                    );
            
            var sum = new LongInput(0);
            var enable = true;
            
            for (var s : list) {
                if (s.equals("don't()")) {
                    enable = false;
                } else if (s.equals("do()")) {
                    enable = true;
                } else {
                    if (!enable) {
                        continue;
                    }
                    
                    sum = sum.add(s.firstNumber(0).asLong() * s.firstNumber(1).asLong());
                }
            }
            
            sum.solution().print();
        });
    }
}
