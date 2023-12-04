package dev.notkili.aoc.solutions.twenty_three;

import dev.notkili.aoc.shared.input.IntInput;
import dev.notkili.aoc.shared.misc.Count;
import dev.notkili.aoc.shared.misc.collections.Set;
import dev.notkili.aoc.shared.parse.InputParser;

import java.util.HashMap;
import java.util.NoSuchElementException;


public class Day4 {
    public static void main(String[] args) {
        new InputParser(2023, 4).getInput().ifPresent(input -> {
            input.splitAt("\n").mapToInt(card -> {
                var result = card.asTuple(": ");

                var winningAndMine = result.getB().asTuple(" \\| ");
                var winning = winningAndMine.getA();
                var mine = winningAndMine.getB();

                Set<IntInput> winningNums = new Set<>();

                try {
                    int i = 0;
                    while (true) {
                        winningNums.add(winning.findFirstNthNumber(i));
                        i++;
                    }
                } catch (NoSuchElementException ignored) {
                    // No more numbers
                }

                Set<IntInput> myNums = new Set<>();

                try {
                    int i = 0;
                    while (true) {
                        myNums.add(mine.findFirstNthNumber(i));
                        i++;
                    }
                } catch (NoSuchElementException ignored) {
                    // No more numbers
                }

                var intersect = myNums.intersection(winningNums);

                if (intersect.size() == 0) {
                    return new IntInput(0);
                } else {
                    return new IntInput((int) Math.pow(2, intersect.size() - 1));
                }
            }).sum().asSolution().print();//.submit(2023, 4, 1);
        });


        new InputParser(2023, 4).getInput().ifPresent(input -> {
            HashMap<IntInput, IntInput> winningCards = new HashMap<>();
            HashMap<IntInput, Count> map = new HashMap<>();

            input.splitAt("\n").forEach(card -> {
                var result = card.asTuple(": ");

                var gameId = result.getA().findFirstNthNumber(0);
                var winningAndMine = result.getB().asTuple(" \\| ");
                var winning = winningAndMine.getA();
                var mine = winningAndMine.getB();

                Set<IntInput> winningNums = new Set<>();

                try {
                    int i = 0;
                    while (true) {
                        winningNums.add(winning.findFirstNthNumber(i));
                        i++;
                    }
                } catch (NoSuchElementException ignored) {

                }

                Set<IntInput> myNums = new Set<>();

                try {
                    int i = 0;
                    while (true) {
                        myNums.add(mine.findFirstNthNumber(i));
                        i++;
                    }
                } catch (NoSuchElementException ignored) {

                }

                var winningCardsAmount = myNums.intersection(winningNums).size();
                winningCards.put(gameId, new IntInput(winningCardsAmount));
                map.put(gameId, new Count(1));
            });

            var maxId = winningCards.keySet().stream().max(IntInput::compareTo).get();

            for (int i = 1; i <= maxId.asInt(); i++) {
                var winningNumbers = winningCards.get(new IntInput(i));
                var cardCount = map.get(new IntInput(i));

                for (int j = 1; j <= winningNumbers.asInt(); j++) {
                    map.get(new IntInput(i + j)).increment(cardCount.asInt());
                }
            }

            map.values().stream().reduce(Count::increment).get().asLongInput().asSolution().print(); //.submit(2023, 4, 2);
        });
    }
}
