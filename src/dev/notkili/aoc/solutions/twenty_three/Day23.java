package dev.notkili.aoc.solutions.twenty_three;

import dev.notkili.aoc.shared.input.ArrayInput2D;
import dev.notkili.aoc.shared.input.IntInput;
import dev.notkili.aoc.shared.misc.TimeTracker;
import dev.notkili.aoc.shared.misc.collections.list.List;
import dev.notkili.aoc.shared.misc.collections.set.Set;
import dev.notkili.aoc.shared.misc.position.Direction;
import dev.notkili.aoc.shared.misc.position.Int2DPos;
import dev.notkili.aoc.shared.misc.position.Int3DPos;
import dev.notkili.aoc.shared.misc.tuple.Quadruple;
import dev.notkili.aoc.shared.misc.tuple.Triple;
import dev.notkili.aoc.shared.misc.tuple.Tuple;
import dev.notkili.aoc.shared.parse.InputParser;

import java.util.*;

public class Day23 {
    public static void main(String[] args) {
        TimeTracker.trackTime(Day23::part1);
        TimeTracker.trackTime(Day23::part2);
    }

    private static void part1() {
        new InputParser(2023, 23).getInput().ifPresent(input -> {
            var array = ArrayInput2D.parse(input).getBackingArray();

            var map = new HashMap<Int2DPos, Character>();

            for (int y = 0; y < array.length; y++) {
                for (int x = 0; x < array[y].length; x++) {
                    map.put(new Int2DPos(x, y), array[y][x].asChar());
                }
            }

            Int2DPos start = null;

            for (int x = 0; x < array[0].length; x++) {
                if (map.get(new Int2DPos(x, 0)) == '.') {
                    start = new Int2DPos(x, 0);
                }
            }

            Int2DPos end = null;

            for (int x = 0; x < array[0].length; x++) {
                if (map.get(new Int2DPos(x, array.length - 1)) == '.') {
                    end = new Int2DPos(x, array.length - 1);
                }
            }

            var nodes = flattenPart1(start, end, map);
            findLongestPathBetween(nodes.get(start), nodes.get(end), nodes).asSolution().print();
        });
    }

    private static void part2() {
        new InputParser(2023, 23).getInput().ifPresent(input -> {
            var array = ArrayInput2D.parse(input.replaceAll("[\\^><v]", ".")).getBackingArray();

            var map = new HashMap<Int2DPos, Character>();

            for (int y = 0; y < array.length; y++) {
                for (int x = 0; x < array[y].length; x++) {
                    map.put(new Int2DPos(x, y), array[y][x].asChar());
                }
            }

            Int2DPos start = null;

            for (int x = 0; x < array[0].length; x++) {
                if (map.get(new Int2DPos(x, 0)) == '.') {
                    start = new Int2DPos(x, 0);
                }
            }

            Int2DPos end = null;

            for (int x = 0; x < array[0].length; x++) {
                if (map.get(new Int2DPos(x, array.length - 1)) == '.') {
                    end = new Int2DPos(x, array.length - 1);
                }
            }

            var nodes = flattenPart2(start, end, map);
            findLongestPathBetween(nodes.get(start), nodes.get(end), nodes).asSolution().print();
        });
    }

    private static HashMap<Int2DPos, Node> flattenPart1(Int2DPos start, Int2DPos end, HashMap<Int2DPos, Character> map) {
        var nodes = new HashMap<Int2DPos, Node>();

        var maxX = map.keySet().stream().mapToInt(Int2DPos::getX).max().getAsInt();
        var maxY = map.keySet().stream().mapToInt(Int2DPos::getY).max().getAsInt();

        nodes.put(start, new Node(start));
        nodes.put(end, new Node(end));

        for (int y = 0; y <= maxY; y++) {
            for (int x = 0; x <= maxX; x++) {
                var pos = new Int2DPos(x, y);

                if (!map.containsKey(pos))
                    continue;

                if (map.get(pos).equals('#'))
                    continue;

                if (pos.getNeighbours(Direction.CardArr).removeAll(p -> !map.containsKey(p) || map.get(p).equals('#')).size() > 2) {
                    nodes.put(pos, new Node(pos));
                }
            }
        }

        for (var node : nodes.values()) {
            var queue = new LinkedList<Triple<Int2DPos, Direction, Integer>>();
            var visited = new Set<Int2DPos>();

            for (var dir : Direction.CardArr) {
                var neigh = node.position.add(dir.getDelta());

                if (!map.containsKey(neigh)) {
                    continue;
                }

                var val = map.get(neigh);
                if (val.equals('#')) {
                    continue;
                }

                if (!val.equals('.')) {
                    switch (val) {
                        case '>' -> {
                            if (dir == Direction.EAST) queue.add(new Triple<>(neigh, Direction.EAST, 1));
                        }
                        case '<' -> {
                            if (dir == Direction.WEST) queue.add(new Triple<>(neigh, Direction.WEST, 1));
                        }
                        case '^' -> {
                            if (dir == Direction.NORTH) queue.add(new Triple<>(neigh, Direction.NORTH, 1));
                        }
                        case 'v' -> {
                            if (dir == Direction.SOUTH) queue.add(new Triple<>(neigh, Direction.SOUTH, 1));
                        }
                    }
                    continue;
                }

                queue.add(new Triple<>(neigh, null, 1));
            }

            while (!queue.isEmpty()) {
                var current = queue.poll();

                var pos = current.getA();

                if (nodes.containsKey(pos)) {
                    node.addAdjacentNode(nodes.get(pos), current.getC());
                    continue;
                }

                if (visited.contains(pos)) {
                    continue;
                }

                visited.add(pos);

                var neighbours = new List<Int2DPos>();

                if (current.getB() != null)
                    neighbours = List.of(pos.add(current.getB().getDelta()));
                else
                    neighbours = new List<Int2DPos>().addAll(pos.getNeighbours(Direction.CardArr).set());

                for (var neigh : neighbours) {
                    if (!map.containsKey(neigh)) {
                        continue;
                    }

                    switch (map.get(neigh)) {
                        case '.' -> {
                            queue.add(new Triple<>(neigh, null, current.getC() + 1));
                        }
                        case '>' -> {
                            queue.add(new Triple<>(neigh, Direction.EAST, current.getC() + 1));
                        }
                        case '<' -> {
                            queue.add(new Triple<>(neigh, Direction.WEST, current.getC() + 1));
                        }
                        case '^' -> {
                            queue.add(new Triple<>(neigh, Direction.NORTH, current.getC() + 1));
                        }
                        case 'v' -> {
                            queue.add(new Triple<>(neigh, Direction.SOUTH, current.getC() + 1));
                        }
                    }
                }
            }
        }
        
        return nodes;
    }

