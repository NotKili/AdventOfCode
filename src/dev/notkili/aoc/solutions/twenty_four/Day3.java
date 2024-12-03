package dev.notkili.aoc.solutions.twenty_four;

import dev.notkili.aoc.shared.Pattern;
import dev.notkili.aoc.shared.input.StringInput;
import dev.notkili.aoc.shared.parse.InputParser;

import java.util.regex.Matcher;

public class Day3 {
    public static void main(String[] args) {
        System.out.println("Solution 1:");

        new InputParser(2024, 3).getInput().ifPresent(strInput -> {
            Matcher matcher = new Pattern("mul\\(\\d+,\\d+\\)").compile().matcher(strInput.str());
            
            Long sum = 0L;
            
            while (matcher.find()) {
                var inp = new StringInput(matcher.group());
                sum += inp.firstNumber(0).asLong() * inp.firstNumber(1).asLong();
            }
            
            System.out.println(sum);
        });

        System.out.println("\nSolution 2:");

        new InputParser(2024, 3).getInput().ifPresent(strInput -> {
            Matcher matcher = new Pattern("(don't\\(\\))|(do\\(\\))|(mul\\(\\d+,\\d+\\))").compile().matcher(strInput.str());
            
            var sum = 0L;
            var enable = true;
            
            while (matcher.find()) {
                var inp = new StringInput(matcher.group());
                if (inp.startsWith("don't()")) {
                    enable = false;
                } else if (inp.startsWith("do()")) {
                    enable = true;
                } else {
                    if (!enable) {
                        continue;
                    }
                    
                    sum += inp.firstNumber(0).asLong() * inp.firstNumber(1).asLong();
                }
            }
            
            System.out.println(sum);
        });
    }
}
