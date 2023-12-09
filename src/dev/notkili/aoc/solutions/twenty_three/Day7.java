package dev.notkili.aoc.solutions.twenty_three;

import dev.notkili.aoc.shared.input.LongInput;
import dev.notkili.aoc.shared.input.StringInput;
import dev.notkili.aoc.shared.misc.Count;
import dev.notkili.aoc.shared.misc.collections.Counter;
import dev.notkili.aoc.shared.misc.collections.list.List;
import dev.notkili.aoc.shared.misc.tuple.Tuple;
import dev.notkili.aoc.shared.parse.InputParser;

import java.util.HashMap;

public class Day7 {
    public static void main(String[] args) {
        part1();

        part2();
    }

    private static void part1() {
        new InputParser(2023, 7).getInput().ifPresent(input -> {

            HashMap<Integer, List<Tuple<LongInput, String>>> hands = new HashMap<>();

            input.splitAt("\n").forEach(in -> {
                var split = in.splitAt(" ");
                var hand = split.get(0);
                var bet = split.get(1).asLong();

                var stren = getCardStrength(hand.splitAt("").mapTo(StringInput::asString).asList());

                if (!hands.containsKey(stren)) {
                    hands.put(stren, new List<>());
                }

                hands.get(stren).add(new Tuple<>(bet, hand.asString()));
            });

            Count count = new Count(1);
            LongInput result = new LongInput(0);

            for (int i = 1; i <= 7; i++) {
                var hand = hands.get(i);

                if (hand == null) {
                    continue;
                }

                hand.sort((a, b) -> compareHands(a.getB(), b.getB()));

                for (var h : hand) {
                    result = result.add(h.getA().multiply(count.getCount()));
                    count.increment();
                }
            }

            result.asSolution().print();//.submit(2023, 7, 1);
        });
    }

    // Returns 1 if a is better, -1 if b is better, 0 if equal
    private static int compareHands(String a, String b) {
        var charsA = a.toCharArray();
        var charsB = b.toCharArray();

        for (int i = 0; i < a.length(); i++) {
            var charA = charsA[i];
            var charB = charsB[i];

            if (charA == charB) {
            } else if (Character.isDigit(charA) && Character.isDigit(charB)) {
                return charA > charB ? 1 : -1;
            } else if (Character.isDigit(charA)) {
                return -1;
            } else if (Character.isDigit(charB)) {
                return 1;
            } else {
                switch (charA) {
                    case 'T':
                        return -1;
                    case 'J':
                        return charB == 'T' ? 1 : -1;
                    case 'Q':
                        return charB == 'T' || charB == 'J' ? 1 : -1;
                    case 'K':
                        return charB == 'T' || charB == 'J' || charB == 'Q' ? 1 : -1;
                    case 'A':
                        return charB == 'T' || charB == 'J' || charB == 'Q' || charB == 'K' ? 1 : -1;
                }
            }
        }

        return 0;
    }

    private static int compareHandsJoker(String a, String b) {
        var charsA = a.toCharArray();
        var charsB = b.toCharArray();

        for (int i = 0; i < a.length(); i++) {
            var charA = charsA[i];
            var charB = charsB[i];

            if (charA == charB) {
                // Same char
            } else if (Character.isDigit(charA) && Character.isDigit(charB)) {
                return charA > charB ? 1 : -1;
            } else if (Character.isDigit(charA)) {
                return charB == 'J' ? 1 : -1;
            } else if (Character.isDigit(charB)) {
                return charA == 'J' ? -1 : 1;
            } else {
                switch (charA) {
                    case 'J':
                        return -1;
                    case 'T':
                        return charB == 'J' ? 1 : -1;
                    case 'Q':
                        return charB == 'T' || charB == 'J' ? 1 : -1;
                    case 'K':
                        return charB == 'T' || charB == 'J' || charB == 'Q' ? 1 : -1;
                    case 'A':
                        return charB == 'T' || charB == 'J' || charB == 'Q' || charB == 'K' ? 1 : -1;
                }
            }
        }

        return 0;
    }

