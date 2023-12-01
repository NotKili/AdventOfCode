package dev.notkili.aoc.shared.misc;

import dev.notkili.aoc.shared.input.StringInput;
import dev.notkili.aoc.shared.misc.collections.Set;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Unmodifiable sets & lists of constants
 */
public class Constants {
    public static final Set<Integer> DIGITS = Set.of(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));

    public static final StringInput DIGIT_PATTERN = new StringInput("\\d");

    public static final StringInput DIGITS_PATTERN = new StringInput("\\d+");

    public static final StringInput LITERAL_DIGIT_PATTERN = new StringInput("zero|one|two|three|four|five|six|seven|eight|nine");

    public static final Set<Character> DIGITS_CHAR = Set.of(List.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9'));

    public static final Set<Character> HEX_DIGITS = Set.of(List.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                                                          'a', 'b', 'c', 'd', 'e', 'f'));

    public static final Set<Character> LOWERCASE_LATIN_LETTERS = Set.of(List.of('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
                                                                  'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
                                                                  'u', 'v', 'w', 'x', 'y', 'z'));

    public static final Set<Character> UPPERCASE_LATIN_LETTERS = Set.of(List.of('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
                                                                  'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
                                                                  'U', 'V', 'W', 'X', 'Y', 'Z'));

    public static final Set<Character> LETTERS = LOWERCASE_LATIN_LETTERS.union(UPPERCASE_LATIN_LETTERS);
}
