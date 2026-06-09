package ru.vtb.javaproexcercises.ex01.domain;

import ru.vtb.javaproexcercises.ex01.enums.TestResult;

public record TestExecution(TestResult testResult, String testName, Exception exception) {

    @Override
    public String toString() {
        return "Test{" +
            "testResult=" + testResult +
            ", testName='" + testName + '\'' +
            ", exception=" + exception +
            '}';
    }
}
