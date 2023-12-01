package dev.notkili.aoc.shared;

import dev.notkili.aoc.shared.submit.ResultSubmitter;

public class Solution {
    private final String input;

    public Solution(String input) {
        this.input = input;
    }

    public void submit(int year, int day, int part) {
        new ResultSubmitter(this.input, year, day, part).submit();
    }

    public void print() {
        System.out.println("Solution: " + this.input);
    }
}
