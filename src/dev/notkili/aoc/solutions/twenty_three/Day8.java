package dev.notkili.aoc.solutions.twenty_three;

import dev.notkili.aoc.shared.input.LongInput;
import dev.notkili.aoc.shared.input.StringInput;
import dev.notkili.aoc.shared.misc.collections.list.List;
import dev.notkili.aoc.shared.misc.tuple.Tuple;
import dev.notkili.aoc.shared.parse.InputParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Day8 {
    public static void main(String[] args) {
        part1();

        part2();
    }

    private static void part1() {
        new InputParser(2023, 8).getInput().ifPresent(input -> {
            var parsed = input.asTuple("\n\n");
            var instr = parsed.getA();
            var mapping = parsed.getB();

            HashMap<StringInput, Tuple<StringInput, StringInput>> map = new HashMap<>();

            for (var m : mapping.splitAt("\n")) {
                var split = m.replace("(", "").replace(")", "").asTuple(" = ");
                map.put(split.getA(), split.getB().asTuple(", "));
            }

            var instrList = instr.splitAt("");

            LongInput count = new LongInput(0);
            StringInput current = new StringInput("AAA");

            while (true) {
                for (var in : instrList) {
                    if (in.asString().equals("R")) {
                        current = map.get(current).getB();
                    } else {
                        current = map.get(current).getA();
                    }

                    count = count.add(1);

                    if (current.asString().equals("ZZZ")) {
                        count.asSolution().print(); // submit(2023, 8, 1);
                        return;
                    }
                }
            }
        });
    }

    private static void part2() {
        new InputParser(2023, 8).getInput().ifPresent(input -> {
            var parsed = input.asTuple("\n\n");
            var instr = parsed.getA();
            var mapping = parsed.getB();

            HashMap<StringInput, Tuple<StringInput, StringInput>> map = new HashMap<>();

            for (var m : mapping.splitAt("\n")) {
                var split = m.replace("(", "").replace(")", "").asTuple(" = ");
                map.put(split.getA(), split.getB().asTuple(", "));
            }

            var instrList = instr.splitAt("");
            var starts = map.keySet().stream().filter(s -> s.endsWith("A")).toList();
            var goals = map.keySet().stream().filter(s -> s.endsWith("Z")).toList();

            HashMap<StringInput, HashMap<StringInput, List<Long>>> dist = new HashMap<>();

            for (var start : starts) {
                dist.put(start, new HashMap<>());

                for (var goal : goals) {
                    dist.get(start).put(goal, new List<>());

                    long count = 0;
                    StringInput current = start;
                    outer:
                    while (true) {
                        for (var in : instrList) {
                            count++;
                            if (in.asString().equals("R")) {
                                current = map.get(current).getB();
                            } else {
                                current = map.get(current).getA();
                            }

                            if (current.endsWith(goal.asString())) {
                                dist.get(start).get(current).add(count);

                                if (dist.get(start).get(current).size() >= 2) {
                                    break outer;
                                }
                            }

                            if (count > 100000)
                                break outer;
                        }
                    }
                }
            }

            HashMap<StringInput, Integer[]> intervals = new HashMap<>();

            for (var start : starts) {
                long factor = Long.MAX_VALUE;
                for (var goal : goals) {
                    var list = dist.get(start).get(goal);

                    if (list.size() == 0)
                        continue;

                    factor = Math.min(factor, list.get(1) - list.get(0));
                }
                intervals.put(start, factors(factor));
            }

            new LongInput(kgv(intervals.values().toArray(new Integer[0][]))).asSolution().print(); // submit(2023, 8, 2);
        });
    }

    private static Integer[] factors(long num) {
        ArrayList<Integer> factors = new ArrayList<>();

        var index = 0;

        int i = 2;
        while (num != 1) {
            if (!isPrime(i)) {
                i++;
                continue;
            }

            factors.add(0);
            while (num % i == 0) {
                factors.set(index, factors.get(index) + 1);
                num /= i;
            }

            i++;
            index++;
        }

        return factors.toArray(new Integer[0]);
    }

    private static HashMap<Integer, Integer> getNPrimes(int n) {
        int i = 2;
        int count = 0;
        HashMap<Integer, Integer> primes = new HashMap<>();

        while (true) {
            if (isPrime(i)) {
                primes.put(count, i);
                count++;
                if (count == n)
                    return primes;
            }

            i++;
        }
    }

    private static boolean isPrime(long num) {
        for (long i = 2; i < num; i++) {
            if (num % i == 0)
                return false;
        }

        return true;
    }

    private static long kgv(Integer[]... factors) {
        int maxLen = Arrays.stream(factors).map(f -> f.length).max(Integer::compareTo).orElse(0);
        long lcm = 1;
        var primes = getNPrimes(maxLen);

        for (int i = 0; i < maxLen; i++) {
            int max = 0;

            for (var factor : factors) {
                if (i >= factor.length)
                    continue;

                max = Math.max(max, factor[i]);
            }

            lcm *= Math.pow(primes.get(i), max);
        }

        return lcm;
    }
}
