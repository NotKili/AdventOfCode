package dev.notkili.aoc.solutions.twenty_four;

import dev.notkili.aoc.shared.input.CharInput;
import dev.notkili.aoc.shared.input.IntInput;
import dev.notkili.aoc.shared.input.MapInput2D;
import dev.notkili.aoc.shared.misc.collections.list.List;
import dev.notkili.aoc.shared.misc.collections.set.Set;
import dev.notkili.aoc.shared.misc.position.Direction;
import dev.notkili.aoc.shared.misc.position.Int2DPos;
import dev.notkili.aoc.shared.misc.tuple.Tuple;
import dev.notkili.aoc.shared.parse.InputParser;

import java.util.LinkedList;

public class Day12 {
    public static void main(String[] args) {
        System.out.println("Solution 1:");

        new InputParser(2024, 12).getInput().ifPresent(strInput -> {
            var map = strInput.charMap();
            
            var visited = new Set<Int2DPos>();
            int sum = 0;
            
            for (var pos : map.positions()) {
                if (visited.contains(pos)) continue;
                
                sum += calculateRegionSize1(visited, map, pos);
            }
            
            new IntInput(sum).solution().print();
        });
        
        System.out.println("Solution 2:");

        new InputParser(2024, 12).getInput().ifPresent(strInput -> {
            var map = strInput.charMap();

            var visited = new Set<Int2DPos>();
            int sum = 0;

            for (var pos : map.positions()) {
                if (visited.contains(pos)) continue;
                
                sum += calculateRegionSize2(visited, map, pos);
            }

            new IntInput(sum).solution().print();
        });
    }
    
    private static int calculateRegionSize1(Set<Int2DPos> visited, MapInput2D<CharInput> map, Int2DPos pos) {
        var queue = new LinkedList<Int2DPos>();
        var self = map.get(pos);
        var tempBounds = 0;
        var perim = 0;

        queue.add(pos);

        while (!queue.isEmpty()) {
            var pop = queue.pop();
            
            if (visited.contains(pop)) continue;
            
            visited.add(pop);
            perim++;
            
            var adj = new List<Int2DPos>();
            for (var neigh : pop.getNeighbours(Direction.CARD)) {
                if (!map.contains(neigh)) continue;
                
                if (!map.get(neigh).equals(self)) continue;
                
                adj.add(neigh);
            }
            
            tempBounds += 4 - adj.size();
            adj.forEach(queue::add);
        }
        
        return tempBounds * perim;
    }

    private static int calculateRegionSize2(Set<Int2DPos> visited, MapInput2D<CharInput> map, Int2DPos pos) {
        var queue = new LinkedList<Int2DPos>();
        var self = map.get(pos);

        queue.add(pos);
        var region = new Set<Int2DPos>();

        while (!queue.isEmpty()) {
            var pop = queue.pop();

            if (visited.contains(pop)) continue;

            visited.add(pop);
            region.add(pop);

            var adj = new List<Int2DPos>();
            for (var neigh : pop.getNeighbours(Direction.CARD)) {
                if (!map.contains(neigh)) continue;

                if (!map.get(neigh).equals(self)) continue;

                adj.add(neigh);
            }

            adj.forEach(queue::add);
        }

        return region.size() * calculateUniqueSides(map, region);
    }
    
    private static int calculateUniqueSides(MapInput2D<CharInput> map, Set<Int2DPos> area) {
        var queue = new LinkedList<Tuple<Int2DPos, Direction>>();
        
        for (var pos : area) {
            var self = map.get(pos);
            
            for (var dir : Direction.CARD) {
                var neigh = pos.add(dir);
                
                if (!map.contains(neigh)) {
                    queue.add(Tuple.of(pos, dir));
                }

                if (map.contains(neigh) && !map.get(neigh).equals(self)) {
                    queue.add(Tuple.of(pos, dir));
                }
            }
        }
        
        var sides = 0;
        // Visited contains "Fence" & "Side of fence", as one slot can have at most 4 fences on it
        var visited = new Set<Tuple<Int2DPos, Direction>>();
        
        while (!queue.isEmpty()) {
            var pop = queue.pop();
            
            var pos = pop.getA();
            var fromDir = pop.getB();
            var fence = Tuple.of(pos.add(fromDir), fromDir);
            
            if (visited.contains(fence)) {
                continue;
            }

            visited.add(fence);
            sides++;
            
            var self = map.get(pos);
            var dirR90 = fromDir.nextClockwise(false);
            var dirL90 = fromDir.nextCounterClockwise(false);

            for (var direc : List.of(dirR90, dirL90)) {
                var delta = 1;
                
                while (true) {
                    Int2DPos adj = pos.add(direc, delta);
                    Int2DPos fencePos = adj.add(fromDir);
                    
                    if (!map.contains(adj))
                        break;
                    
                    if (!map.get(adj).equals(self))
                        break;
                    
                    if (map.contains(fencePos) && map.get(fencePos).equals(self))
                        break;
                    
                    visited.add(Tuple.of(fencePos, fromDir));
                    delta++;
                }
            }
        }
        
        return sides;
    }
}
