package dev.notkili.aoc.solutions.twenty_four;

import dev.notkili.aoc.shared.input.LongInput;
import dev.notkili.aoc.shared.misc.tuple.Tuple;
import dev.notkili.aoc.shared.parse.InputParser;

public class Day13 {
    public static void main(String[] args) {
        System.out.println("Solution 1:");

        new InputParser(2024, 13).getInput().ifPresent(strInput -> {
            var inp = strInput.split("\n\n");
            
            var sum = 0L;
            
            for (var game : inp) {
                var a = game.split("\n").get(0).split(": ").get(1).tuple(", ").map(str1 -> str1.numbers().get(0).asLong(), str2 -> str2.numbers().get(0).asLong());
                var b = game.split("\n").get(1).split(": ").get(1).tuple(", ").map(str1 -> str1.numbers().get(0).asLong(), str2 -> str2.numbers().get(0).asLong());
                var price = game.split("\n").get(2).split(": ").get(1).tuple(", ").map(str1 -> str1.numbers().get(0).asLong(), str2 -> str2.numbers().get(0).asLong());
                
                var mk = solve(price, a, b);
                
                if (mk == null) {
                    continue;
                }

                sum += mk.getA() * 3L;
                sum += mk.getB();
            }
            
            new LongInput(sum).solution().print();
        });
        
        System.out.println("Solution 2:");
        
        new InputParser(2024, 13).getInput().ifPresent(strInput -> {
            var inp = strInput.split("\n\n");

            var sum = 0L;

            for (var game : inp) {
                var delta = 10000000000000L;
                var a = game.split("\n").get(0).split(": ").get(1).tuple(", ").map(str1 -> str1.numbers().get(0).asLong(), str2 -> str2.numbers().get(0).asLong());
                var b = game.split("\n").get(1).split(": ").get(1).tuple(", ").map(str1 -> str1.numbers().get(0).asLong(), str2 -> str2.numbers().get(0).asLong());
                var price = game.split("\n").get(2).split(": ").get(1).tuple(", ").map(str1 -> delta + str1.numbers().get(0).asLong() , str2 -> delta + str2.numbers().get(0).asLong());

                var mk = solve(price, a, b);

                if (mk == null) {
                    continue;
                }
                
                sum += mk.getA() * 3L;
                sum += mk.getB();
            }

            new LongInput(sum).solution().print();
        });
    }

    // Ref: https://en.wikipedia.org/wiki/Cramer%27s_rule
    public static Tuple<Long, Long> solve(Tuple<Long, Long> result, Tuple<Long, Long> a, Tuple<Long, Long> b) {
        var x = result.getA();
        var y = result.getB();
        var x0 = a.getA();
        var y0 = a.getB();
        var x1 = b.getA();
        var y1 = b.getB();

        var det = x0 * y1 - y0 * x1;

        if (det == 0) return null;

        var mNum = y1 * x - x1 * y;
        var kNum = x0 * y - y0 * x;

        if (mNum % det != 0 || kNum % det != 0) return null;
        
        return new Tuple<>(mNum / det, kNum / det);
    }
}
