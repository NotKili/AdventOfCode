package dev.notkili.aoc.solutions.twenty_three;

import dev.notkili.aoc.shared.input.IntInput;
import dev.notkili.aoc.shared.input.StringInput;
import dev.notkili.aoc.shared.parse.InputParser;

import java.util.HashMap;

public class Day2 {
    public static void main(String[] args) {
        new InputParser(2023, 2)
                .getInput().ifPresent(input -> {
                    var map = new HashMap<String, Integer>();
                    map.put("red", 12);
                    map.put("green", 13);
                    map.put("blue", 14);

                    input.splitAt("\n").mapToInt(line -> {
                        var idGames = line.asString().split(": ");
                        var id = new StringInput(idGames[0].split(" ")[1]).asInt();
                        var revealed = idGames[1].split("; ");

                        for (var set : revealed) {
                            var colors = set.split(", ");

                            for (var color : colors) {
                                var cid = color.split(" ");
                                var c = cid[1];
                                var num = new StringInput(cid[0]).asInt();

                                if (map.get(c) < num.asInt()) {
                                    return new IntInput(0);
                                }
                            }
                        }

                        return id;
                    })
                            .sum()
                            .asSolution()
                            .print();//.submit(2023, 2, 1);
                });


        new InputParser(2023, 2)
                .getInput().ifPresent(input -> {
                    input.splitAt("\n").mapToInt(line -> {
                        var idGames = line.asString().split(": ");
                        var revealed = idGames[1].split("; ");

                        HashMap<String, IntInput> cmin = new HashMap<>();

                        for (var set : revealed) {
                            var colors = set.split(", ");

                            for (var color : colors) {
                                var cid = color.split(" ");
                                var c = cid[1];
                                var num = new StringInput(cid[0]).asInt();

                                cmin.compute(c, (k, v) -> v == null ? num : v.max(num));
                            }
                        }

                        return cmin.values().stream().reduce(IntInput::multiply).get();
                    })
                            .sum()
                            .asSolution()
                            .print();//.submit(2023, 2, 2);
                });
    }
}
