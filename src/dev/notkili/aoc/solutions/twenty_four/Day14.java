package dev.notkili.aoc.solutions.twenty_four;

import dev.notkili.aoc.shared.input.IntInput;
import dev.notkili.aoc.shared.misc.collections.list.List;
import dev.notkili.aoc.shared.misc.position.Int2DPos;
import dev.notkili.aoc.shared.parse.InputParser;

import java.util.HashMap;

public class Day14 {
    public static void main(String[] args) {
        System.out.println("Solution 1:");

        new InputParser(2024, 14).getInput().ifPresent(strInput -> {
            var nums = strInput.split("\n").mapTo(str -> str.numbers().asList()).asList();
            
            var map = new HashMap<Int2DPos, List<Int2DPos>>();
            
            for (var num : nums) {
                map.computeIfAbsent(new Int2DPos(num.get(0), num.get(1)), k -> List.of()).add(new Int2DPos(num.get(2), num.get(3)));
            }
            
            for (int i = 0; i < 100; i++) {
                map = sim(map, 101, 103);
            }
            
            new IntInput(eval(map, 101, 103)).solution().print();
        });
        
        System.out.println("Solution 2:");

        new InputParser(2024, 14).getInput().ifPresent(strInput -> {
            var nums = strInput.split("\n").mapTo(str -> str.numbers().asList()).asList();

            var map = new HashMap<Int2DPos, List<Int2DPos>>();

            for (var num : nums) {
                map.computeIfAbsent(new Int2DPos(num.get(0), num.get(1)), k -> List.of()).add(new Int2DPos(num.get(2), num.get(3)));
            }

            int i = 0;
            
            while (true) {
                if (isTree(map, 101, 103)) {
                    break;
                }
                map = sim(map, 101, 103);
                
                i++;
            }

            new IntInput(i).solution().print();
        });
    }
    
    private static void print(HashMap<Int2DPos, List<Int2DPos>> robots, int xMax, int yMax, boolean asHash) {
        for (int y = 18 - 5; y < 51 + 5; y++) {
            for (int x = 0; x < xMax; x++) {
                var robot = robots.get(new Int2DPos(x, y));
                
                System.out.print(robot == null ? "." : asHash ? "#" : robot.size() + "");
            }
            System.out.println();
        }
    }
    
    // Based off of inspecting the generated output for the first 20k seconds, it seems like a christmas tree picture implies having at most 1 robot at each position
    private static boolean isTree(HashMap<Int2DPos, List<Int2DPos>> robots, int xMax, int yMax) {
        for (var robot : robots.values()) {
            if (robot.size() > 1) {
                return false;
            }
        }
        
        return true;
        
        // return count >= 32;
    }
    
    private static int eval(HashMap<Int2DPos, List<Int2DPos>> robots, int xMax, int yMax) {
        int q1 = 0, q2 = 0, q3 = 0, q4 = 0;
        
        for (int y = 0; y < yMax; y++) {
            for (int x = 0; x < xMax; x++) {
                var robot = robots.get(new Int2DPos(x, y));
                
                if (robot == null)
                    continue;
                
                // Q1
                if (y < yMax / 2 && x < xMax / 2) {
                    q1 += robot.size();
                } else if (y < yMax / 2 && x > xMax / 2) {
                    q2 += robot.size();
                } else if (y > yMax / 2 && x < xMax / 2) {
                    q3 += robot.size();
                } else if (y > yMax / 2 && x > xMax / 2) {
                    q4 += robot.size();
                }
            }
        }
        
        return q1 * q2 * q3 * q4;
    }
    
    private static HashMap<Int2DPos, List<Int2DPos>> sim(HashMap<Int2DPos, List<Int2DPos>> robots, int xMax, int yMax) {
        var newMap = new HashMap<Int2DPos, List<Int2DPos>>();
        
        for (var key : robots.keySet()) {
            for (var robot : robots.get(key)) {
                var newPos = key.add(robot);
                
                var fX = newPos.getX();
                var fY = newPos.getY();
                
                if (fX < 0) {
                    fX = xMax + fX;
                } else if (fX >= xMax) {
                    fX = fX % xMax;
                }

                if (fY < 0) {
                    fY = yMax + fY;
                } else if (fY >= yMax) {
                    fY = fY % yMax;
                }
                
                newMap.computeIfAbsent(new Int2DPos(fX, fY), k -> List.of()).add(robot);
            }
        }
        
        return newMap;
    }
}
