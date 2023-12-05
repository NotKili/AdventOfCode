package dev.notkili.aoc.solutions.twenty_three;

import dev.notkili.aoc.shared.input.IntInput;
import dev.notkili.aoc.shared.input.LongInput;
import dev.notkili.aoc.shared.misc.collections.List;
import dev.notkili.aoc.shared.misc.collections.Set;
import dev.notkili.aoc.shared.misc.tuple.Triple;
import dev.notkili.aoc.shared.misc.tuple.Tuple;
import dev.notkili.aoc.shared.parse.InputParser;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class Day5 {
    public static void main(String[] args) {
        solve1();

        solve2();
    }

    private static void solve1() {
        new InputParser(2023, 5).getInput().ifPresent(input -> {
            var diff = input.splitAt("\n\n");

            // Seed to anything
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
                return new Range(lower, lower + l.get(1).asLong().asLong());
            });
            var soilInput = new List<>(diff.get(1).replace("seed-to-soil map:\n", "").splitAt("\n").mapTo(str -> str.asTriple(" ")).mapTo(triple -> new Triple<>(new LongInput(triple.getA().asString()), new LongInput(triple.getB().asString()), new LongInput(triple.getC().asString()))).asList());
            var fertilizerInput = new List<>(diff.get(2).replace("soil-to-fertilizer map:\n", "").splitAt("\n").mapTo(str -> str.asTriple(" ")).mapTo(triple -> new Triple<>(new LongInput(triple.getA().asString()), new LongInput(triple.getB().asString()), new LongInput(triple.getC().asString()))).asList());
            var waterInput = new List<>(diff.get(3).replace("fertilizer-to-water map:\n", "").splitAt("\n").mapTo(str -> str.asTriple(" ")).mapTo(triple -> new Triple<>(new LongInput(triple.getA().asString()), new LongInput(triple.getB().asString()), new LongInput(triple.getC().asString()))).asList());
            var lightInput = new List<>(diff.get(4).replace("water-to-light map:\n", "").splitAt("\n").mapTo(str -> str.asTriple(" ")).mapTo(triple -> new Triple<>(new LongInput(triple.getA().asString()), new LongInput(triple.getB().asString()), new LongInput(triple.getC().asString()))).asList());
            var temperatureInput = new List<>(diff.get(5).replace("light-to-temperature map:\n", "").splitAt("\n").mapTo(str -> str.asTriple(" ")).mapTo(triple -> new Triple<>(new LongInput(triple.getA().asString()), new LongInput(triple.getB().asString()), new LongInput(triple.getC().asString()))).asList());
            var humidityInput = new List<>(diff.get(6).replace("temperature-to-humidity map:\n", "").splitAt("\n").mapTo(str -> str.asTriple(" ")).mapTo(triple -> new Triple<>(new LongInput(triple.getA().asString()), new LongInput(triple.getB().asString()), new LongInput(triple.getC().asString()))).asList());
            var locationInput = new List<>(diff.get(7).replace("humidity-to-location map:\n", "").splitAt("\n").mapTo(str -> str.asTriple(" ")).mapTo(triple -> new Triple<>(new LongInput(triple.getA().asString()), new LongInput(triple.getB().asString()), new LongInput(triple.getC().asString()))).asList());

            Ranges seeds = new Ranges();
            seedInput.forEach(seeds::add);

            List<Tuple<Range, Range>> soils = new List<>();
            soilInput.forEach(triple -> soils.add(new Tuple<>(new Range(triple.getB().asLong(), triple.getB().add(triple.getC()).asLong()), new Range(triple.getA().asLong(), triple.getA().add(triple.getC()).asLong()))));

            List<Tuple<Range, Range>> fertilizer = new List<>();
            fertilizerInput.forEach(triple -> fertilizer.add(new Tuple<>(new Range(triple.getB().asLong(), triple.getB().add(triple.getC()).asLong()), new Range(triple.getA().asLong(), triple.getA().add(triple.getC()).asLong()))));

            List<Tuple<Range, Range>> water = new List<>();
            waterInput.forEach(triple -> water.add(new Tuple<>(new Range(triple.getB().asLong(), triple.getB().add(triple.getC()).asLong()), new Range(triple.getA().asLong(), triple.getA().add(triple.getC()).asLong()))));

            List<Tuple<Range, Range>> light = new List<>();
            lightInput.forEach(triple -> light.add(new Tuple<>(new Range(triple.getB().asLong(), triple.getB().add(triple.getC()).asLong()), new Range(triple.getA().asLong(), triple.getA().add(triple.getC()).asLong()))));

            List<Tuple<Range, Range>> temperature = new List<>();
            temperatureInput.forEach(triple -> temperature.add(new Tuple<>(new Range(triple.getB().asLong(), triple.getB().add(triple.getC()).asLong()), new Range(triple.getA().asLong(), triple.getA().add(triple.getC()).asLong()))));

            List<Tuple<Range, Range>> humidity = new List<>();
            humidityInput.forEach(triple -> humidity.add(new Tuple<>(new Range(triple.getB().asLong(), triple.getB().add(triple.getC()).asLong()), new Range(triple.getA().asLong(), triple.getA().add(triple.getC()).asLong()))));

            List<Tuple<Range, Range>> location = new List<>();
            locationInput.forEach(triple -> location.add(new Tuple<>(new Range(triple.getB().asLong(), triple.getB().add(triple.getC()).asLong()), new Range(triple.getA().asLong(), triple.getA().add(triple.getC()).asLong()))));

            AtomicReference<Long> min = new AtomicReference<>(Long.MAX_VALUE);

            for (var seed : seeds.ranges) {
                getInRange(seed, soils).forEach(soil -> {
                    getInRange(soil, fertilizer).forEach(fertilizer1 -> {
                        getInRange(fertilizer1, water).forEach(water1 -> {
                            getInRange(water1, light).forEach(light1 -> {
                                getInRange(light1, temperature).forEach(temperature1 -> {
                                    getInRange(temperature1, humidity).forEach(humidity1 -> {
                                        getInRange(humidity1, location).forEach(location1 -> {
                                            if (location1.lower < min.get()) {
                                                min.set(location1.lower);
                                            }
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
            }

            new LongInput(min.get()).asSolution().print();//.submit(2023, 5, 2);
        });
    }

    private static List<Range> getInRange(Range original, List<Tuple<Range, Range>> mapping) {
        var list = new List<Range>();

        for (var entry : mapping) {
            list.addAll(mapRanges(original, entry));
        }

        if (list.isEmpty()) {
            list.add(original);
        }

        return list;
    }

    private static List<Range> mapRanges(Range original, Tuple<Range, Range> mapping) {
        var list = new List<Range>();
        var src = mapping.getA();
        var dest = mapping.getB();
        var diff = dest.lower - src.lower;

        if (original.lower > src.upper || original.upper < src.lower) {
            return list;
        }

        var lowerDiff = original.lower - src.lower;
        var upperDiff = original.upper - src.upper;

        if (lowerDiff < -1 && upperDiff > 1)
            return list;

        if (lowerDiff < -1) {
            original = new Range(src.lower, original.upper);
        } else if (upperDiff > 1) {
            original = new Range(original.lower, src.upper);
        }

        return list.add(new Range(original.lower + diff, original.upper + diff));
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

    private static class Ranges {
        private final List<Range> ranges;

        public Ranges() {
            this.ranges = new List<>();
        }

        public Ranges add(long lower, long upper) {
            return add(new Range(lower, upper));
        }

        public Ranges add(Range range) {
            ranges.add(range);
            ranges.sort((a, b) -> (int) (a.lower - b.lower));
            return this;
        }

        public long max() {
            return ranges.get(ranges.size() - 1).upper;
        }

        public void print() {
            ranges.forEach(range -> System.out.println(range.lower + " " + range.upper));
        }
    }

    public static class Range {
        private final long lower;
        private final long upper;

        public Range(long lower, long upper) {
            this.lower = lower;
            this.upper = upper;
        }

        public boolean contains(long val) {
            return val >= lower && val < upper;
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
