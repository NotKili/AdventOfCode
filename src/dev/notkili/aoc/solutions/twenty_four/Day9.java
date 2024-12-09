package dev.notkili.aoc.solutions.twenty_four;

import dev.notkili.aoc.shared.input.IntInput;
import dev.notkili.aoc.shared.input.LongInput;
import dev.notkili.aoc.shared.input.StringInput;
import dev.notkili.aoc.shared.misc.functional.TriConsumer;
import dev.notkili.aoc.shared.parse.InputParser;

import java.util.ArrayList;
import java.util.HashSet;

public class Day9 {
    public static void main(String[] args) {
        System.out.println("Solution 1:");
        
        new InputParser(2024, 9).getInput().ifPresent(strInput -> {
            var arr = new ArrayList<Long>();

            var empty = false;
            var id = 0L;
            for (StringInput stringInput : strInput.split("")) {
                long toAdd;
                if (empty) {
                    toAdd = -1L;
                } else {
                    toAdd = id++;
                }

                for (int i = 0; i < stringInput.integer().asLong(); i++) {
                    arr.add(toAdd);
                }
                
                empty = !empty;
            }

            var loop = true;
            while (loop) {
                loop = false;
                
                var nextReplace = arr.indexOf(-1L);
                
                if (nextReplace == -1)
                    continue;

                for (int i = arr.size() - 1; i > nextReplace; i--) {
                    if (arr.get(i) == -1L)
                        continue;

                    arr.set(nextReplace, arr.get(i));
                    arr.set(i, -1L);
                    loop = true;
                    break;
                }
            }
            
            var checksum = 0L;
            
            for (int i = 0; i < arr.size(); i++) {
                if (arr.get(i) == -1L) {
                    break;
                }
                
                checksum += i * arr.get(i);
            }
            
            new LongInput(checksum).solution().print();
        });

        System.out.println("Solution 2:");

        new InputParser(2024, 9).getInput().ifPresent(strInput -> {
            var arr = new ArrayList<Long>();

            var empty = false;
            var id = 0L;
            for (StringInput stringInput : strInput.split("")) {
                long toAdd;
                if (empty) {
                    toAdd = -1L;
                } else {
                    toAdd = id++;
                }

                for (int i = 0; i < stringInput.integer().asLong(); i++) {
                    arr.add(toAdd);
                }

                empty = !empty;
            }
            
            var notMoved = new HashSet<>(arr);
            
            while (!notMoved.isEmpty()) {
                var fileBlockStart = -1;
                var fileBlockSize = 0;
                var fileBlockID = -1L;

                for (int i = arr.size() - 1; i >= 0; i--) {
                    var curBlockID = arr.get(i);
                    
                    if (curBlockID == -1L) {
                        continue;
                    }
                    
                    if (!notMoved.contains(curBlockID)) {
                        continue;
                    }
                    
                    if (fileBlockID == -1L) {
                        fileBlockStart = i;
                        fileBlockID = curBlockID;
                    }
                    
                    if (curBlockID != fileBlockID) {
                        break;
                    }

                    fileBlockSize++;
                }
                
                if (fileBlockID == -1)
                    break;
                
                var emptyBlockSize = 0L;
                for (int i = 0; i < fileBlockStart; i++) {
                    var curBlockID = arr.get(i);
                    
                    if (curBlockID != -1L) {
                        emptyBlockSize = 0L;
                        continue;
                    }
                    
                    emptyBlockSize++;
                    
                    if (emptyBlockSize >= fileBlockSize) {
                        for (int x = i - fileBlockSize + 1; x <= i; x++) {
                            arr.set(x, fileBlockID);
                        }
                        
                        for (int x = fileBlockStart; x > fileBlockStart - fileBlockSize; x--) {
                            arr.set(x, -1L);
                        }
                        
                        break;
                    }
                }

                notMoved.remove(fileBlockID);
            }

            var checksum = 0L;

            for (int i = 0; i < arr.size(); i++) {
                if (arr.get(i) == -1L) {
                    continue;
                }

                checksum += i * arr.get(i);
            }

            new LongInput(checksum).solution().print();
        });
    }
    private static void printArr(ArrayList<Long> arr) {
        System.out.print("[");
        for (var a : arr) {
            System.out.print((a >= 0L ? a + "" : '.') + " ");
        }
        System.out.print("]\n");
    }
}
