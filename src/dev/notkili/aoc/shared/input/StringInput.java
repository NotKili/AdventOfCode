package dev.notkili.aoc.shared.input;

import dev.notkili.aoc.shared.Pattern;
import dev.notkili.aoc.shared.Solution;
import dev.notkili.aoc.shared.misc.Constants;
import dev.notkili.aoc.shared.misc.collections.Counter;
import dev.notkili.aoc.shared.misc.collections.set.Set;
import dev.notkili.aoc.shared.misc.tuple.*;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

public class StringInput implements Input<StringInput> {
    public static StringInput of(Object in) {
        return new StringInput(in.toString());
    }
    
    private final String input;

    public StringInput(String input) {
        if (input.endsWith("\n")) {
            input = input.substring(0, input.length() - 1);
        }

        this.input = input;
    }

    public String str() {
        return input;
    }

    public IntInput integer() {
        return new IntInput(input);
    }

    public LongInput longInteger() {
        return new LongInput(input);
    }

    public DoubleInput doubleInteger() {
        return new DoubleInput(input);
    }

    public IntInput length() {
        return new IntInput(input.length());
    }

    public boolean empty() {
        return input.isEmpty();
    }
    
    public boolean blank() {
        return input.isBlank();
    }

    public boolean endsWith(String suffix) {
        return input.endsWith(suffix);
    }

    public boolean startsWith(String prefix) {
        return input.startsWith(prefix);
    }

    public StringInput substring(int beginIndex) {
        return new StringInput(input.substring(beginIndex));
    }

    public StringInput substring(int beginIndex, int endIndex) {
        return new StringInput(input.substring(beginIndex, endIndex));
    }

    public StringInput concat(String str) {
        return new StringInput(input.concat(str));
    }

    public StringInput concat(StringInput str) {
        return new StringInput(input.concat(str.input));
    }

    public StringInput substringTo(int endIndex) {
        return new StringInput(input.substring(0, endIndex));
    }

    public StringInput trim() {
        return new StringInput(input.trim());
    }

    public StringInput map(Function<StringInput, StringInput> mapper) {
        return mapper.apply(this);
    }

    public StringInput mapStr(Function<String, String> mapper) {
        return new StringInput(mapper.apply(input));
    }

    public char[] chars() {
        return input.toCharArray();
    }

    public Character[] charsInput() {
        return input.chars().mapToObj(c -> (char) c).toArray(Character[]::new);
    }

    public CharInput charAt(int index) {
        return new CharInput(input.charAt(index));
    }

    public StringInput replace(String old, String replacement) {
        return new StringInput(input.replace(old, replacement));
    }

    public StringInput replaceAll(String regex, String replacement) {
        return new StringInput(input.replaceAll(regex, replacement));
    }

    public StringInput reverse() {
        return new StringInput(new StringBuilder(input).reverse().toString());
    }

    public StringInput orderBy(Comparator<Character> comparator) {
        return new StringInput(input.chars().mapToObj(c -> (char) c).sorted(comparator).map(String::valueOf).collect(Collectors.joining()));
    }

    public ListInput.StringListInput split(String regex) {
        return new ListInput.StringListInput(Arrays.stream(input.split(regex)).map(StringInput::new).collect(Collectors.toList()));
    }

    public ListInput.StringListInput split(String regex, int limit) {
        return new ListInput.StringListInput(Arrays.stream(input.split(regex, limit)).map(StringInput::new).collect(Collectors.toList()));
    }

    public StringInput join(long times) {
        var builder = new StringBuilder();

        for (int i = 0; i < times; i++) {
            builder.append(input);
        }

        return new StringInput(builder.toString());
    }

    public StringInput join(long times, String delimiter) {
        var builder = new StringBuilder();

        for (int i = 0; i < times; i++) {
            builder.append(input);

            if (i != times - 1) {
                builder.append(delimiter);
            }
        }

        return new StringInput(builder.toString());
    }

    public IntInput count(char c) {
        int count = 0;

        for (char x : input.toCharArray()) {
            if (x == c) {
                count++;
            }
        }

        return new IntInput(count);
    }

    public IntInput count(Predicate<Character> predicate) {
        int count = 0;

        for (char x : input.toCharArray()) {
            if (predicate.test(x)) {
                count++;
            }
        }

        return new IntInput(count);
    }

    public IntInput count(String s) {
        int count = 0;

        for (int i = 0; i < input.length() - s.length(); i++) {
            if (input.startsWith(s, i)) {
                count++;
            }
        }

        return new IntInput(count);
    }

