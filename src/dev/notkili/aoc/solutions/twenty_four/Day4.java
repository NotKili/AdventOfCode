package dev.notkili.aoc.solutions.twenty_four;

import dev.notkili.aoc.shared.input.*;
import dev.notkili.aoc.shared.misc.collections.list.List;
import dev.notkili.aoc.shared.misc.position.Int2DPos;
import dev.notkili.aoc.shared.parse.InputParser;

import java.util.HashMap;

public class Day4 {
    public static void main(String[] args) {
        System.out.println("Solution 1:");
        
        new InputParser(2024, 4).getInput().ifPresent(strInput -> {
            var asArray = ArrayInput2D.parse(strInput);
            
            var map = new HashMap<Int2DPos, CharInput>();
            
            for (int y = 0; y < asArray.getHeight(); y++) {
                for (int x = 0; x < asArray.getWidth(); x++) {
                    map.put(new Int2DPos(x, y), asArray.get(x, y));
                }
            }
            
            int sum = 0;
            
            for (int y = 0; y < asArray.getHeight(); y++) {
                for (int x = 0; x < asArray.getWidth(); x++) {
                    if (asArray.get(x, y).equals(new CharInput('X'))) {
                        sum += findXMAS(map, y, x);
                    }
                }
            }
            
            new IntInput(sum).solution().print();
        });
        
        System.out.println("\nSolution 2:");
        
        new InputParser(2024, 4).getInput().ifPresent(strInput -> {
            var asArray = ArrayInput2D.parse(strInput);

            var map = new HashMap<Int2DPos, CharInput>();

            for (int y = 0; y < asArray.getHeight(); y++) {
                for (int x = 0; x < asArray.getWidth(); x++) {
                    map.put(new Int2DPos(x, y), asArray.get(x, y));
                }
            }

            int sum = 0;

            for (int y = 0; y < asArray.getHeight(); y++) {
                for (int x = 0; x < asArray.getWidth(); x++) {
                    if (asArray.get(x, y).equals(new CharInput('A'))) {
                        sum += findMas(map, y, x);
                    }
                }
            }

            // new IntInput(sum).solution().submit(2024, 4, 2);
            new IntInput(sum).print();
        });
    }
    
    private static int findXMAS(HashMap<Int2DPos, CharInput> map, int y, int x) {
        int count = 0;
        StringBuilder builder = new StringBuilder();

        for (var dxdy : List.of(new int[] {0, 1}, new int[] {0, -1}, new int[] {1, 0}, new int[] {-1, 0}, new int[] {1, 1}, new int[] {1, -1}, new int[] {-1, 1}, new int[] {-1, -1})) {
            for (int d = 0; d < 4; d++) {
                if (!map.containsKey(new Int2DPos(x + dxdy[0] * d, y + dxdy[1] * d))) {
                    break;
                }

                builder.append(map.get(new Int2DPos(x + dxdy[0] * d, y + dxdy[1] * d)).asChar());
            }

            if (builder.toString().equals("XMAS")) {
                count++;
            }
            builder = new StringBuilder();
        }
        
        return count;
    }
    
    private static int findMas(HashMap<Int2DPos, CharInput> map, int y, int x) {
        for (var dxdy : List.of(new Int2DPos[] {new Int2DPos(x + 1, y + 1), new Int2DPos(x - 1, y - 1), }, new Int2DPos[] {new Int2DPos(x + 1, y - 1), new Int2DPos(x - 1, y + 1)})) {
            if (!map.containsKey(dxdy[0]) || !map.containsKey(dxdy[1])) {
                return 0;
            }
            
            if (!((map.get(dxdy[0]).equals(new CharInput('M')) && map.get(dxdy[1]).equals(new CharInput('S'))) || (map.get(dxdy[0]).equals(new CharInput('S')) && map.get(dxdy[1]).equals(new CharInput('M'))))) {
                return 0;
            }
        }
        
        return 1;
    }
}
