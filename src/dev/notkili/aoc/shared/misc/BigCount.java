package dev.notkili.aoc.shared.misc;

import dev.notkili.aoc.shared.input.IntInput;
import dev.notkili.aoc.shared.input.LongInput;

import java.math.BigInteger;

public class BigCount {
    private BigInteger count;

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

    public BigCount(long count) {
        this.count = BigInteger.valueOf(count);
    }

    public BigCount(BigInteger count) {
        this.count = count;
    }

    public BigCount increment(BigCount count) {
        this.count = this.count.add(count.count);
        return this;
    }

    public BigCount increment(int count) {
        this.count = this.count.add(BigInteger.valueOf(count));
        return this;
    }

    public BigCount increment() {
        this.count = this.count.add(BigInteger.ONE);
        return this;
    }

    public BigCount decrement(BigCount count) {
        this.count = this.count.subtract(count.count);
        return this;
    }

    public BigCount decrement(int count) {
        this.count = this.count.subtract(BigInteger.valueOf(count));
        return this;
    }

    public BigCount decrement() {
        this.count = this.count.subtract(BigInteger.ONE);
        return this;
    }
    
    public BigInteger getCount() {
        return this.count;
    }

    public long getCountL() {
        return this.count.longValue();
    }

    public IntInput asIntInput() {
        return new IntInput(getCountL());
    }

    public LongInput asLongInput() {
        return new LongInput(getCountL());
    }

    public int asInt() {
        return (int) getCountL();
    }

    public BigCount copy() {
        return new BigCount(this.count);
    }

    @Override
    public String toString() {
        return "Count={" + count + "}";
    }
}
