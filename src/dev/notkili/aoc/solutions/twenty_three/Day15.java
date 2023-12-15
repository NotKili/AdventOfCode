package dev.notkili.aoc.solutions.twenty_three;

import dev.notkili.aoc.shared.input.CharInput;
import dev.notkili.aoc.shared.input.IntInput;
import dev.notkili.aoc.shared.input.LongInput;
import dev.notkili.aoc.shared.input.StringInput;
import dev.notkili.aoc.shared.misc.TimeTracker;
import dev.notkili.aoc.shared.misc.collections.list.List;
import dev.notkili.aoc.shared.misc.tuple.Tuple;
import dev.notkili.aoc.shared.parse.InputParser;

import java.util.HashMap;

public class Day15 {
    public static void main(String[] args) {
        TimeTracker.trackTime(Day15::part1);
        TimeTracker.trackTime(Day15::part2);
    }

    private static void part1() {
         new InputParser(2023, 15).getInput().ifPresent(input -> {
             input
                     .replace("\n", "")
                     .splitAt(",")
                     .mapToInt(str -> new IntInput(hashString(str.asString())))
                     .reduce(IntInput::add)
                     .get()
                     .asSolution().print();//submit(2023, 15, 1);
         });
    }

    private static void part2() {
        new InputParser(2023, 15).getInput().ifPresent(input -> {
            var map = new HashMap<Integer, List<Tuple<String, String>>>();

            input.replace("\n", "").splitAt(",").mapTo(StringInput::asString).forEach(str -> {
                if (str.contains("=")) {
                    var splits = str.split("=");
                    var label = splits[0];
                    var focalLength = splits[1];

                    var hash = hashString(label);

                    if (map.containsKey(hash)) {
                        var l = map.get(hash);

                        l.find(t -> t.getA().equals(label)).ifPresentOrElse(t -> {
                            l.replaceFirst(t, new Tuple<>(label, focalLength));
                        }, () -> {
                            l.add(new Tuple<>(label, focalLength));
                        });
                    } else {
                        map.put(hash, new List<>(new Tuple<>(label, focalLength)));
                    }
                } else {
                    var label = str.substring(0, str.length() - 1);

                    var hash = hashString(label);

                    if (!map.containsKey(hash)) {
                        return;
                    }

                    var l = map.get(hash);
                    l.removeIf(t -> t.getA().equals(label));
                }
            });

            LongInput sum = new LongInput(0);

            for (int i = 1; i <= 256; i++) {
                if (map.containsKey(i - 1)) {
                    var l = map.get(i - 1);

                    long index = 1;
                    for (var lense : l) {
                        sum = sum.add(i * index * new IntInput(lense.getB()).asInt());
                        index++;
                    }
                }
            }

            sum.asSolution().print(); //.submit(2023, 15, 2);
        });
    }

    public static int hashString(String s) {
        int hash = 0;

        for (char c : s.toCharArray()) {
            hash += c;
            hash *= 17;
            hash %= 256;
        }

        return hash;
    }
}
