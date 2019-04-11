package io.github.jimmynguyen.dailyprogrammer.c161h;

import io.github.jimmynguyen.dailyprogrammer.c161h.entity.Network;
import io.github.jimmynguyen.dailyprogrammer.c161h.entity.TestCase;
import io.github.jimmynguyen.dailyprogrammer.c161h.util.FileUtil;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<TestCase> testCases = new ArrayList<>();
        if (args.length > 0) {
            List<String> lines = FileUtil.readAllLines(args[0]);
            testCases.add(new TestCase(lines.subList(0, lines.indexOf("")), lines.subList(lines.indexOf("")+1, lines.size())));
        } else {
            String[][][] testCasesArray = new String[][][]{
                {
                    {
                        "A B 2",
                        "A C 2",
                        "B C 2",
                        "B D 2",
                        "C E 1",
                        "D E 2",
                        "D G 1",
                        "E F 2",
                        "F G 2"
                    },
                    {
                        "A G",
                        "A G",
                        "C E",
                        "G D",
                        "D E",
                        "A B",
                        "A D"
                    }
                },
                {
                    {
                        "A B 2",
                        "A C 2",
                        "B C 2",
                        "B D 2",
                        "C E 1",
                        "D E 2",
                        "D G 1",
                        "E F 2",
                        "F G 2"
                    },
                    {
                        "A G",
                        "A G",
                        "C E",
                        "G D",
                        "D E",
                        "A B",
                        "A D"
                    }
                }
            };
            for (String[][] testCaseArray : testCasesArray) {
                testCases.add(new TestCase(testCaseArray[0], testCaseArray[1]));
            }
        }
        for (TestCase testCase : testCases) {
            System.out.println(testCase);
            Network network = new Network(testCase.getNetwork(), testCase.getCalls());
            System.out.println(network);
            network.placeCalls();
            System.out.println();
        }
    }
}
