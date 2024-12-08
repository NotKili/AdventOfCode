package dev.notkili.aoc.solutions.twenty_four;

import dev.notkili.aoc.shared.input.CharInput;
import dev.notkili.aoc.shared.input.IntInput;
import dev.notkili.aoc.shared.misc.collections.list.List;
import dev.notkili.aoc.shared.misc.position.Int2DPos;
import dev.notkili.aoc.shared.parse.InputParser;

import java.util.HashMap;
import java.util.HashSet;

public class Day8 {
    public static void main(String[] args) {
        System.out.println("Solution 1:");
        
        new InputParser(2024, 8).getInput().ifPresent(strInput -> {
            var map = strInput.charMap();
            var antennasByChar = new HashMap<CharInput, List<Int2DPos>>();
            
            for (var pos : map.positions()) {
                if (map.get(pos).equals("."))
                    continue;
                
                antennasByChar.computeIfAbsent(map.get(pos), c -> new List<>()).add(pos);
            }
            
            var antiNodes = new HashSet<Int2DPos>();
            
            for (var pos : map.positions()) {
                if (map.get(pos).equals("."))
                    continue;
                
                var equal = antennasByChar.get(map.get(pos));
                
                for (var eqPos : equal) {
                    if (pos.equals(eqPos)) {
                        continue;
                    }
                    
                    var xD = pos.getX() - eqPos.getX();
                    var yD = pos.getY() - eqPos.getY();
                    
                    var antiNodePos = pos.add(xD, yD);
                    
                    if (map.contains(antiNodePos)) {
                        antiNodes.add(antiNodePos);
                    }
                }
            }

            // new IntInput(antiNodes.size()).solution().submit(2024, 8, 1);
            new IntInput(antiNodes.size()).solution().print();
        });

        System.out.println("Solution 2:");

        new InputParser(2024, 8).getInput().ifPresent(strInput -> {
            var map = strInput.charMap();
            var antennasByChar = new HashMap<CharInput, List<Int2DPos>>();

            for (var pos : map.positions()) {
                if (map.get(pos).equals("."))
                    continue;

                antennasByChar.computeIfAbsent(map.get(pos), c -> new List<>()).add(pos);
            }

            var antiNodes = new HashSet<Int2DPos>();

            for (var pos : map.positions()) {
                if (map.get(pos).equals("."))
                    continue;

                var equal = antennasByChar.get(map.get(pos));

                for (var eqPos : equal) {
                    if (pos.equals(eqPos)) {
                        continue;
                    }

                    var xD = pos.getX() - eqPos.getX();
                    var yD = pos.getY() - eqPos.getY();
                    
                    for (var p : List.of(pos, eqPos)) {
                        var antiNodePos = p.add(xD, yD);

                        while (map.contains(antiNodePos)) {
                            antiNodes.add(antiNodePos);
                            antiNodePos = antiNodePos.add(xD, yD);
                        }
                    }
                }
            }
            new IntInput(antiNodes.size()).solution().print();
            // new IntInput(antiNodes.size()).solution().submit(2024, 8, 2);
        });
    }
}