    private static void part2() {
        new InputParser(2023, 7).getInput().ifPresent(input -> {

            HashMap<Integer, List<Tuple<LongInput, String>>> hands = new HashMap<>();

            input.splitAt("\n").forEach(in -> {
                var split = in.splitAt(" ");
                var hand = split.get(0);
                var bet = split.get(1).asLong();

                var stren = getCardStrengthJoker(hand.splitAt("").mapTo(StringInput::asString).asList());

                if (!hands.containsKey(stren)) {
                    hands.put(stren, new List<>());
                }

                hands.get(stren).add(new Tuple<>(bet, hand.asString()));
            });

            Count count = new Count(1);
            LongInput result = new LongInput(0);

            for (int i = 1; i <= 7; i++) {
                var hand = hands.get(i);

                if (hand == null) {
                    continue;
                }

                hand.sort((a, b) -> compareHandsJoker(a.getB(), b.getB()));

                for (var h : hand) {
                    result = result.add(h.getA().multiply(count.getCount()));
                    count.increment();
                }
            }

            result.asSolution().print(); //.submit(2023, 7, 2);
        });
    }

    private static int getCardStrength(java.util.List<String> cards) {
        Counter<String> c = new Counter<>();
        c.add(cards);

        if (c.getSize() == 1) {
            return 7; // 5 of a kind
        } else if (c.getSize() == 2) {
            var firstContained = c.getCountFor(cards.get(0)).asInt();

            if (firstContained == 1 || firstContained == 4) {
                return 6; // Four of a kind
            } else {
                return 5; // Full house
            }
        } else if (c.getSize() == 3) {
            for (var ca : cards) {
                if (c.getCountFor(ca).asInt() == 3) {
                    return 4; // 3 of a kind
                } else if (c.getCountFor(ca).asInt() == 2) {
                    return 3; // 2 pairs
                }
            }

            throw new RuntimeException("");
        } else if (c.getSize() == 4) {
            return 2; // 1 pair
        } else {
            return 1; // High card
        }
    }

    private static int getCardStrengthJoker(java.util.List<String> cards) {
        Counter<String> c = new Counter<>();
        c.add(cards);

        if (c.getSize() == 1) {
            return 7; // 5 of a kind
        } else if (c.getSize() == 2) {
            if (c.getCountFor("J").getCount() != 0) {
                return 7;
            }

            var firstContained = c.getCountFor(cards.get(0)).asInt();

            if (firstContained == 1 || firstContained == 4) {
                return 6; // Four of a kind
            } else {
                return 5; // Full house
            }
        } else if (c.getSize() == 3) {
            if (c.getCountFor("J").getCount() != 0) {
                var elems = c.keys().remove("J");
                var elem1 = elems.get(0);
                var elem2 = elems.get(1);
                var jCount = c.getCountFor("J").getCount();

                if (jCount == 1) {
                    if (c.getCountFor(elem1).getCount() == 3 || c.getCountFor(elem2).getCount() == 3) {
                        return 6; // Four of a kind, 3 + 1 + J
                    } else if (c.getCountFor(elem1).getCount() == 2 && c.getCountFor(elem2).getCount() == 2) {
                        return 5; // Full house, 2 + 2 + J
                    }
                } else  {
                    return 6; // Four of a kind, 2 + 1 + 2 * J || 1 + 1 + 3 * J
                }
            }

            for (var ca : cards) {
                if (c.getCountFor(ca).asInt() == 3) {
                    return 4; // 3 of a kind
                } else if (c.getCountFor(ca).asInt() == 2) {
                    return 3; // 2 pairs
                }
            }

            throw new RuntimeException("");
        } else if (c.getSize() == 4) {
            if (c.getCountFor("J").getCount() != 0) {
                return 4; // 3 of a kind
            }
            return 2; // 1 pair
        } else {
            if (c.getCountFor("J").getCount() != 0) {
                return 2; // One pair
            }
            return 1; // High card
        }
    }
}
