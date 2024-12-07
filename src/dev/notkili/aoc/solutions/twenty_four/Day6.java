package dev.notkili.aoc.solutions.twenty_four;

import dev.notkili.aoc.shared.input.CharInput;
import dev.notkili.aoc.shared.input.IntInput;
import dev.notkili.aoc.shared.misc.collections.set.Set;
import dev.notkili.aoc.shared.misc.position.Direction;
import dev.notkili.aoc.shared.misc.position.Int2DPos;
import dev.notkili.aoc.shared.misc.tuple.Quadruple;
import dev.notkili.aoc.shared.misc.tuple.Tuple;
import dev.notkili.aoc.shared.parse.InputParser;

import java.util.LinkedList;
import java.util.Objects;

public class Day6 {
    public static void main(String[] args) {
        System.out.println("Solution 1:");

        new InputParser(2024, 6).getInput().ifPresent(strInput -> {
            var map = strInput.charMap();
            Int2DPos guardPos = null;
            
            for (var pos : map.positions()) {
                if (map.get(pos).equals("^")) {
                    guardPos = pos;
                }
            }

            Objects.requireNonNull(guardPos);
            
            var dist = new Set<Int2DPos>();
            var orientation = Direction.N;
            
            while (map.contains(guardPos)) {
                dist.add(guardPos);
                
                if (map.contains(guardPos.add(orientation)) && map.get(guardPos.add(orientation)).equals("#")) {
                    orientation = orientation.nextClockwise(false);
                } else {
                    guardPos = guardPos.add(orientation);
                }
            }
            
            // new IntInput(dist.size()).solution().submit(2024, 6, 1);
            new IntInput(dist.size()).solution().print();
        });

        System.out.println("Solution 2:");

        new InputParser(2024, 6).getInput().ifPresent(strInput -> {
            var map = strInput.charMap();
            Int2DPos guardPos = null;

            for (var pos : map.positions()) {
                if (map.get(pos).equals("^")) {
                    guardPos = pos;
                    map.put(pos, new CharInput('.'));
                }
            }

            Objects.requireNonNull(guardPos);
            
            // Current, Facing, Modified, Visited
            var queue = new LinkedList<Quadruple<Int2DPos, Direction, Int2DPos, Set<Tuple<Int2DPos, Direction>>>>();

            queue.add(new Quadruple<>(guardPos, Direction.N, null, new Set<>()));

            var obstacles = new Set<Int2DPos>();
            
            while (!queue.isEmpty()) {
                var next = queue.poll();

                var currentPosition = next.getA();
                var currentFacing = next.getB();
                var customObstaclePos = next.getC();
                var visitedPositions = next.getD();

                // Left map, no circle
                if (!map.contains(currentPosition)) {
                    continue;
                }
                
                // Irrelevant branch
                if (obstacles.contains(customObstaclePos)) {
                    continue;
                }

                var positionAndFacing = new Tuple<>(currentPosition, currentFacing);

                // Reached previously visited pos -> cycle
                if (visitedPositions.contains(positionAndFacing)) {
                    obstacles.add(customObstaclePos);
                    continue;
                }

                visitedPositions.add(positionAndFacing);

                var nextPosition = currentPosition.add(currentFacing);

                if (!map.contains(nextPosition))
                    continue;

                if (map.get(nextPosition).equals("#") || nextPosition.equals(customObstaclePos)) {
                    queue.add(new Quadruple<>(currentPosition, currentFacing.nextClockwise(false), customObstaclePos, visitedPositions));

                } else {
                    queue.add(new Quadruple<>(nextPosition, currentFacing, customObstaclePos, visitedPositions));

                    if (customObstaclePos == null) {
                        if (visitedPositions.containsAny(Set.of(new Tuple<>(nextPosition, Direction.N), new Tuple<>(nextPosition, Direction.S), new Tuple<>(nextPosition, Direction.E), new Tuple<>(nextPosition, Direction.W)))) {
                            continue;
                        }
                        
                        queue.add(new Quadruple<>(currentPosition, currentFacing.nextClockwise(false), nextPosition, visitedPositions.copy()));
                    }
                }
            }

            new IntInput(obstacles.size()).solution().print();
            // new IntInput(loopCount).solution().submit(2024, 6, 2);
        });
    }
}
