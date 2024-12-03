package dev.notkili.aoc.solutions.twenty_three;

import dev.notkili.aoc.shared.input.IntInput;
import dev.notkili.aoc.shared.input.StringInput;
import dev.notkili.aoc.shared.misc.collections.list.List;
import dev.notkili.aoc.shared.misc.collections.set.Set;
import dev.notkili.aoc.shared.misc.position.Direction;
import dev.notkili.aoc.shared.misc.position.Int2DPos;
import dev.notkili.aoc.shared.misc.tuple.Triple;
import dev.notkili.aoc.shared.misc.tuple.Tuple;
import dev.notkili.aoc.shared.parse.InputParser;

import java.util.HashMap;
import java.util.Queue;

public class Day10 {
    public static void main(String[] args) {
        part1();

        part2();
    }

    private static void part1() {
        new InputParser(2023, 10).getInput().ifPresent(input -> {
            var parsed = parseInput(input);
            var start = parsed.getA();
            var map = parsed.getB();

            var mostDistantLocation = findLoop(start, map, true);
            new IntInput(mostDistantLocation.getB()).solution().print(); //.submit(2023, 10, 1);
        });
    }

    private static Tuple<Int2DPos, HashMap<Int2DPos, String>> parseInput(StringInput input) {
        var map = new HashMap<Int2DPos, String>();
        Int2DPos start = null;

        var split = input.split("\n");

        int y = 0;
        for (StringInput line : split) {
            int x = 0;
            for (StringInput symbol : line.split("")) {
                var s = symbol.str();

                if (s.equals(".")) {
                    x++;
                    continue;
                }


                map.put(new Int2DPos(x, y), symbol.str());

                if (s.equals("S")) {
                    start = new Int2DPos(x, y);
                }

                x++;
            }
            y++;
        }

        return new Tuple<>(start, map);
    }

    private static Triple<Int2DPos, Integer, List<Int2DPos>> findLoop(Int2DPos start, HashMap<Int2DPos, String> map, boolean biDirectional) {
        Set<Int2DPos> visited = new Set<>();
        Queue<Triple<Int2DPos, Integer, List<Int2DPos>>> queue = new java.util.LinkedList<>();

        visited.add(start);

        outer:
        for (var neighbour : start.getNeighbours(Direction.CardArr)) {
            if (map.containsKey(neighbour)) {
                var pipe = Pipe.fromSymbol(map.get(neighbour));

                for (Tuple<Integer, Integer> possibleConnection : pipe.getPossibleConnections()) {
                    if (neighbour.add(possibleConnection.getA(), possibleConnection.getB()).equals(start)) {
                        queue.add(new Triple<>(neighbour, 1, new List<>(start, neighbour)));

                        if (!biDirectional)
                            break outer;
                    }
                }
            }
        }

        var mostDistantLocation = new Triple<>(new Int2DPos(0, 0), 0, new List<Int2DPos>());

        while (!queue.isEmpty()) {
            var elem = queue.poll();

            if (visited.contains(elem.getA())) {
                continue;
            }

            visited.add(elem.getA());

            if (mostDistantLocation.getB() < elem.getB()) {
                mostDistantLocation = elem;
            }

            for (Triple<Int2DPos, Integer, List<Int2DPos>> int2DPosIntegerTuple : calculateConnectedLines(elem, map, visited)) {
                queue.add(int2DPosIntegerTuple);
            }
        }

        return mostDistantLocation;
    }

    private static List<Triple<Int2DPos, Integer, List<Int2DPos>>> calculateConnectedLines(Triple<Int2DPos, Integer, List<Int2DPos>> current, HashMap<Int2DPos, String> all, Set<Int2DPos> visited) {
        var result = new List<Triple<Int2DPos, Integer, List<Int2DPos>>>();

        var currentPipe = Pipe.fromSymbol(all.get(current.getA()));
        var possible = currentPipe.getPossibleConnections();

        possible.forEach(pos -> {
            var newPos = current.getA().add(pos.getA(), pos.getB());

            if (all.containsKey(newPos))
                result.add(new Triple<>(newPos, current.getB() + 1, current.getC().copy().add(newPos)));

        });

        return result;
    }

