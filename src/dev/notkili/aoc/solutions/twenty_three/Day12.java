package dev.notkili.aoc.solutions.twenty_three;

import dev.notkili.aoc.shared.input.IntInput;
import dev.notkili.aoc.shared.input.LongInput;
import dev.notkili.aoc.shared.misc.TimeTracker;
import dev.notkili.aoc.shared.misc.collections.list.List;
import dev.notkili.aoc.shared.misc.tuple.Tuple;
import dev.notkili.aoc.shared.parse.InputParser;

import java.util.HashMap;

public class Day12 {
    public static void main(String[] args) {
        TimeTracker.trackTime(Day12::part1);
        TimeTracker.trackTime(Day12::part2);
    }

    private static void part1() {
        new InputParser(2023, 12).getInput().ifPresent(input -> {
            input
                    .split("\n")
                    .mapTo(str -> str.tuple(" ")
                            .mapB(st -> st.split(",")
                                    .toInts().mapTo(IntInput::asInt)
                                    .asList()
                            )
                    ).asList()
                    .stream()
                    .map(t ->
                            calculateArrangements(
                                    t.getA().concat(".").chars(),
                                    new List<>(t.getB())
                            )
                    )
                    .map(LongInput::new)
                    .reduce(LongInput::add)
                    .get()
                    .solution()
                    .print(); //.submit(2023, 12, 1);
        });
    }

    private static void part2() {
        new InputParser(2023, 12).getInput().ifPresent(input -> {
            input
                    .split("\n")
                    .mapTo(str -> str.tuple(" ")
                            .mapB(st -> st.split(",")
                                    .toInts()
                                    .mapTo(IntInput::asInt)
                                    .asList()
                            )
                    ).asList()
                    .stream()
                    .map(t -> {
                        var newTuple = t.map(s -> s.join(5, "?"), l -> new List<>(l).multiply(5));
                        return calculateArrangements(newTuple.getA().concat(".").chars(), newTuple.getB());
                    })
                    .map(LongInput::new)
                    .reduce(LongInput::add)
                    .get()
                    .solution()
                    .print();//.submit(2023, 12, 2);
        });
    }

    private static long calculateArrangements(char[] wells, List<Integer> groupSizes) {
        return searchArrangements(new HashMap<>(), wells, groupSizes, 0, 0);
    }

    private static long searchArrangements(HashMap<Tuple<Integer, Integer>, Long> cache, char[] wells, List<Integer> groupSizes, int wellIndex, int groupIndex) {
        var count = 0L;
        var cKey = new Tuple<>(wellIndex, groupIndex);

        if (cache.containsKey(cKey)) {
            return cache.get(cKey);
        }

        if (wellIndex >= wells.length) {
            var val = groupIndex == groupSizes.size() ? 1L : 0L;
            cache.put(cKey, val);
            return val;
        }

        if (wells[wellIndex] != '#') {
            count += searchArrangements(cache, wells, groupSizes, wellIndex + 1, groupIndex);
        }

        if (groupIndex >= groupSizes.size()) {
            cache.put(cKey, count);
            return count;
        }

        var lastWellGroupIndex = wellIndex + groupSizes.get(groupIndex);

        if (lastWellGroupIndex >= wells.length) {
            cache.put(cKey, count);
            return count;
        }

        if (
                !contains(wells, wellIndex, lastWellGroupIndex, '.')
                        &&
                wells[lastWellGroupIndex] != '#'
        ) {
            count += searchArrangements(cache, wells, groupSizes, lastWellGroupIndex + 1, groupIndex + 1);
        }

        cache.put(cKey, count);
        return count;
    }

    private static boolean contains(char[] arr, int from, int to, char c) {
        for (int i = from; i < to; i++) {
            if (arr[i] == c) {
                return true;
            }
        }
        return false;
    }
}
