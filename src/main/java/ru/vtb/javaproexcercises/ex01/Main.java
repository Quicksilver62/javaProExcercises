package ru.vtb.javaproexcercises.ex01;

import ru.vtb.javaproexcercises.ex01.domain.TestExecution;
import ru.vtb.javaproexcercises.ex01.enums.TestResult;

import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Map<TestResult, List<TestExecution>> results = TestRunner.runTests(SampleTest.class);

        for (Map.Entry<TestResult, List<TestExecution>> entry : results.entrySet()) {
            System.out.println("\n=== " + entry.getKey() + " ===");
            for (TestExecution test : entry.getValue()) {
                System.out.println("  " + test);
            }
        }
    }
}
