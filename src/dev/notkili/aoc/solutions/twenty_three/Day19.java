package dev.notkili.aoc.solutions.twenty_three;

import dev.notkili.aoc.shared.input.LongInput;
import dev.notkili.aoc.shared.misc.TimeTracker;
import dev.notkili.aoc.shared.misc.collections.list.List;
import dev.notkili.aoc.shared.misc.tuple.Triple;
import dev.notkili.aoc.shared.parse.InputParser;

import java.util.HashMap;

public class Day19 {
    public static void main(String[] args) {
        TimeTracker.trackTime(Day19::part1);
        TimeTracker.trackTime(Day19::part2);
    }

    private static void part1() {
        new InputParser(2023, 19).getInput().ifPresent(input -> {
            var split = input.splitAt("\n\n");

            var rules = new List<Rule>();
            split.get(0).splitAt("\n").mapTo(str -> new Rule(str.asString())).forEach(rules::add);

            HashMap<String, Rule> ruleMap = new HashMap<>();

            for (var rule : rules) {
                ruleMap.put(rule.id, rule);
            }

            var parts = new List<>(
                    split.get(1)
                    .splitAt("\n")
                    .mapTo(str -> str.substring(1, str.length().asInt() - 1))
                    .mapTo(str -> str.replaceAll("[xmas]=", ""))
                    .mapTo(str -> str.asQuadruple(","))
                    .mapTo(str -> new Part(str.getA().asLong().asLong(), str.getB().asLong().asLong(), str.getC().asLong().asLong(), str.getD().asLong().asLong()))
                    .asList()
            );

            HashMap<Integer, Part> indexed = new HashMap<>();

            for (int i = 0; i < parts.size(); i++) {
                indexed.put(i, parts.get(i));
            }

            while (parts.containsAny(p -> p.state == State.IN_PROGRESS)) {
                for (var part : parts) {
                    if (part.state != State.IN_PROGRESS) {
                        continue;
                    }

                    ruleMap.get(part.workflow).mapToDest(part);
                }
            }

            new LongInput(parts.findAll(p -> p.state == State.ACCEPTED).map(Part::sum).reduce(Long::sum).get()).asSolution().print(); // submit(2023, 19, 1);
        });
    }

    private static void part2() {
        new InputParser(2023, 19).getInput().ifPresent(input -> {
            var split = input.splitAt("\n\n");

            var rules = new List<Rule>();
            split.get(0).splitAt("\n").mapTo(str -> new Rule(str.asString())).forEach(rules::add);

            HashMap<String, Rule> ruleMap = new HashMap<>();

            for (var rule : rules) {
                ruleMap.put(rule.id, rule);
            }

            var startingPart = new RangedPart(new Range(1, 4001), new Range(1, 4001), new Range(1, 4001), new Range(1, 4001));

            calculateDistinct(startingPart, ruleMap).asSolution().print(); // submit(2023, 19, 2);
        });
    }

    private static LongInput calculateDistinct(RangedPart from, HashMap<String, Rule> rules) {
        if (from.state == State.REJECTED)
            return new LongInput(0);

        if (from.state == State.ACCEPTED) {
            return new LongInput(from.product());
        }

        var rule = rules.get(from.workflow);
        var total = new LongInput(0);
        var newPart = from.copy();

        for (var compariser : rule.comparisers) {
            var symbol = compariser.getC().symbol;
            var value = compariser.getC().against;

            var range = newPart.getValue(compariser.getB());

            var copyOfNew = newPart.copy();
            copyOfNew.setWorkflow(compariser.getA());
            var newRange = copyOfNew.getValue(compariser.getB());

            if (symbol.equals("<")) {
                // [a, b) | a < c -> [a, c) + [c, b)
                if (range.min < value) {
                    newRange.max = Math.min(value, newRange.max);
                    total = total.add(calculateDistinct(copyOfNew, rules));
                }

                if (range.max >= value) {
                    range.min = value;
                }
            } else if (symbol.equals(">")) {
                // [a, b) | b > c -> [c + 1, b) + [a, c + 1)
                if (range.max > value) {
                    newRange.min = value + 1;
                    total = total.add(calculateDistinct(copyOfNew, rules));
                }

                if (range.min <= value) {
                    range.max = value + 1;
                }
            } else {
                throw new IllegalArgumentException("Unknown symbol: " + symbol);
            }
        }

        newPart.setWorkflow(rule.noMatchDest);
        return total.add(calculateDistinct(newPart, rules));
    }