    private static void part2() {
        new InputParser(2023, 10).getInput().ifPresent(input -> {
            var parsed = parseInput(input);
            var start = parsed.getA();
            var map = parsed.getB();

            var loop = findLoop(start, map, false);

            var inLoop = loop.getC().set();
            var mapX2 = new HashMap<Int2DPos, String>();
            var split = input.split("\n");
            int maxX = split.get(0).length().asInt() * 2;
            int maxY = split.size() * 2;

            int y = 0;
            for (StringInput line : split) {
                int x = 0;
                for (StringInput symbol : line.split("")) {
                    var s = symbol.str();

                    if (inLoop.contains(new Int2DPos(x / 2, y / 2))) {
                        switch (s) {
                            case ".":
                            case "S":
                                mapX2.put(new Int2DPos(x, y), s);
                                mapX2.put(new Int2DPos(x + 1, y), s);
                                mapX2.put(new Int2DPos(x, y + 1), s);
                                mapX2.put(new Int2DPos(x + 1, y + 1), s);
                                break;
                            case "-":
                                mapX2.put(new Int2DPos(x, y), s);
                                mapX2.put(new Int2DPos(x + 1, y), s);
                                mapX2.put(new Int2DPos(x, y + 1), ".");
                                mapX2.put(new Int2DPos(x + 1, y + 1), ".");
                                break;
                            case "|":
                                mapX2.put(new Int2DPos(x, y), s);
                                mapX2.put(new Int2DPos(x + 1, y), ".");
                                mapX2.put(new Int2DPos(x, y + 1), s);
                                mapX2.put(new Int2DPos(x + 1, y + 1), ".");
                                break;
                            case "L":
                                mapX2.put(new Int2DPos(x, y), s);
                                mapX2.put(new Int2DPos(x + 1, y), "-");
                                mapX2.put(new Int2DPos(x, y + 1), ".");
                                mapX2.put(new Int2DPos(x + 1, y + 1), ".");
                                break;
                            case "J":
                                mapX2.put(new Int2DPos(x, y), s);
                                mapX2.put(new Int2DPos(x + 1, y), ".");
                                mapX2.put(new Int2DPos(x, y + 1), ".");
                                mapX2.put(new Int2DPos(x + 1, y + 1), ".");
                                break;
                            case "7":
                                mapX2.put(new Int2DPos(x, y), s);
                                mapX2.put(new Int2DPos(x + 1, y), ".");
                                mapX2.put(new Int2DPos(x, y + 1), "|");
                                mapX2.put(new Int2DPos(x + 1, y + 1), ".");
                                break;
                            case "F":
                                mapX2.put(new Int2DPos(x, y), s);
                                mapX2.put(new Int2DPos(x + 1, y), "-");
                                mapX2.put(new Int2DPos(x, y + 1), "|");
                                mapX2.put(new Int2DPos(x + 1, y + 1), ".");
                                break;
                        }
                    } else {
                        mapX2.put(new Int2DPos(x, y), s);
                        mapX2.put(new Int2DPos(x + 1, y), s);
                        mapX2.put(new Int2DPos(x, y + 1), s);
                        mapX2.put(new Int2DPos(x + 1, y + 1), s);
                    }

                    x += 2;
                }
                y += 2;
            }

            Set<Int2DPos> emptyPositions = new Set<>();
            Set<Int2DPos> relevantPositions = new Set<>();

            for (int tmpY = 0; tmpY < maxY; tmpY++) {
                for (int tmpX = 0; tmpX < maxX; tmpX++) {
                    if (mapX2.get(new Int2DPos(tmpX, tmpY)).equals("."))
                        emptyPositions.add(new Int2DPos(tmpX, tmpY));

                    if (!inLoop.contains(new Int2DPos(tmpX / 2, tmpY / 2))) {
                        relevantPositions.add(new Int2DPos(tmpX, tmpY));
                        emptyPositions.add(new Int2DPos(tmpX, tmpY));
                    }
                }
            }

            var notEnclosed = new Set<Int2DPos>();
            var checked = new Set<Int2DPos>();

            for (var pos : relevantPositions) {
                if (checked.contains(pos))
                    continue;

                var bfs = bfsSearch(pos, emptyPositions);

                if (bfs.set().stream().anyMatch(p -> p.getX() == 0 || p.getY() == 0 || p.getX() == maxX - 1 || p.getY() == maxY - 1))
                    notEnclosed.addAll(bfs);

                checked.addAll(bfs);
            }

            var enclosed = relevantPositions.subtract(notEnclosed);

            new IntInput(
                    enclosed
                            .set()
                            .stream()
                            .map(p -> new Int2DPos(p.getX() / 2, p.getY() / 2))
                            .distinct()
                            .count())
                    .solution().print();//.submit(2023, 10, 2);
        });
    }

    private static Set<Int2DPos> bfsSearch(Int2DPos start, Set<Int2DPos> allPositions) {
        Set<Int2DPos> visited = new Set<>();
        Queue<Int2DPos> queue = new java.util.LinkedList<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            var current = queue.poll();

            for (var neighbour : current.getNeighbours(Direction.CardArr)) {
                if (visited.contains(neighbour))
                    continue;

                if (!allPositions.contains(neighbour))
                    continue;

                queue.add(neighbour);
                visited.add(neighbour);
            }
        }

        return visited;
    }

    enum Pipe {
        HORIZONTAL("-", new List<>(new Tuple<>(-1, 0), new Tuple<>(1, 0))),
        VERTICAL("|", new List<>(new Tuple<>(0, -1), new Tuple<>(0, 1))),
        NORTH_EAST("L", new List<>(new Tuple<>(0, -1), new Tuple<>(1, 0))),
        NORTH_WEST("J", new List<>(new Tuple<>(0, -1), new Tuple<>(-1, 0))),
        SOUTH_WEST("7", new List<>(new Tuple<>(0, 1), new Tuple<>(-1, 0))),
        SOUTH_EAST("F", new List<>(new Tuple<>(0, 1), new Tuple<>(1, 0)));

        private final String symbol;
        private final List<Tuple<Integer, Integer>> possibleConnections;

        Pipe(String symbol, List<Tuple<Integer, Integer>> possibleConnections) {
            this.symbol = symbol;
            this.possibleConnections = possibleConnections;
        }

        public static Pipe fromSymbol(String symbol) {
            for (Pipe pipe : Pipe.values()) {
                if (pipe.getSymbol().equals(symbol)) {
                    return pipe;
                }
            }

            throw new IllegalArgumentException("No pipe with symbol " + symbol);
        }

        public String getSymbol() {
            return symbol;
        }

        public List<Tuple<Integer, Integer>> getPossibleConnections() {
            return possibleConnections;
        }
    }
}
