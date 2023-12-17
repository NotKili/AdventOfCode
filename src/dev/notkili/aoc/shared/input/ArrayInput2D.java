package dev.notkili.aoc.shared.input;

public class ArrayInput2D<T> {
    private final T[][] backingArray;

    public static ArrayInput2D<CharInput> parse(StringInput input) {
        var split = input.splitAt("\n");
        int y = split.size();
        int x = split.get(0).length().asInt();

        var backingArray = new CharInput[y][x];

        for (int i = 0; i < y; i++) {
            var line = split.get(i).asString().toCharArray();
            for (int j = 0; j < x; j++) {
                backingArray[i][j] = new CharInput(line[j]);
            }
        }

        return new ArrayInput2D<>(backingArray);
    }

    public ArrayInput2D(T[][] backingArray) {
        this.backingArray = backingArray;
    }

    public T get(int x, int y) {
        return backingArray[y][x];
    }

    public int getWidth() {
        return backingArray[0].length;
    }

    public int getHeight() {
        return backingArray.length;
    }

    public T[][] getBackingArray() {
        return backingArray;
    }

    public void print() {
        for (var row : backingArray) {
            for (var col : row) {
                System.out.print(col);
            }
            System.out.println();
        }
    }
}
