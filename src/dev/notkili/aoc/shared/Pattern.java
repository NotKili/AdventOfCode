package dev.notkili.aoc.shared;

public class Pattern {
    private final String pattern;

    public static Pattern of(String pattern) {
        return new Pattern(pattern);
    }

    public Pattern(String pattern) {
        this.pattern = group(pattern);
    }

    public Pattern or(String pattern) {
        return new Pattern(this.pattern + '|' + group(pattern));
    }

    public Pattern or(Pattern pattern) {
        return new Pattern(this.pattern + '|' + pattern.pattern);
    }

    public Pattern then(String pattern) {
        return new Pattern(this.pattern + group(pattern));
    }

    public Pattern then(Pattern pattern) {
        return new Pattern(this.pattern + pattern.pattern);
    }

    public Pattern count(int count) {
        return new Pattern(this.pattern + '{' + count + '}');
    }

    public Pattern min(int min) {
        return new Pattern(this.pattern + '{' + min + ",}");
    }

    public Pattern max(int max) {
        return new Pattern(this.pattern + "{0," + max + '}');
    }

    public Pattern between(int min, int max) {
        return new Pattern(this.pattern + '{' + min + ',' + max + '}');
    }

    public java.util.regex.Pattern compile() {
        return java.util.regex.Pattern.compile(pattern);
    }

    private String group(String pattern) {
        if (pattern.startsWith("(") && pattern.endsWith(")"))
            return pattern;

        return "(" + pattern + ")";
    }
}
