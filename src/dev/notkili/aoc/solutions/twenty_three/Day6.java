package dev.notkili.aoc.solutions.twenty_three;

import dev.notkili.aoc.shared.input.LongInput;
import dev.notkili.aoc.shared.misc.collections.list.List;
import dev.notkili.aoc.shared.misc.tuple.Tuple;
import dev.notkili.aoc.shared.parse.InputParser;

public class Day6 {
    public static void main(String[] args) {
        part1();

        part2();
    }

    private static void part1() {
        new InputParser(2023, 6).getInput().ifPresent(input -> {
            var lines = input.splitAt("\n");
            var times = lines.get(0);
            var distances = lines.get(1);

            var list = new List<Tuple<LongInput, LongInput>>();
            int i = 0;
            while (true) {
                try {
                    list.add(new Tuple<>(times.findFirstNthNumber(i).asLongInput(), distances.findFirstNthNumber(i).asLongInput()));
                    i++;
                } catch (Exception ignored) {
                    break;
                }
            }

            LongInput result = new LongInput(1);
            for (var res : list.map(Day6::evaluateGame)) {
                result = result.multiply(res);
            }

            result.solution().print();//.submit(2023, 6, 1);
        });
    }

    private static LongInput evaluateGame(Tuple<LongInput, LongInput> gameInfo) {
        LongInput toBeat = new LongInput(0);

        var maxTime = gameInfo.getA().asLong();

        for (long hold = 1; hold < maxTime; hold++) {
            long timeToTravel = maxTime - hold;
            long distanceToTravel = hold * timeToTravel;

            if (distanceToTravel > gameInfo.getB().asLong()) {
                toBeat = toBeat.add(new LongInput(1));
            }
        }

        return toBeat;
    }

    private static void part2() {
        new InputParser(2023, 6).getInput().ifPresent(input -> {
            var lines = input.splitAt("\n");
            var times = lines.get(0);
            var distances = lines.get(1);

            Tuple<String, String> ab = new Tuple<>("", "");
            var list = new List<Tuple<LongInput, LongInput>>();
            int i = 0;
            while (true) {
                try {
                    ab = new Tuple<>(ab.getA() + times.findFirstNthNumber(i).asStringInput().asString(), ab.getB() + distances.findFirstNthNumber(i).asLongInput().asStringInput().asString());
                    i++;
                } catch (Exception ignored) {
                    break;
                }
            }

            list.add(new Tuple<>(new LongInput(ab.getA()), new LongInput(ab.getB())));

            LongInput result = new LongInput(1);
            for (var res : list.map(Day6::evaluateGame)) {
                result = result.multiply(res);
            }

            result.solution().print();//.submit(2023, 6, 2);
        });
    }
}
