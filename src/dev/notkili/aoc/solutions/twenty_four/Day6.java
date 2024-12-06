package dev.notkili.aoc.solutions.twenty_four;

import dev.notkili.aoc.shared.input.CharInput;
import dev.notkili.aoc.shared.input.IntInput;
import dev.notkili.aoc.shared.misc.collections.set.Set;
import dev.notkili.aoc.shared.misc.position.Direction;
import dev.notkili.aoc.shared.misc.position.Int2DPos;
import dev.notkili.aoc.shared.misc.tuple.Tuple;
import dev.notkili.aoc.shared.parse.InputParser;

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

            var dist = new Set<Int2DPos>();
            var orientation = Direction.N;
            var mutableGuardPos = guardPos;

            while (map.contains(mutableGuardPos)) {
                dist.add(mutableGuardPos);

                if (map.contains(mutableGuardPos.add(orientation)) && map.get(mutableGuardPos.add(orientation)).equals("#")) {
                    orientation = orientation.nextClockwise(false);
                } else {
                    mutableGuardPos = mutableGuardPos.add(orientation);
                }
            }
            
            var obstacles = new Set<Int2DPos>();
            
            for (var onPath : dist) {
                if (onPath.equals(guardPos)) {
                    continue;
                }
                
                map.put(onPath, new CharInput('#'));

                mutableGuardPos = guardPos;
                orientation = Direction.N;
                var visited = new Set<Tuple<Int2DPos, Direction>>();

                while (map.contains(mutableGuardPos) && !visited.contains(new Tuple<>(mutableGuardPos, orientation))) {
                    visited.add(new Tuple<>(mutableGuardPos, orientation));
                    
                    if (map.contains(mutableGuardPos.add(orientation)) && map.get(mutableGuardPos.add(orientation)).equals("#")) {
                        orientation = orientation.nextClockwise(false);
                    } else {
                        mutableGuardPos = mutableGuardPos.add(orientation);
                    }
                }
                
                if (map.contains(mutableGuardPos)) {
                    obstacles.add(onPath);
                }
                
                map.put(onPath, new CharInput('.'));
            }

            new IntInput(obstacles.size()).solution().print();
        });
    }
}
