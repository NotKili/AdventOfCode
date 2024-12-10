package dev.notkili.aoc.solutions.twenty_four;

import dev.notkili.aoc.shared.input.CharInput;
import dev.notkili.aoc.shared.input.IntInput;
import dev.notkili.aoc.shared.misc.collections.set.Set;
import dev.notkili.aoc.shared.misc.position.Direction;
import dev.notkili.aoc.shared.misc.position.Int2DPos;
import dev.notkili.aoc.shared.misc.tuple.Tuple;
import dev.notkili.aoc.shared.parse.InputParser;

import java.util.LinkedList;

public class Day10 {
    public static void main(String[] args) {
        System.out.println("Solution 1:");

        new InputParser(2024, 10).getInput().ifPresent(strInput -> {
            var topography = strInput.charMap();
            IntInput headScore = IntInput.zero();
            
            for (var pos0 : topography.positions()) {
                if (!topography.get(pos0).equals("0"))
                    continue;
                
                // CurrentHeight, Position
                var queue = new LinkedList<Tuple<CharInput, Int2DPos>>();
                queue.add(Tuple.of(topography.get(pos0), pos0));
                
                int heads = 0;
                var visited = new Set<Int2DPos>();
                
                while (!queue.isEmpty()) {
                    var pop = queue.pop();
                    
                    if (pop.getA().equals("9")) {
                        if (!visited.contains(pop.getB()))
                            heads += 1;
                        visited.add(pop.getB());
                        continue;
                    }
                    
                    for (var neigh : pop.getB().getNeighbours(Direction.CARD)) {
                        if (!topography.contains(neigh))
                            continue;
                        
                        if (topography.get(neigh).asInt() - topography.get(pop.getB()).asInt() != 1) {
                            continue;
                        }
                        
                        queue.add(Tuple.of(topography.get(neigh), neigh));
                    }
                }

                headScore = headScore.add(heads);
            }
            
            headScore.solution().print();
        });

        System.out.println("Solution 2:");
        
        new InputParser(2024, 10).getInput().ifPresent(strInput -> {
            var topography = strInput.charMap();
            IntInput rating = IntInput.zero();

            for (var pos0 : topography.positions()) {
                if (!topography.get(pos0).equals("0"))
                    continue;

                var queue = new LinkedList<Tuple<CharInput, Int2DPos>>();
                queue.add(Tuple.of(topography.get(pos0), pos0));

                int headRating = 0;

                while (!queue.isEmpty()) {
                    var pop = queue.pop();

                    if (pop.getA().equals("9")) {
                        headRating += 1;
                        continue;
                    }

                    for (var neigh : pop.getB().getNeighbours(Direction.CARD)) {
                        if (!topography.contains(neigh))
                            continue;

                        if (topography.get(neigh).asInt() - topography.get(pop.getB()).asInt() != 1) {
                            continue;
                        }

                        queue.add(Tuple.of(topography.get(neigh), neigh));
                    }
                }

                rating = rating.add(headRating);
            }
            
            rating.solution().print();
        });
    }
    
    
}
