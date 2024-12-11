package dev.notkili.aoc.solutions.twenty_four;

import dev.notkili.aoc.shared.input.IntInput;
import dev.notkili.aoc.shared.input.LongInput;
import dev.notkili.aoc.shared.input.StringInput;
import dev.notkili.aoc.shared.misc.BigCount;
import dev.notkili.aoc.shared.misc.Count;
import dev.notkili.aoc.shared.parse.InputParser;

import java.math.BigInteger;
import java.util.HashMap;

public class Day11 {
    public static void main(String[] args) {
        System.out.println("Solution 1:");
        
        new InputParser(2024, 11).getInput().ifPresent(strInput -> {
            var in = strInput.split(" ").mapTo(StringInput::longInteger).mapTo(LongInput::asLong).asList();
            
            var counts = new HashMap<Long, Count>();
            
            for (var i : in) {
                counts.computeIfAbsent(i, k -> new Count(0)).increment();
            }
            
            for (int i = 0; i < 25; i++) {
                var newCounts = new HashMap<Long, Count>();
                
                for (var l : counts.keySet()) {
                    splitNum(counts, newCounts, l);
                }
                
                counts = newCounts;
            }

            new IntInput(counts.values().stream().map(Count::asInt).reduce(Integer::sum).get()).solution().print();
        });
        
        System.out.println("Solution 2:");

        new InputParser(2024, 11).getInput().ifPresent(strInput -> {
            var in = strInput.split(" ").mapTo(StringInput::longInteger).mapTo(LongInput::asLong).asList();

            var counts = new HashMap<Long, BigCount>();

            for (var i : in) {
                counts.computeIfAbsent(i, k -> new BigCount(0)).increment();
            }

            for (int i = 0; i < 75; i++) {
                var newCounts = new HashMap<Long, BigCount>();

                for (var l : counts.keySet()) {
                    splitNumBig(counts, newCounts, l);
                }

                counts = newCounts;
            }

            new StringInput(counts.values().stream().map(BigCount::getCount).reduce(BigInteger::add).get().toString()).solution().print();
        });
    }
    
    private static void splitNum(HashMap<Long, Count> oldCounts, HashMap<Long, Count> newCounts, Long l) {
        var lStr = l.toString();
        
        if (l == 0) {
            newCounts.computeIfAbsent(1L, k -> new Count(0)).increment(oldCounts.getOrDefault(l, new Count(0)));
        } else if (lStr.length() % 2 == 0) {
            var l1 = new LongInput(lStr.substring(0, lStr.length() / 2)).asLong();
            var l2 = new LongInput(lStr.substring(lStr.length() / 2)).asLong();
            newCounts.computeIfAbsent(l1, k -> new Count(0)).increment(oldCounts.getOrDefault(l, new Count(0)));
            newCounts.computeIfAbsent(l2, k -> new Count(0)).increment(oldCounts.getOrDefault(l, new Count(0)));
        } else {
            newCounts.computeIfAbsent(l * 2024, k -> new Count(0)).increment(oldCounts.getOrDefault(l, new Count(0)));
        }
    }

    private static void splitNumBig(HashMap<Long, BigCount> oldCounts, HashMap<Long, BigCount> newCounts, Long l) {
        var lStr = l.toString();

        if (l == 0) {
            newCounts.computeIfAbsent(1L, k -> new BigCount(0)).increment(oldCounts.getOrDefault(l, new BigCount(0)));
        } else if (lStr.length() % 2 == 0) {
            var l1 = new LongInput(lStr.substring(0, lStr.length() / 2)).asLong();
            var l2 = new LongInput(lStr.substring(lStr.length() / 2)).asLong();
            newCounts.computeIfAbsent(l1, k -> new BigCount(0)).increment(oldCounts.getOrDefault(l, new BigCount(0)));
            newCounts.computeIfAbsent(l2, k -> new BigCount(0)).increment(oldCounts.getOrDefault(l, new BigCount(0)));
        } else {
            newCounts.computeIfAbsent(l * 2024, k -> new BigCount(0)).increment(oldCounts.getOrDefault(l, new BigCount(0)));
        }
    }
}
