package dev.notkili.aoc.solutions.twenty_four;

import dev.notkili.aoc.shared.input.*;
import dev.notkili.aoc.shared.misc.collections.list.List;
import dev.notkili.aoc.shared.misc.position.Direction;
import dev.notkili.aoc.shared.misc.position.Int2DPos;
import dev.notkili.aoc.shared.misc.tuple.Tuple;
import dev.notkili.aoc.shared.parse.InputParser;

public class Day4 {
    public static void main(String[] args) {
        System.out.println("Solution 1:");
        
        new InputParser(2024, 4).getInput().ifPresent(strInput -> {
            var map = MapInput2D.parse(strInput);
            
            IntInput sum = IntInput.zero();
            
            for (var pos : map.positions()) {
                if (map.get(pos).equals('X')) {
                    sum = sum.add(findXMAS(map, pos));
                }
            }
            
            sum.solution().print();
        });
        
        System.out.println("\nSolution 2:");
        
        new InputParser(2024, 4).getInput().ifPresent(strInput -> {
            var map = MapInput2D.parse(strInput);
            
            IntInput sum = IntInput.zero();

            for (var pos : map.positions()) {
                if (map.get(pos).equals('A')) {
                    sum = sum.add(findMas(map, pos));
                }
            }

            // new IntInput(sum).solution().submit(2024, 4, 2);
            new IntInput(sum).print();
        });
    }
    
    private static int findXMAS(MapInput2D<CharInput> map, Int2DPos pos) {
        int count = 0;
        StringBuilder builder = new StringBuilder();

        for (var dir : Direction.ALL) {
            for (int d = 0; d < 4; d++) {
                if (!map.contains(pos.add(dir, d))) {
                    break;
                }

                builder.append(map.get(pos.add(dir, d)).asChar());
            }

            if (builder.toString().equals("XMAS")) {
                count++;
            }
            builder = new StringBuilder();
        }
        
        return count;
    }
    
    private static int findMas(MapInput2D<CharInput> map, Int2DPos pos) {
        for (var dirs : List.of(Tuple.of(Direction.NE, Direction.SW), Tuple.of(Direction.NW, Direction.SE))) {
            if (!map.contains(pos.add(dirs.getA())) || !map.contains(pos.add(dirs.getB()))) {
                return 0;
            }
            
            if (!(
                        map.get(pos.add(dirs.getA())).equals('M') && map.get(pos.add(dirs.getB())).equals('S')
                            ||
                        map.get(pos.add(dirs.getA())).equals('S') && map.get(pos.add(dirs.getB())).equals('M')
                    )) {
                return 0;
            }
        }
        
        return 1;
    }
}
