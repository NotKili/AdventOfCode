package dev.notkili.aoc.solutions.twenty_three;

import dev.notkili.aoc.shared.input.IntInput;
import dev.notkili.aoc.shared.input.LongInput;
import dev.notkili.aoc.shared.input.StringInput;
import dev.notkili.aoc.shared.misc.TimeTracker;
import dev.notkili.aoc.shared.misc.collections.list.List;
import dev.notkili.aoc.shared.misc.collections.set.Set;
import dev.notkili.aoc.shared.misc.tuple.Triple;
import dev.notkili.aoc.shared.misc.tuple.Tuple;
import dev.notkili.aoc.shared.parse.InputParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Day24 {
    public static void main(String[] args) {
        TimeTracker.trackTime(Day24::part1);
        TimeTracker.trackTime(Day24::part2);
    }

    public static void part1() {
        new InputParser(2023, 24).getInput().ifPresent(input -> {
            var list = new List<>(input.replace("  ", " ").split("\n").mapTo(s -> s.tuple(" @ ")).mapTo(t -> {
                var pos = t.getA().split(", ").mapTo(StringInput::longInteger).asList();
                var delta = t.getB().split(", ").mapTo(StringInput::longInteger).asList();

                return new Line2D(new Tuple<>(pos.get(0).asDouble(), pos.get(1).asDouble()), delta.get(0).asInt(), delta.get(1).asInt());
            }).asList());

            var min = 200000000000000L;
            var max = 400000000000000L;

            var checked = new Set<Tuple<Line2D, Line2D>>();

            var collisions = new List<Triple<Line2D, Line2D, Tuple<Double, Double>>>();
            for (var line: list) {
                for (var other : list) {
                    if (line == other)
                        continue;

                    if (checked.contains(new Tuple<>(line, other)))
                        continue;

                    checked.add(new Tuple<>(line, other));
                    checked.add(new Tuple<>(other, line));

                    var pos = line.collidesAtXY(other);

                    if (pos != null) {
                        collisions.add(new Triple<>(line, other, pos));
                    }
                }
            }

            collisions.rem(p -> {
                var x = p.getC().getA();
                var y = p.getC().getB();

                return x < min || x > max || y < min || y > max;
            });

            new IntInput(collisions.size()).solution().print(); //.submit(2023, 24, 1);
        });
    }

    public static void part2() {
        new InputParser(2023, 24).getInput().ifPresent(input -> {
            var list = new List<>(input.replace("  ", " ").split("\n").mapTo(s -> s.tuple(" @ ")).mapTo(t -> {
                var pos = t.getA().split(", ").mapTo(StringInput::longInteger).asList();
                var delta = t.getB().split(", ").mapTo(StringInput::longInteger).asList();

                return new Line3D(new Triple<>(pos.get(0).asDouble(), pos.get(1).asDouble(), pos.get(2).asDouble()), delta.get(0).asInt(), delta.get(1).asInt(), delta.get(2).asInt());
            }).asList());

            var scanner = new Scanner(System.in);

            System.out.print("Enter the amount of equations you want to generate (4 should be fine): ");
            var amount = Integer.parseInt(scanner.nextLine());

            System.out.println("\nEquations: ");
            for (var index : generateRandomDistinct(amount, 0, list.size())) {
                System.out.println(list.get(index).generateEquation());
            }

            System.out.println("\nEnter these equations on WolframAlpha 'https://www.wolframalpha.com/input?i=system+equation+calculator' to solve.");

// https://www.wolframalpha.com/input?i=system+equation+calculator&assumption=%7B%22F%22%2C+%22SolveSystemOf4EquationsCalculator%22%2C+%22equation1%22%7D+-%3E%22%28x-2.60252047346974E14%29%2F%28a-66%29%3D%28y-3.60095837456982E14%29%2F%28b%2B174%29%3D%28z-9.086018216578E12%29%2F%28c-512%29%22&assumption=%7B%22F%22%2C+%22SolveSystemOf4EquationsCalculator%22%2C+%22equation3%22%7D+-%3E%22%28x-3.58771388883194E14%29%2F%28a-88%29%3D%28y-2.90970068566246E14%29%2F%28b%2B82%29%3D%28z-2.08977773545854E14%29%2F%28c-254%29%22&assumption=%22FSelect%22+-%3E+%7B%7B%22SolveSystemOf4EquationsCalculator%22%7D%2C+%22dflt%22%7D&assumption=%7B%22F%22%2C+%22SolveSystemOf4EquationsCalculator%22%2C+%22equation2%22%7D+-%3E%22%28x-5.11477129668052E14%29%2F%28a%2B386%29%3D%28y-5.4807041616582E14%29%2F%28b%2B384%29%3D%28z-5.20727565082156E14%29%2F%28c%2B322%29%22&assumption=%7B%22F%22%2C+%22SolveSystemOf4EquationsCalculator%22%2C+%22equation4%22%7D+-%3E%22%28x-1.26687249998278E14%29%2F%28a-156%29%3D%28y-2.81467449200748E14%29%2F%28b%2B21%29%3D%28z-3.8183884791769E14%29%2F%28c%2B138%29%22
// var x = 472612107765508L;
// var y = 270148844447628L;
// var z = 273604689965980L;

            System.out.print("Enter x: ");
            var x = Long.parseLong(scanner.nextLine());
            System.out.print("Enter y: ");
            var y = Long.parseLong(scanner.nextLine());
            System.out.print("Enter z: ");
            var z = Long.parseLong(scanner.nextLine());

            new LongInput(x + y + z).solution().print(); // .submit(2023, 24, 2);
        });
    }

    public static class Line3D {
        private final Triple<Double, Double, Double> startingPos;

        private final int deltaX;
        private final int deltaY;
        private final int deltaZ;

        public Line3D(Triple<Double, Double, Double> startingPos, int deltaX, int deltaY, int deltaZ) {
            this.startingPos = startingPos;
            this.deltaX = deltaX;
            this.deltaY = deltaY;
            this.deltaZ = deltaZ;
        }

        public String generateEquation() {
            var eq = "(x-" + startingPos.getA() + ")/(a-"+ deltaX + ")=(y-" + startingPos.getB() + ")/(b-" + deltaY + ")=(z-" + startingPos.getC() + ")/(c-" + deltaZ +")";
            eq = eq.replace("--", "+");

            return eq;
        }
    }

    public static class Line2D {
        private final Tuple<Double, Double> startingPos;

        private final int deltaX;

        private final int deltaY;

        public Line2D(Tuple<Double, Double> startingPos, int deltaX, int deltaY) {
            this.startingPos = startingPos;
            this.deltaX = deltaX;
            this.deltaY = deltaY;
        }

        public Tuple<Double, Double> collidesAtXY(Line2D other) {
            if (deltaY * other.deltaX - deltaX * other.deltaY == 0) {
                return null;
            }

            var x = (deltaX * other.deltaX * (other.startingPos.getB() - startingPos.getB()) - other.deltaY * deltaX * other.startingPos.getA() + deltaY * other.deltaX * startingPos.getA()) / (deltaY * other.deltaX - deltaX * other.deltaY);
            var y = (deltaY * other.deltaY * (other.startingPos.getA() - startingPos.getA()) - other.deltaX * deltaY * other.startingPos.getB() + deltaX * other.deltaY * startingPos.getB()) / (deltaX * other.deltaY - deltaY * other.deltaX);

            if (deltaX != 0 && (x - startingPos.getA()) / deltaX < 0 || other.deltaX != 0 && (x - other.startingPos.getA()) / other.deltaX < 0) {
                return null;
            }

            if (deltaY != 0 && (y - startingPos.getB()) / deltaY < 0 || other.deltaY != 0 && (y - other.startingPos.getB()) / other.deltaY < 0) {
                return null;
            }

            return new Tuple<>(x, y);
        }

        @Override
        public String toString() {
            return startingPos.toString() + ", delta: " + deltaX + ", " + deltaY;
        }

        @Override
        public int hashCode() {
            return startingPos.hashCode() + deltaX + deltaY;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Line2D other) {
                return startingPos.equals(other.startingPos) && deltaX == other.deltaX && deltaY == other.deltaY;
            }

            return false;
        }
    }

    private static List<Integer> generateRandomDistinct(int n, int fromIncl, int toExcl) {
        var list = new ArrayList<Integer>();

        for (int i = fromIncl; i < toExcl; i++) {
            list.add(i);
        }

        Collections.shuffle(list);

        var toReturn = new List<Integer>();

        for (int i = 0; i < n; i++) {
            toReturn.add(list.get(i));
        }

        return toReturn;
    }
}