    public static class RangedPart {
        private final Range x;

        private final Range m;

        private final Range a;

        private final Range s;

        private String workflow = "in";

        private State state = State.IN_PROGRESS;

        public RangedPart(Range x, Range m, Range a, Range s) {
            this.x = x;
            this.m = m;
            this.a = a;
            this.s = s;
        }

        public Range getValue(String key) {
            return switch (key) {
                case "x" -> x;
                case "m" -> m;
                case "a" -> a;
                case "s" -> s;
                default -> throw new IllegalArgumentException("Unknown key: " + key);
            };
        }

        public long product() {
            return x.getTotal() * m.getTotal() * a.getTotal() * s.getTotal();
        }

        public void setWorkflow(String workflow) {
            if (workflow.equals("A")) {
                this.state = State.ACCEPTED;
            } else if (workflow.equals("R")) {
                this.state = State.REJECTED;
            } else {
                this.workflow = workflow;
            }
        }

        public RangedPart copy() {
            var part = new RangedPart(new Range(x.min, x.max), new Range(m.min, m.max), new Range(a.min, a.max), new Range(s.min, s.max));
            part.workflow = workflow;
            part.state = state;
            return part;
        }

        @Override
        public String toString() {
            return "RangedPart{" +
                    "x=" + x +
                    ", m=" + m +
                    ", a=" + a +
                    ", s=" + s +
                    ", workflow='" + workflow + '\'' +
                    ", state=" + state +
                    '}';
        }
    }

    public static class Range {
        private long min;
        private long max;

        public Range(long min, long max) {
            this.min = min;
            this.max = max;
        }

        public long getTotal() {
            return max - min;
        }

        @Override
        public String toString() {
            return "[" + min + "," + max + ")";
        }
    }

    public static class Rule {
        private final String id;
        private final List<Triple<String, String, Compariser>> comparisers;

        private String noMatchDest;

        public Rule(String rule) {
            this.comparisers = new List<>();

            var split = rule.split("\\{");
            this.id = split[0];

            var flows = split[1].substring(0, split[1].length() - 1).split(",");

            for (var flow : flows) {
                if (flow.contains(":")) {
                    var splitFlow = flow.split(":");

                    var compariser = new Triple<>(splitFlow[1], splitFlow[0].charAt(0) + "", new Compariser(splitFlow[0].charAt(1) + "", new LongInput(splitFlow[0].substring(2)).asLong()));
                    this.comparisers.add(compariser);
                } else {
                    noMatchDest = flow;
                }
            }
        }

        public void mapToDest(Part part) {
            for (var compariser : comparisers) {
                var dest = compariser.getA();
                var valueID = compariser.getB();
                var comp = compariser.getC();

                part.getValue(valueID);

                if (comp.compare(part.getValue(valueID))) {
                    part.setWorkflow(dest);
                    return;
                }
            }

            part.setWorkflow(noMatchDest);
        }
    }

    public static class Compariser {
        private final String symbol;
        private final long against;

        public Compariser(String symbol, long against) {
            this.symbol = symbol;
            this.against = against;
        }

        public boolean compare(long number) {
            return switch (symbol) {
                case ">" -> number > against;
                case "<" -> number < against;
                default -> throw new IllegalArgumentException("Unknown symbol: " + symbol);
            };
        }
    }

    public static class Part {
        private final long x;

        private final long m;

        private final long a;

        private final long s;

        private String workflow = "in";

        private State state = State.IN_PROGRESS;

        public Part(long x, long m, long a, long s) {
            this.x = x;
            this.m = m;
            this.a = a;
            this.s = s;
        }

        public long getValue(String key) {
            return switch (key) {
                case "x" -> x;
                case "m" -> m;
                case "a" -> a;
                case "s" -> s;
                default -> throw new IllegalArgumentException("Unknown key: " + key);
            };
        }

        public long sum() {
            return x + m + a + s;
        }

        public void setWorkflow(String workflow) {
            if (workflow.equals("A")) {
                this.state = State.ACCEPTED;
            } else if (workflow.equals("R")) {
                this.state = State.REJECTED;
            } else {
                this.workflow = workflow;
            }
        }

        @Override
        public String toString() {
            return "Part{" +
                    "x=" + x +
                    ", m=" + m +
                    ", a=" + a +
                    ", s=" + s +
                    ", workflow='" + workflow + '\'' +
                    ", state=" + state +
                    '}';
        }
    }

    public enum State {
        ACCEPTED,
        REJECTED,
        IN_PROGRESS
    }
}
