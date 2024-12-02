package dev.notkili.aoc.solutions.twenty_three;

import dev.notkili.aoc.shared.input.IntInput;
import dev.notkili.aoc.shared.input.ListInput;
import dev.notkili.aoc.shared.input.StringInput;
import dev.notkili.aoc.shared.parse.InputParser;

public class Day9 {
    public static void main(String[] args) {
        part1();

        part2();
    }

    private static void part1() {
        new InputParser(2023, 9).getInput().ifPresent(input -> {
            var sequences = input.splitAt("\n").splitAt(" ").mapTo(ListInput.StringListInput::toInts).asList();

            IntInput result = new IntInput(0);

            for (var seq: sequences) {
                var tmp = extrapolate(seq);
                result = result.add(tmp);
            }

            result.solution().print();//.submit(2023, 9, 1);
        });
    }

    private static void part2() {
        new InputParser(2023, 9).getInput().ifPresent(input -> {
            var sequences = input.splitAt("\n").splitAt(" ").mapTo(ListInput::reverse).mapTo(mp -> mp.mapToInt(StringInput::asInt)).asList();

            IntInput result = new IntInput(0);

            for (var seq: sequences) {
                var tmp = extrapolate(seq);
                result = result.add(tmp);
            }

            result.solution().print();//.submit(2023, 9, 2);
        });
    }

    // 995526126
    public static IntInput extrapolate(ListInput.IntListInput nums) {
        if (nums.distinctCount().asInt() == 1) {
            return nums.get(0);
        }

        ListInput.IntListInput differences = new ListInput.IntListInput();

        for (int i = 0; i < nums.size() - 1; i++) {
            var numA = nums.get(i);
            var numB = nums.get(i + 1);
            var diff = numB.subtract(numA);
            differences.add(diff);
        }

        return extrapolate(differences).add(nums.get(nums.size() - 1));
    }
}
