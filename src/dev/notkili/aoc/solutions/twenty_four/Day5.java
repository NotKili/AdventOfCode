package dev.notkili.aoc.solutions.twenty_four;

import dev.notkili.aoc.shared.input.IntInput;
import dev.notkili.aoc.shared.input.ListInput;
import dev.notkili.aoc.shared.input.StringInput;
import dev.notkili.aoc.shared.misc.collections.set.Set;
import dev.notkili.aoc.shared.misc.tuple.Tuple;
import dev.notkili.aoc.shared.parse.InputParser;

import java.util.HashMap;

public class Day5 {
    public static void main(String[] args) {
        System.out.println("Solution 1:");

        new InputParser(2024, 5).getInput().ifPresent(strInput -> {
            var mustBeBefore = new HashMap<IntInput, Set<IntInput>>();
            
            strInput.split("\n\n").get(0).split("\n").mapTo(t -> t.tuple("\\|")).mapTo(t -> Tuple.of(t.getA().integer(), t.getB().integer())).forEach(t -> {
                mustBeBefore.computeIfAbsent(t.getB(), x -> new Set<>()).add(t.getA());
            });
            
            strInput.split("\n\n").get(1).split("\n").splitAt(",").mapTo(update -> {
                if (!isValidUpdate(mustBeBefore, update)) {
                    return IntInput.zero();
                }
                
                return update.get(update.size() / 2).integer();
            }).reduce(IntInput::add).orElseThrow().solution().print();
        });

        System.out.println("\nSolution 2:");

        new InputParser(2024, 5).getInput().ifPresent(strInput -> {
            var mustBeBefore = new HashMap<IntInput, Set<IntInput>>();

            strInput.split("\n\n").get(0).split("\n").mapTo(t -> t.tuple("\\|")).mapTo(t -> Tuple.of(t.getA().integer(), t.getB().integer())).forEach(t -> {
                mustBeBefore.computeIfAbsent(t.getB(), x -> new Set<>()).add(t.getA());
            });

            strInput.split("\n\n").get(1).split("\n").splitAt(",").mapTo(update -> {
                if (isValidUpdate(mustBeBefore, update)) {
                    return IntInput.zero();
                }

                return validateUpdate(mustBeBefore, update).get(update.size() / 2).integer();
            }).reduce(IntInput::add).orElseThrow().solution().print();
        });
    }
    
    public static boolean isValidUpdate(HashMap<IntInput, Set<IntInput>> order, ListInput.StringListInput l) {
        var notVisited = new Set<IntInput>();
        
        for (int x = 0; x < l.size(); x++) {
            notVisited.add(l.get(x).integer());
        }
        
        for (int x = 0; x < l.size(); x++) {
            var asI = l.get(x).integer();
            
            notVisited.remove(asI);
            
            if (order.getOrDefault(asI, new Set<>()).containsAny(notVisited)) {
                return false;
            }
        }
        
        return true;
    }

    public static ListInput<StringInput> validateUpdate(HashMap<IntInput, Set<IntInput>> order, ListInput.StringListInput l) {
        // Heavily dependent on the sorting algorithm used, QS for example will not work the same way. Naive "bubble sort" like approach works aswell with sifting down invalid orders
        
        /* Old Solution
            var visited = new Set<IntInput>();
            
            for (int x = 0; x < l.size(); x++) {
                var asI = l.get(x).integer();
    
                visited.add(asI);
    
                if (order.getOrDefault(asI, new Set<>()).containsAny(visited)) {
                    var tmp = l.get(x - 1);
                    l.replace(x - 1, l.get(x));
                    l.replace(x, tmp);
                    validateUpdate(order, l);
                }
            }
            
            return l;
         */
        
        return l.sort((a, b) -> {
            var ai = a.integer();
            var bi = b.integer();

            if (!order.containsKey(bi) || !order.get(bi).contains(ai)) {
                return 0;
            }

            return -1;
        });
    }
}