    public Counter<Character> count() {
        var c =  new Counter<Character>();

        for (var character : input.toCharArray())
            c.add(character);

        return c;
    }

    public Set<Character> unique() {
        var set = new Set<Character>();

        for (char c : input.toCharArray()) {
            set.add(c);
        }

        return set;
    }

    public StringInput first(String regex, int n, boolean overlap) {
        return first(new Pattern(regex), n, overlap);
    }

    public StringInput first(Pattern regex, int n, boolean overlap) {
        int count = 0;

        if (overlap) {
            var startIndex = 0;
            Matcher matcher = regex.compile().matcher(input);

            while (matcher.find(startIndex)) {
                if (count == n) {
                    return new StringInput(matcher.group());
                }

                startIndex = matcher.start() + 1;
                count++;
            }
        } else {
            Matcher matcher = regex.compile().matcher(input);

            while (matcher.find()) {
                if (count == n) {
                    return new StringInput(matcher.group());
                }

                count++;
            }
        }

        throw new NoSuchElementException("String " + input + " does not contain a first " + n + "th matching " + regex.pattern());
    }

    public StringInput last(String regex, int n, boolean overlap) {
        return last(new Pattern(regex), n, overlap);
    }
    
    public StringInput last(Pattern regex, int n, boolean overlap) {
        return reverse().first(regex.reverse(), n, overlap).reverse();
    }

    public ListInput.StringListInput all(String regex) {
        return all(new Pattern(regex));
    }
    
    public ListInput.StringListInput all(Pattern regex) {
        var list = new ArrayList<StringInput>();
        Matcher matcher = regex.compile().matcher(input);

        while (matcher.find()) {
            list.add(new StringInput(matcher.group()));
        }

        return new ListInput.StringListInput(list);
    }

    public IntInput firstNumber(int n) {
        return first(Pattern.of(Constants.DIGITS_PATTERN), n, false).integer();
    }

    public IntInput lastNumber(int n) {
        return last(Pattern.of(Constants.DIGITS_PATTERN), n, false).integer();
    }
    
    public ListInput.IntListInput numbers() {
        return numbers(false);
    }
    
    public ListInput.IntListInput numbers(boolean includeLiteral) {
        var list = new ArrayList<IntInput>();
        Matcher matcher;
        
        if (!includeLiteral) {
            matcher = Pattern.of(Constants.DIGITS_PATTERN).compile().matcher(input);
        } else {
            matcher = Pattern.of(Constants.LITERAL_DIGIT_PATTERN).or(Constants.DIGITS_PATTERN).compile().matcher(input);
        }

        while (matcher.find()) {
            list.add(new IntInput(matcher.group()));
        }
        
        return new ListInput.IntListInput(list);
    }

    public IntInput firstDigit(int n) {
        return firstDigit(n, false);
    }

    public IntInput firstDigit(int n, boolean includeLiteral) {
        if (!includeLiteral) {
            int count = 0;

            for (int i = 0; i < input.length(); i++) {
                if (Character.isDigit(input.charAt(i))) {
                    if (count == n) {
                        return new IntInput(Character.digit(input.charAt(i), 10));
                    }

                    count++;
                }
            }

        } else {
            Matcher matcher = Pattern.of(Constants.LITERAL_DIGIT_PATTERN).or(Constants.DIGIT_PATTERN).compile().matcher(input);

            int count = 0;
            int startIndex = 0;
            while (matcher.find(startIndex)) {
                if (count == n) {
                    var result = matcher.group();

                    if (result.length() != 1) {
                        return IntInput.literal(result);
                    }

                    return new IntInput(matcher.group());
                }

                startIndex = matcher.start() + 1;
                count++;
            }
        }

        throw new NoSuchElementException("String " + input + " does not contain a first " + n + "th digit");
    }

    public IntInput lastDigit(int n) {
        return lastDigit(n, false);
    }

