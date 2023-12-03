package dev.notkili.aoc.shared;

import dev.notkili.aoc.shared.input.StringInput;

import java.util.Stack;

public class Pattern {
    private final String pattern;

    public static Pattern of(StringInput pattern) {
        return new Pattern(pattern.asString());
    }

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

    /**
     * Tries to reverse a pattern, might not work for overly complex pattern
     * @return The reversed pattern
     */
    public Pattern reverse() {
        StringBuilder builder = new StringBuilder();
        Stack<String> stack = new Stack<>();
        /*
        0 = push normal
        1 = add to builder
        2 = escape
         */
        int mode = 0;
        var chars = pattern.toCharArray();

        for (char c : chars) {
            if (mode == 2) {
                mode = 0;
                builder.append(c);
                stack.push(builder.toString());
                builder = new StringBuilder();
                continue;
            }

            switch (c) {
                case '(':
                    stack.push(")");
                    break;
                case ')':
                    stack.push("(");
                    break;
                case '[':
                    mode = 1;
                    builder = new StringBuilder();
                    builder.append(c);
                    break;
                case '{':
                    mode = 1;
                    Stack<String> tmp = new Stack<>();
                    builder = new StringBuilder();

                    var pop = stack.pop();

                    if (pop.equals("(") || pop.equals("[") || pop.equals("{" )) {
                        tmp.push(".");
                    } else if (pop.equals(")") || pop.equals("]") || pop.equals("}")) {
                        tmp.pop();
                    }

                    builder.append(pop);

                    while (!tmp.isEmpty()) {
                        pop = stack.pop();

                        if (pop.equals("(") || pop.equals("[") || pop.equals("{" )) {
                            tmp.push(".");
                        } else if (pop.equals(")") || pop.equals("]") || pop.equals("}")) {
                            tmp.pop();
                        }

                        builder.append(pop);
                    }

                    stack.push(builder.toString());
                    builder = new StringBuilder();
                    builder.append(c);
                    break;
                case ']':
                    mode = 0;
                    builder.append(c);
                    stack.push(builder.toString());
                    builder = new StringBuilder();
                    break;
                case '}':
                    mode = 0;
                    builder.append(c);
                    var result = builder.toString();
                    builder = new StringBuilder();

                    if (stack.isEmpty()) {
                        stack.push(result);
                        break;
                    }

                    stack.push(stack.pop() + result);
                    break;
                case '\\':
                    mode = 2;
                    builder = new StringBuilder();
                    builder.append(c);
                    break;
                case '+':
                case '.':
                case '*':
                case '?':
                    Stack<String> tmp2 = new Stack<>();
                    builder = new StringBuilder();

                    var pop2 = stack.pop();

                    if (pop2.equals("(") || pop2.equals("[") || pop2.equals("{" )) {
                        tmp2.push(".");
                    } else if (pop2.equals(")") || pop2.equals("]") || pop2.equals("}")) {
                        tmp2.pop();
                    }

                    builder.append(pop2);

                    while (!tmp2.isEmpty()) {
                        pop2 = stack.pop();
                        if (pop2.equals("(") || pop2.equals("[") || pop2.equals("{" )) {
                            tmp2.push(".");
                        } else if (pop2.equals(")") || pop2.equals("]") || pop2.equals("}")) {
                            tmp2.pop();
                        }

                        builder.append(pop2);
                    }

                    stack.push(c + "");
                    stack.push(builder.toString());
                    break;
                default:
                    switch (mode) {
                        case 0 -> stack.push(c + "");
                        case 1 -> builder.append(c);
                    }
            }
        }

        builder = new StringBuilder();

        while (!stack.isEmpty()) {
            builder.append(stack.pop());
        }

        return new Pattern(builder.toString());
    }

    public String pattern() {
        return pattern;
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
