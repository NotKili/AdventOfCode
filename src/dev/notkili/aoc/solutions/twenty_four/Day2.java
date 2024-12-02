package dev.notkili.aoc.solutions.twenty_four;

import dev.notkili.aoc.shared.input.IntInput;
import dev.notkili.aoc.shared.input.StringInput;
import dev.notkili.aoc.shared.parse.InputParser;

import java.util.ArrayList;
import java.util.List;

public class Day2 {
    public static void main(String[] args) {
        System.out.println("Solution 1:");
        
        new InputParser(2024, 2).getInput().ifPresent(strInput -> {
            strInput.splitAt("\n").mapTo(str -> str.splitAt(" ")).mapTo(report -> report.mapTo(StringInput::asInt).asList()).mapTo(Day2::safe).mapTo(b -> b ? 1 : 0).reduce(Integer::sum).ifPresent(System.out::println);
        });
        
        System.out.println("\nSolution 2:");
        
        new InputParser(2024, 2).getInput().ifPresent(strInput -> {
            strInput.splitAt("\n").mapTo(str -> str.splitAt(" ")).mapTo(report -> report.mapTo(StringInput::asInt).asList()).mapTo(report -> safeRem(report)).mapTo(b -> b ? 1 : 0).reduce(Integer::sum).ifPresent(System.out::println);
        });
    }
    
    private static boolean safe(List<IntInput> in) {
        if (in.size() == 1) {
            return true;
        }
        
        var dir = in.get(0).asInt() < in.get(1).asInt();
        
        for (int i = 0; i < in.size() - 1; i++) {
            if (dir) {
                if (in.get(i).asInt() >= in.get(i + 1).asInt()) {
                    return false;
                }
                
                if (in.get(i + 1).asInt() - in.get(i).asInt() > 3) {
                    return false;
                }
            } else {
                if (in.get(i).asInt() <= in.get(i + 1).asInt()) {
                    return false;
                }
                
                if (in.get(i).asInt() - in.get(i + 1).asInt() > 3) {
                    return false;
                }
            }
        }
        
        return true;
    }

    private static boolean dir(List<IntInput> in) {
        var a = 0;
        var b = 0;
        
        for (int i = 0; i < in.size() - 1; i++) {
            if (in.get(i).asInt() < in.get(i + 1).asInt()) {
                a++;
            } else {
                b++;
            }
        }
        
        return a > b;
    }
    
    private static boolean safeRem(List<IntInput> in) {
        if (safe(in)) {
            return true;
        }
        
        for (int i = 0; i < in.size(); i++) {
            var cpy = new ArrayList<>(in);
            
            cpy.remove(i);
            
            if (safe(cpy)) {
                return true;
            }
        }
        
        return false;
    }
}