    private static HashMap<Int2DPos, Node> flattenPart2(Int2DPos start, Int2DPos end, HashMap<Int2DPos, Character> map) {
        var nodes = new HashMap<Int2DPos, Node>();

        var maxX = map.keySet().stream().mapToInt(Int2DPos::getX).max().getAsInt();
        var maxY = map.keySet().stream().mapToInt(Int2DPos::getY).max().getAsInt();

        nodes.put(start, new Node(start));
        nodes.put(end, new Node(end));

        for (int y = 0; y <= maxY; y++) {
            for (int x = 0; x <= maxX; x++) {
                var pos = new Int2DPos(x, y);

                if (!map.containsKey(pos))
                    continue;

                if (map.get(pos).equals('#'))
                    continue;


                if (pos.getNeighbours(Direction.CardArr).removeAll(p -> !map.containsKey(p) || map.get(p).equals('#')).size() > 2) {
                    nodes.put(pos, new Node(pos));
                }
            }
        }

        for (var node : nodes.values()) {
            var queue = new LinkedList<Tuple<Int2DPos, Integer>>();
            var visited = new Set<Int2DPos>();

            for (var neigh : node.position.getNeighbours(Direction.CardArr)) {
                queue.add(new Tuple<>(neigh, 1));
            }

            while (!queue.isEmpty()) {
                var current = queue.poll();

                if (!map.containsKey(current.getA())) {
                    continue;
                }

                if (map.get(current.getA()).equals('#')) {
                    continue;
                }

                if (nodes.containsKey(current.getA())) {
                    node.addAdjacentNode(nodes.get(current.getA()), current.getB());
                    continue;
                }

                if (visited.contains(current.getA())) {
                    continue;
                }

                visited.add(current.getA());

                for (var neigh : current.getA().getNeighbours(Direction.CardArr)) {
                    queue.add(new Tuple<>(neigh, current.getB() + 1));
                }
            }
        }

        return nodes;
    }

    private static IntInput findLongestPathBetween(Node from, Node to, HashMap<Int2DPos, Node> nodes) {
        var queue = new LinkedList<Triple<Node, Integer, Set<Node>>>();

        IntInput max = new IntInput(-1);

        queue.add(new Triple<>(from, 0,  new Set<Node>().add(from)));

        while (!queue.isEmpty()) {
            var current = queue.poll();

            if (current.getA().equals(to)) {
                max = max.max(new IntInput(current.getB()));
                continue;
            }

            current.getA().adjacentNodes.forEach((node, dist) -> {
                if (current.getC().contains(node)) {
                    return;
                }

                queue.add(new Triple<>(node, current.getB() + dist, current.getC().copy().add(node)));
            });
        }

        return max;
    }


    private static class Node {
        private static int ID_F = 0;

        private final int id;
        private final Int2DPos position;

        private final HashMap<Node, Integer> adjacentNodes;

        public Node(Int2DPos position) {
            this.id = ID_F++;
            this.position = position;
            this.adjacentNodes = new HashMap<>();
        }

        public void addAdjacentNode(Node node, int dist) {
            if (node.id == id)
                return;

            adjacentNodes.put(node, dist);
        }

        @Override
        public int hashCode() {
            return id;
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof Node && ((Node) obj).id == id;
        }
    }
}
