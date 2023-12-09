package dev.notkili.aoc.solutions.twenty_three;

import dev.notkili.aoc.shared.input.LongInput;
import dev.notkili.aoc.shared.misc.collections.list.List;
import dev.notkili.aoc.shared.misc.collections.Set;
import dev.notkili.aoc.shared.misc.tuple.Triple;
import dev.notkili.aoc.shared.misc.tuple.Tuple;
import dev.notkili.aoc.shared.parse.InputParser;

import java.util.*;

public class Day5 {
    public static void main(String[] args) {
        solve1();

        solve2();
    }

    private static void solve1() {
        new InputParser(2023, 5).getInput().ifPresent(input -> {
            var diff = input.splitAt("\n\n");

            HashMap<LongInput, Set<LongInput>> seedToAnything = new HashMap<>();

            List<LongInput> seeds = new List<>();
            diff.get(0).asTuple(": ").getB().splitAt(" ").forEach(s -> {
                var seed = new LongInput(s.asString());
                seeds.add(seed);
                seedToAnything.put(seed, new Set<LongInput>().add(seed));
            });

            var soils = new List<>(diff.get(1).replace("seed-to-soil map:\n", "").splitAt("\n").mapTo(str -> str.asTriple(" ")).mapTo(triple -> new Triple<>(new LongInput(triple.getA().asString()), new LongInput(triple.getB().asString()), new LongInput(triple.getC().asString()))).asList());

            for (var seed : seeds) {
                seedToAnything.put(seed, transmute(seed, seedToAnything, soils));
            }

            var fertilizers = new List<>(diff.get(2).replace("soil-to-fertilizer map:\n", "").splitAt("\n").mapTo(str -> str.asTriple(" ")).mapTo(triple -> new Triple<>(new LongInput(triple.getA().asString()), new LongInput(triple.getB().asString()), new LongInput(triple.getC().asString()))).asList());

            for (var seed : seeds) {
                seedToAnything.put(seed, transmute(seed, seedToAnything, fertilizers));
            }

            var waters = new List<>(diff.get(3).replace("fertilizer-to-water map:\n", "").splitAt("\n").mapTo(str -> str.asTriple(" ")).mapTo(triple -> new Triple<>(new LongInput(triple.getA().asString()), new LongInput(triple.getB().asString()), new LongInput(triple.getC().asString()))).asList());

            for (var seed : seeds) {
                seedToAnything.put(seed, transmute(seed, seedToAnything, waters));
            }

            var lights = new List<>(diff.get(4).replace("water-to-light map:\n", "").splitAt("\n").mapTo(str -> str.asTriple(" ")).mapTo(triple -> new Triple<>(new LongInput(triple.getA().asString()), new LongInput(triple.getB().asString()), new LongInput(triple.getC().asString()))).asList());

            for (var seed : seeds) {
                seedToAnything.put(seed, transmute(seed, seedToAnything, lights));
            }

            var temperature = new List<>(diff.get(5).replace("light-to-temperature map:\n", "").splitAt("\n").mapTo(str -> str.asTriple(" ")).mapTo(triple -> new Triple<>(new LongInput(triple.getA().asString()), new LongInput(triple.getB().asString()), new LongInput(triple.getC().asString()))).asList());

            for (var seed : seeds) {
                seedToAnything.put(seed, transmute(seed, seedToAnything, temperature));
            }

            var humidity = new List<>(diff.get(6).replace("temperature-to-humidity map:\n", "").splitAt("\n").mapTo(str -> str.asTriple(" ")).mapTo(triple -> new Triple<>(new LongInput(triple.getA().asString()), new LongInput(triple.getB().asString()), new LongInput(triple.getC().asString()))).asList());

            for (var seed : seeds) {
                seedToAnything.put(seed, transmute(seed, seedToAnything, humidity));
            }

            var location = new List<>(diff.get(7).replace("humidity-to-location map:\n", "").splitAt("\n").mapTo(str -> str.asTriple(" ")).mapTo(triple -> new Triple<>(new LongInput(triple.getA().asString()), new LongInput(triple.getB().asString()), new LongInput(triple.getC().asString()))).asList());

            for (var seed : seeds) {
                seedToAnything.put(seed, transmute(seed, seedToAnything, location));
            }

            LongInput min = new LongInput(Long.MAX_VALUE);

            for (var seed : seeds) {
                var set = seedToAnything.get(seed);

                for (var val : set) {
                    min = min.min(val);
                }
            }

            min.asSolution().print();//.submit(2023, 5, 1);
        });
    }

