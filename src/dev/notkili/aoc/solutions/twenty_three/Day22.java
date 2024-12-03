package dev.notkili.aoc.solutions.twenty_three;

import dev.notkili.aoc.shared.input.IntInput;
import dev.notkili.aoc.shared.input.LongInput;
import dev.notkili.aoc.shared.misc.Count;
import dev.notkili.aoc.shared.misc.TimeTracker;
import dev.notkili.aoc.shared.misc.collections.list.List;
import dev.notkili.aoc.shared.misc.collections.set.Set;
import dev.notkili.aoc.shared.misc.position.Int3DPos;
import dev.notkili.aoc.shared.misc.tuple.Triple;
import dev.notkili.aoc.shared.misc.tuple.Tuple;
import dev.notkili.aoc.shared.parse.InputParser;

import java.util.Comparator;
import java.util.HashMap;

public class Day22 {
    public static void main(String[] args) {
        TimeTracker.trackTime(Day22::part1);
        TimeTracker.trackTime(Day22::part2);
    }

    public static void part1() {
        new InputParser(2023, 22).getInput().ifPresent(input -> {
            var bricks = new List<>(input.split("\n")
                    .mapTo(str -> str.split("~"))
                    .mapTo(str -> new Tuple<>(str.get(0).triple(","), str.get(1).triple(",")))
                    .mapTo(t -> new Tuple<>(
                            new Triple<>(t.getA().getA().integer(), t.getA().getC().integer(), t.getA().getB().integer()),
                            new Triple<>(t.getB().getA().integer(), t.getB().getC().integer(), t.getB().getB().integer()))
                    ).mapTo(t -> new Brick(t.getA(), t.getB())).asList());


            bricks.sort(Comparator.comparing(b -> b.posA.getY()));

            var movedDown = new List<Brick>();

            HashMap<Integer, List<Brick>> botLevel = new HashMap<>();
            HashMap<Integer, List<Brick>> topLevel = new HashMap<>();

            bricks.forEach(b -> {
                if (b.posA.getY() != 0) {
                    var adjacent = b.getBotAdjacent(false);

                    if (movedDown.containsAny(br -> br.getTop(false).intersection(adjacent).size() > 0)) {
                        movedDown.add(b);
                        botLevel.computeIfAbsent(b.posA.getY(), (k) -> new List<>()).add(b);
                        topLevel.computeIfAbsent(b.posB.getY(), (k) -> new List<>()).add(b);
                        return;
                    }

                    while (true) {
                        if (b.posA.getY() == 0) {
                            break;
                        }

                        b = new Brick(b.id, b.posA.add(0, -1, 0), b.posB.add(0, -1, 0));
                        var botAdjacent = b.getBotAdjacent(false);

                        if (movedDown.containsAny(br -> br.getTop(false).intersection(botAdjacent).size() > 0)) {
                            break;
                        }
                    }
                };

                movedDown.add(b);
                botLevel.computeIfAbsent(b.posA.getY(), (k) -> new List<>()).add(b);
                topLevel.computeIfAbsent(b.posB.getY(), (k) -> new List<>()).add(b);
            });

            Count count = new Count(0);

            Count actionCount = new Count(0);

            movedDown.forEach(Brick::reset);

            movedDown.forEach(b -> {
                actionCount.increment();
                var supported = b.getSupporting(botLevel, true);

                if (supported.size() == 0) {
                    count.increment();
                    return;
                }

                var otherSupporting = topLevel.get(b.posB.getY()).findAll(br -> br != b).map(br -> br.getSupporting(botLevel, true));

                if (supported.removeAll(br -> otherSupporting.containsAny(l -> l.contains(br))).size() == 0) {
                    count.increment();
                }
            });

            new IntInput(count.getCount()).solution().print(); //.submit(2023, 22, 1);
        });
    }

