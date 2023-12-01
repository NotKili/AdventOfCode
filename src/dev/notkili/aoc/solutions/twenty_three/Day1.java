package dev.notkili.aoc.solutions.twenty_three;

import dev.notkili.aoc.shared.input.StringInput;
import dev.notkili.aoc.shared.parse.InputParser;

public class Day1 {
    public static void main(String[] args) {
        System.out.println("Solution 1:");
        var result = new InputParser(2023, 1).getInput()
                .map(input ->
                        input.splitAt("\n")
                        .mapToInt(str ->
                                str.findFirstNthDigit(0)
                                .multiply(10)
                                .add(str.findLastNthDigit(0)))
                        .sum()
                        .asStringInput());

        // result.ifPresent(res -> res.submit(2023, 1, 1));
        result.ifPresent(StringInput::print);

        System.out.println("\nSolution 2:");
        result = new InputParser(2023, 1).getInput()
                .map(input ->
                        input.splitAt("\n")
                                .mapToInt(str ->
                                        str.findFirstNthDigit(0, true)
                                        .multiply(10)
                                        .add(str.findLastNthDigit(0, true)))
                                .sum()
                                .asStringInput());

        // result.ifPresent(res -> res.submit(2023, 1, 2));
        result.ifPresent(StringInput::print);
    }
}