    public IntInput lastDigit(int n, boolean includeLiteral) {
        if (!includeLiteral) {
            int count = 0;

            for (int i = input.length() - 1; i >= 0; i--) {
                if (Character.isDigit(input.charAt(i))) {
                    if (count == n) {
                        return new IntInput(Character.digit(input.charAt(i), 10));
                    }

                    count++;
                }
            }
        } else {
            Matcher matcher = Pattern.of(Constants.LITERAL_DIGIT_PATTERN.reverse().str()).or(Constants.DIGIT_PATTERN.str()).compile().matcher(reverse().str());

            int count = 0;
            int startIndex = 0;
            while (matcher.find(startIndex)) {
                if (count == n) {
                    var result = matcher.group();

                    if (result.length() != 1) {
                        return IntInput.literal(new StringInput(result).reverse().str());
                    }

                    return new IntInput(matcher.group());
                }

                startIndex = matcher.start() + 1;
                count++;
            }
        }


        throw new NoSuchElementException("String " + input + " does not contain a last " + n + "th digit");
    }
    
    public ListInput.IntListInput digits() {
        return digits(false);
    }

    public ListInput.IntListInput digits(boolean includeLiteral) {
        var list = new ArrayList<IntInput>();
        Matcher matcher;
        
        if (!includeLiteral) {
            matcher = Pattern.of(Constants.DIGIT_PATTERN.str()).compile().matcher(input);
        } else {
            matcher = Pattern.of(Constants.LITERAL_DIGIT_PATTERN.str()).or(Constants.DIGIT_PATTERN.str()).compile().matcher(input);
        }

        while (matcher.find()) {
            list.add(new IntInput(matcher.group()));
        }
        
        return new ListInput.IntListInput(list);
    }

    public ListInput.StringListInput groupLines(int n) {
        var lines = input.split("\n");

        if (lines.length % n != 0) {
            throw new IllegalArgumentException("StringInput.groupLines() requires a number of lines that is a multiple of the number of lines in the input");
        }

        var list = new ArrayList<StringInput>();

        for (int i = 0; i < lines.length; i += n) {
            var builder = new StringBuilder();

            for (int j = 0; j < n; j++) {
                builder.append(lines[i + j]);
            }

            list.add(new StringInput(builder.toString()));
        }

        return new ListInput.StringListInput(list);
    }

    public Tuple<StringInput, StringInput> tuple(String splitAtRegex) {
        var split = input.split(splitAtRegex);

        if (split.length != 2) {
            throw new IllegalArgumentException("StringInput.asTuple() requires a regex that splits the input into exactly two parts");
        }

        return new Tuple<>(new StringInput(split[0]), new StringInput(split[1]));
    }

    public Triple<StringInput, StringInput, StringInput> triple(String splitAtRegex) {
        var split = input.split(splitAtRegex);

        if (split.length != 3) {
            throw new IllegalArgumentException("StringInput.asTriple() requires a regex that splits the input into exactly three parts");
        }

        return new Triple<>(new StringInput(split[0]), new StringInput(split[1]), new StringInput(split[2]));
    }

    public Quadruple<StringInput, StringInput, StringInput, StringInput> quadruple(String splitAtRegex) {
        var split = input.split(splitAtRegex);

        if (split.length != 4) {
            throw new IllegalArgumentException("StringInput.asQuadruple() requires a regex that splits the input into exactly four parts");
        }

        return new Quadruple<>(new StringInput(split[0]), new StringInput(split[1]), new StringInput(split[2]), new StringInput(split[3]));
    }

    public Quintuple<StringInput, StringInput, StringInput, StringInput, StringInput> quintuple(String splitAtRegex) {
        var split = input.split(splitAtRegex);

        if (split.length != 5) {
            throw new IllegalArgumentException("StringInput.asQuintuple() requires a regex that splits the input into exactly five parts");
        }

        return new Quintuple<>(new StringInput(split[0]), new StringInput(split[1]), new StringInput(split[2]), new StringInput(split[3]), new StringInput(split[4]));
    }

    public Sextuple<StringInput, StringInput, StringInput, StringInput, StringInput, StringInput> sextuple(String splitAtRegex) {
        var split = input.split(splitAtRegex);

        if (split.length != 6) {
            throw new IllegalArgumentException("StringInput.asSextuple() requires a regex that splits the input into exactly six parts");
        }

        return new Sextuple<>(new StringInput(split[0]), new StringInput(split[1]), new StringInput(split[2]), new StringInput(split[3]), new StringInput(split[4]), new StringInput(split[5]));
    }

    @Override
    public String toString() {
        return this.input;
    }

    @Override
    public int compareTo(StringInput o) {
        return input.compareTo(o.input);
    }

    @Override
    public void print() {
        System.out.print(input);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        
        if (o instanceof StringInput si) {
            return Objects.equals(input, si.input);
        }
        
        return Objects.equals(input, o.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(input);
    }

    @Override
    public Solution solution() {
        return new Solution(input);
    }
}