    private static void solve2() {
        new InputParser(2023, 5).getInput().ifPresent(input -> {
            var diff = input.splitAt("\n\n");

            var seedInput = diff.get(0).asTuple(": ").getB().splitAt(" ").groupN(2).mapTo(l -> {
                var lower = l.get(0).asLong().asLong();
                return new Range(lower, lower + l.get(1).asLong().asLong() - 1);
            });
            var soilInput = new List<>(diff.get(1).replace("seed-to-soil map:\n", "").splitAt("\n").mapTo(str -> str.asTriple(" ")).mapTo(triple -> new Triple<>(new LongInput(triple.getA().asString()), new LongInput(triple.getB().asString()), new LongInput(triple.getC().asString()))).asList());
            var fertilizerInput = new List<>(diff.get(2).replace("soil-to-fertilizer map:\n", "").splitAt("\n").mapTo(str -> str.asTriple(" ")).mapTo(triple -> new Triple<>(new LongInput(triple.getA().asString()), new LongInput(triple.getB().asString()), new LongInput(triple.getC().asString()))).asList());
            var waterInput = new List<>(diff.get(3).replace("fertilizer-to-water map:\n", "").splitAt("\n").mapTo(str -> str.asTriple(" ")).mapTo(triple -> new Triple<>(new LongInput(triple.getA().asString()), new LongInput(triple.getB().asString()), new LongInput(triple.getC().asString()))).asList());
            var lightInput = new List<>(diff.get(4).replace("water-to-light map:\n", "").splitAt("\n").mapTo(str -> str.asTriple(" ")).mapTo(triple -> new Triple<>(new LongInput(triple.getA().asString()), new LongInput(triple.getB().asString()), new LongInput(triple.getC().asString()))).asList());
            var temperatureInput = new List<>(diff.get(5).replace("light-to-temperature map:\n", "").splitAt("\n").mapTo(str -> str.asTriple(" ")).mapTo(triple -> new Triple<>(new LongInput(triple.getA().asString()), new LongInput(triple.getB().asString()), new LongInput(triple.getC().asString()))).asList());
            var humidityInput = new List<>(diff.get(6).replace("temperature-to-humidity map:\n", "").splitAt("\n").mapTo(str -> str.asTriple(" ")).mapTo(triple -> new Triple<>(new LongInput(triple.getA().asString()), new LongInput(triple.getB().asString()), new LongInput(triple.getC().asString()))).asList());
            var locationInput = new List<>(diff.get(7).replace("humidity-to-location map:\n", "").splitAt("\n").mapTo(str -> str.asTriple(" ")).mapTo(triple -> new Triple<>(new LongInput(triple.getA().asString()), new LongInput(triple.getB().asString()), new LongInput(triple.getC().asString()))).asList());

            List<Tuple<Range, Range>> soils = new List<>();
            soilInput.forEach(triple -> soils.add(new Tuple<>(new Range(triple.getB().asLong(), triple.getB().add(triple.getC()).subtract(1).asLong()), new Range(triple.getA().asLong(), triple.getA().add(triple.getC()).subtract(1).asLong()))));

            List<Tuple<Range, Range>> fertilizer = new List<>();
            fertilizerInput.forEach(triple -> fertilizer.add(new Tuple<>(new Range(triple.getB().asLong(), triple.getB().add(triple.getC()).subtract(1).asLong()), new Range(triple.getA().asLong(), triple.getA().add(triple.getC()).subtract(1).asLong()))));

            List<Tuple<Range, Range>> water = new List<>();
            waterInput.forEach(triple -> water.add(new Tuple<>(new Range(triple.getB().asLong(), triple.getB().add(triple.getC()).subtract(1).asLong()), new Range(triple.getA().asLong(), triple.getA().add(triple.getC()).subtract(1).asLong()))));

            List<Tuple<Range, Range>> light = new List<>();
            lightInput.forEach(triple -> light.add(new Tuple<>(new Range(triple.getB().asLong(), triple.getB().add(triple.getC()).subtract(1).asLong()), new Range(triple.getA().asLong(), triple.getA().add(triple.getC()).subtract(1).asLong()))));

            List<Tuple<Range, Range>> temperature = new List<>();
            temperatureInput.forEach(triple -> temperature.add(new Tuple<>(new Range(triple.getB().asLong(), triple.getB().add(triple.getC()).subtract(1).asLong()), new Range(triple.getA().asLong(), triple.getA().add(triple.getC()).subtract(1).asLong()))));

            List<Tuple<Range, Range>> humidity = new List<>();
            humidityInput.forEach(triple -> humidity.add(new Tuple<>(new Range(triple.getB().asLong(), triple.getB().add(triple.getC()).subtract(1).asLong()), new Range(triple.getA().asLong(), triple.getA().add(triple.getC()).subtract(1).asLong()))));

            List<Tuple<Range, Range>> location = new List<>();
            locationInput.forEach(triple -> location.add(new Tuple<>(new Range(triple.getB().asLong(), triple.getB().add(triple.getC()).subtract(1).asLong()), new Range(triple.getA().asLong(), triple.getA().add(triple.getC()).subtract(1).asLong()))));

            List<List<Tuple<Range, Range>>> mappers = new List<>();

            mappers
                    .add(soils)
                    .add(fertilizer)
                    .add(water)
                    .add(light)
                    .add(temperature)
                    .add(humidity)
                    .add(location)
            ;

            List<Range> ranges = new List<>();
            ranges.addAll(seedInput.asList());

            for (var mapper : mappers) {
                mapper.sort(Comparator.comparingLong(a -> a.getA().lower));
                ranges = applyMapping(ranges, mapper);
            }

            ranges
                    .map(range -> range.lower)
                    .min(Comparator.comparing(a -> a))
                    .ifPresentOrElse(min -> {
                        new LongInput(min).asSolution().print();//.submit(2023, 5, 2);
            }, () -> System.err.println("No solution found"));
        });
    }

