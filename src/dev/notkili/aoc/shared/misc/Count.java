package dev.notkili.aoc.shared.misc;

import dev.notkili.aoc.shared.input.IntInput;
import dev.notkili.aoc.shared.input.LongInput;

public class Count {
    private long count;

    public static Count fromInput(LongInput input) {
        return new Count(input.asLong());
    }

    public static Count fromInput(IntInput input) {
        return new Count(input.asInt());
    }

    public static Count fromLong(long l) {
        return new Count(l);
    }

    public static Count fromInt(int i) {
        return new Count(i);
    }

    public static Count empty() {
        return new Count(0);
    }

    public Count(long count) {
        this.count = count;
    }

    public Count increment(Count count) {
        this.count += count.count;
        return this;
    }

    public Count increment(int count) {
        this.count += count;
        return this;
    }

    public Count increment() {
        count++;
        return this;
    }

    public Count decrement(Count count) {
        this.count -= count.count;
        return this;
    }

    public Count decrement(int count) {
        this.count -= count;
        return this;
    }

    public Count decrement() {
        count--;
        return this;
    }

    public long getCount() {
        return count;
    }

    public IntInput asIntInput() {
        return new IntInput(count);
    }

    public LongInput asLongInput() {
        return new LongInput(count);
    }

    public int asInt() {
        return (int) count;
    }

    public Count copy() {
        return new Count(count);
    }
}
