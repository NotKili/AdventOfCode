package dev.notkili.aoc.shared;

import dev.notkili.aoc.shared.input.ListInput;
import dev.notkili.aoc.shared.input.StringInput;

import java.util.Objects;
import java.util.Stack;
import java.util.regex.Matcher;

public class Pattern {
    public static final Pattern ANY = new Pattern(".");
    public static final Pattern ALPHA = new Pattern("[a-zA-Z]");
    public static final Pattern ALPHA_LOW = new Pattern("[a-z]");
    public static final Pattern ALPHA_UP = new Pattern("[A-Z]");
    public static final Pattern DIGIT = new Pattern("\\d");
    public static final Pattern WORD = new Pattern("\\w");
    public static final Pattern NON_WORD = new Pattern("\\W");
    public static final Pattern NON_DIGIT = new Pattern("\\D");
    public static final Pattern NON_ALPHA = new Pattern("[^a-zA-Z]");
    public static final Pattern WHITESPACE = new Pattern("\\s");
    
    private final String pattern;

    public static Pattern of(StringInput pattern) {
        return new Pattern(pattern.str());
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
    
    public Pattern or(StringInput pattern) {
        return new Pattern(this.pattern + '|' + group(pattern.str()));
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
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static Builder builder(String pattern) {
        return new Builder(pattern);
    }
    
    public static Builder builder(StringInput pattern) {
        return new Builder(pattern);
    }
    
    public static Builder builder(Pattern pattern) {
        return new Builder(pattern);
    }
    
    public static class Builder {
        private Pattern pattern;
        
        public Builder() {
            this.pattern = new Pattern("");
        }
        
        public Builder(String pattern) {
            this.pattern = new Pattern(pattern);
        }
        
        public Builder(StringInput pattern) {
            this.pattern = new Pattern(pattern.str());
        }

        public Builder(Pattern pattern) {
            this.pattern = pattern;
        }
        
        public Builder or(String pattern) {
            Objects.requireNonNull(this.pattern);
            this.pattern = this.pattern.or(pattern);
            return this;
        }
        
        public Builder or(StringInput pattern) {
            Objects.requireNonNull(this.pattern);
            this.pattern = this.pattern.or(pattern);
            return this;
        }
        
        public Builder or(Pattern pattern) {
            Objects.requireNonNull(this.pattern);
            this.pattern = this.pattern.or(pattern);
            return this;
        }
        
        public Builder then(String pattern) {
            Objects.requireNonNull(this.pattern);
            this.pattern = this.pattern.then(pattern);
            return this;
        }
        
        public Builder then(StringInput pattern) {
            Objects.requireNonNull(this.pattern);
            this.pattern = this.pattern.then(pattern.str());
            return this;
        }
        
        public Builder then(Pattern pattern) {
            Objects.requireNonNull(this.pattern);
            this.pattern = this.pattern.then(pattern);
            return this;
        }
        
        public Builder count(int count) {
            Objects.requireNonNull(this.pattern);
            this.pattern = this.pattern.count(count);
            return this;
        }
        
        public Builder min(int min) {
            Objects.requireNonNull(this.pattern);
            this.pattern = this.pattern.min(min);
            return this;
        }
        
        public Builder max(int max) {
            Objects.requireNonNull(this.pattern);
            this.pattern = this.pattern.max(max);
            return this;
        }
        
        public Builder between(int min, int max) {
            Objects.requireNonNull(this.pattern);
            this.pattern = this.pattern.between(min, max);
            return this;
        }
        
        public Builder reverse() {
            Objects.requireNonNull(this.pattern);
            this.pattern = this.pattern.reverse();
            return this;
        }
        
        public java.util.regex.Pattern compile() {
            Objects.requireNonNull(this.pattern);
            return pattern.compile();
        }
        
        public Matcher matcher(String input) {
            Objects.requireNonNull(this.pattern);
            return pattern.compile().matcher(input);
        }
        
        public Matcher matcher(StringInput input) {
            Objects.requireNonNull(this.pattern);
            return pattern.compile().matcher(input.str());
        }
        
        public StringInput first(String input, int n, boolean wrap) {
            Objects.requireNonNull(this.pattern);
            return StringInput.of(input).first(this.pattern, n, wrap);
        }
        
        public StringInput first(StringInput input, int n, boolean wrap) {
            Objects.requireNonNull(this.pattern);
            return input.first(this.pattern, n, wrap);
        }
        
        public StringInput last(String input, int n, boolean wrap) {
            Objects.requireNonNull(this.pattern);
            return StringInput.of(input).last(this.pattern, n, wrap);
        }
        
        public StringInput last(StringInput input, int n, boolean wrap) {
            Objects.requireNonNull(this.pattern);
            return input.last(this.pattern, n, wrap);
        }
        
        public ListInput.StringListInput all(String input) {
            Objects.requireNonNull(this.pattern);
            return StringInput.of(input).all(this.pattern);
        }
        
        public ListInput.StringListInput all(StringInput input) {
            Objects.requireNonNull(this.pattern);
            return input.all(this.pattern);
        }
        
        public ListInput.StringListInput split(String input) {
            Objects.requireNonNull(this.pattern);
            return StringInput.of(input).split(this.pattern.pattern());
        }

        public Pattern build() {
            Objects.requireNonNull(this.pattern);
            return pattern;
        }
    }
}