    public static List<Range> applyMapping(List<Range> ranges, List<Tuple<Range, Range>> mapper) {
        List<Range> newRanges = new List<>();

        for (var range : ranges) {
            List<Range> tmpList = new List<>();

            var lowerBound = range.lower;
            var upperBound = range.upper;

            while (lowerBound < upperBound) {
                // b > d : (a, b) , (c, d)
                if (lowerBound > mapper.get(-1).getA().upper) {
                    tmpList.add(new Range(lowerBound, upperBound));
                    break;
                }

                for (var map : mapper) {
                    var destRange = map.getA();
                    var delta = map.getB().lower - map.getA().lower;

                    if (range.lower <= lowerBound && lowerBound <= destRange.upper) {
                        var newCurrent = Math.min(upperBound, destRange.upper + 1);
                        tmpList.add(new Range(lowerBound + delta, newCurrent + delta));
                        lowerBound = newCurrent;
                        break;
                    } else if (lowerBound < destRange.lower) {
                        var newCurrent = Math.min(upperBound, destRange.lower);
                        tmpList.add(new Range(lowerBound, newCurrent));
                        lowerBound = newCurrent + 1;
                        break;
                    }
                }
            }

            newRanges.addAll(tmpList);
        }

        return newRanges.distinct();
    }

    private static Optional<LongInput> getNum(LongInput source, Triple<LongInput, LongInput, LongInput> srcDestRange) {
        var dest = srcDestRange.getA();
        var src = srcDestRange.getB();
        var range = srcDestRange.getC();

        if (source.asLong() < src.asLong() || source.asLong() > src.add(range).asLong()) {
            return Optional.empty();
        }

        return Optional.of(dest.add(source.subtract(src).abs()));
    }

    private static Set<LongInput> transmute(LongInput key, Map<LongInput, Set<LongInput>> map, List<Triple<LongInput, LongInput, LongInput>> srcDestRangeList) {
        Set<LongInput> set = new Set<>();
        Set<LongInput> old = map.get(key).copy();

        map.get(key).forEach(k -> srcDestRangeList.forEach(srcDestRange -> {
            var numOpt = getNum(k, srcDestRange);

            numOpt.ifPresent(set::add);
        }));

        if (set.isEmpty()) {
            return old;
        }

        return set;
    }

    public static class Range {
        private final long lower;
        private final long upper;

        public Range(long lower, long upper) {
            this.lower = lower;
            this.upper = upper;
        }

        @Override
        public String toString() {
            return "Range{" +
                    "lower=" + lower +
                    ", upper=" + upper +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Range range = (Range) o;
            return lower == range.lower && upper == range.upper;
        }

        @Override
        public int hashCode() {
            return Objects.hash(lower, upper);
        }
    }
}