    public static void part2() {
        Brick.ID_F = 'A';

        new InputParser(2023, 22).getInput().ifPresent(input -> {
            var bricks = new List<>(input.split("\n")
                    .mapTo(str -> str.split("~"))
                    .mapTo(str -> new Tuple<>(str.get(0).triple(","), str.get(1).triple(",")))
                    .mapTo(t -> new Tuple<>(
                            new Triple<>(t.getA().getA().integer(), t.getA().getC().integer(), t.getA().getB().integer()),
                            new Triple<>(t.getB().getA().integer(), t.getB().getC().integer(), t.getB().getB().integer()))
                    ).mapTo(t -> new Brick(t.getA(), t.getB())).asList());


            bricks.sort(Comparator.comparing(b -> b.posA.getY()));

            var movedDown = new List<Brick>();

            HashMap<Integer, List<Brick>> botLevel = new HashMap<>();
            HashMap<Integer, List<Brick>> topLevel = new HashMap<>();

            bricks.forEach(b -> {
                if (b.posA.getY() != 0) {
                    var adjacent = b.getBotAdjacent(false);

                    if (movedDown.containsAny(br -> br.getTop(false).intersection(adjacent).size() > 0)) {
                        movedDown.add(b);
                        botLevel.computeIfAbsent(b.posA.getY(), (k) -> new List<>()).add(b);
                        topLevel.computeIfAbsent(b.posB.getY(), (k) -> new List<>()).add(b);
                        return;
                    }

                    while (true) {
                        if (b.posA.getY() == 0) {
                            break;
                        }

                        b = new Brick(b.id, b.posA.add(0, -1, 0), b.posB.add(0, -1, 0));
                        var botAdjacent = b.getBotAdjacent(false);

                        if (movedDown.containsAny(br -> br.getTop(false).intersection(botAdjacent).size() > 0)) {
                            break;
                        }
                    }
                };

                movedDown.add(b);
                botLevel.computeIfAbsent(b.posA.getY(), (k) -> new List<>()).add(b);
                topLevel.computeIfAbsent(b.posB.getY(), (k) -> new List<>()).add(b);
            });

            movedDown.forEach(Brick::reset);

            LongInput count = new LongInput(0);

            for (var brick : movedDown) {
                Set<Brick> moved = new Set<>();

                moved.add(brick);

                while (true) {
                    var exit = true;

                    for (var b2 : movedDown) {
                        if (moved.contains(b2))
                            continue;

                        var supported = b2.getSupportedBy(topLevel, true);

                        if (supported.size() == 0)
                            continue;

                        if (supported.removeAll(moved::contains).size() == 0) {
                            moved.add(b2);
                            exit = false;
                        }
                    }

                    if (exit)
                        break;
                }

                if (moved.size() != 1) {
                    count = count.add(moved.size() - 1);
                }
            }


            count.solution().print();
        });
    }

    public static class Brick {
        private static Character ID_F = 'A';

        private final char id;
        private final Int3DPos posA;
        private final Int3DPos posB;

        private Set<Brick> supporting = new Set<>();

        private Set<Brick> supported = new Set<>();

        public Brick(char id, Int3DPos posA, Int3DPos posB) {
            this.id = id;
            this.posA = posA;
            this.posB = posB;
        }

        public Brick(Triple<IntInput, IntInput, IntInput> posA, Triple<IntInput, IntInput, IntInput> posB) {
            this.id = ID_F++;
            this.posA = new Int3DPos(Math.min(posA.getA().asInt(), posB.getA().asInt()), Math.min(posA.getB().asInt(), posB.getB().asInt()), Math.min(posA.getC().asInt(), posB.getC().asInt()));
            this.posB = new Int3DPos(Math.max(posA.getA().asInt(), posB.getA().asInt()), Math.max(posA.getB().asInt(), posB.getB().asInt()), Math.max(posA.getC().asInt(), posB.getC().asInt()));
        }

        private Set<Brick> getSupporting(HashMap<Integer, List<Brick>> levels, boolean cache) {
            if (supporting != null && cache)
                return supporting.copy();

            supporting = new Set<>();

            var above = levels.get(posB.getY() + 1);

            if (above == null) {
                return supporting.copy();
            }

            for (var brick : above) {
                if (brick.getBotAdjacent(cache).intersection(getTop(cache)).size() > 0) {
                    supporting.add(brick);
                }
            }

            return supporting.copy();
        }

        private Set<Brick> getSupportedBy(HashMap<Integer, List<Brick>> levels, boolean cache) {
            if (supported != null && cache)
                return supported.copy();

            supported = new Set<>();

            var bot = getBotAdjacent(cache);

            var below = levels.get(posA.getY() - 1);

            if (below == null) {
                return supported.copy();
            }

            for (var brick : below) {
                if (brick.getTop(cache).intersection(bot).size() > 0) {
                    supported.add(brick);
                }
            }

            return supported.copy();
        }

        private Set<Int3DPos> top = null;

        private Set<Int3DPos> getTop(boolean cache) {
            if (top != null && cache)
                return top.copy();

            top = new Set<>();

            for (int x = posA.getX(); x <= posB.getX(); x++) {
                for (int z = posA.getZ(); z <= posB.getZ(); z++) {
                    top.add(new Int3DPos(x, posB.getY(), z));
                }
            }

            return top.copy();
        }

        private Set<Int3DPos> botAdjacent = null;

        private Set<Int3DPos> getBotAdjacent(boolean cache) {
            if (botAdjacent != null && cache)
                return botAdjacent.copy();

            botAdjacent = new Set<>();

            for (int x = posA.getX(); x <= posB.getX(); x++) {
                for (int z = posA.getZ(); z <= posB.getZ(); z++) {
                    botAdjacent.add(new Int3DPos(x, posA.getY() - 1, z));
                }
            }

            return botAdjacent.copy();
        }

        public void reset() {
            botAdjacent = null;
            top = null;
            supporting = null;
            supported = null;
        }

        @Override
        public int hashCode() {
            return id;
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof Brick && ((Brick) obj).id == id;
        }

        @Override
        public String toString() {
            return "Brick " + id + "{" +
                    "posA=" + posA +
                    ", posB=" + posB +
                    '}';
        }
    }
}
