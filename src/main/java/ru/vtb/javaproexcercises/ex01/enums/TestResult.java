package ru.vtb.javaproexcercises.ex01.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TestResult {
    Success("тест выполнен успешно"),
    Failed("условие теста провалено"),
    Error("тест упал с произвольным исключением"),
    Skipped("тест не исполнялся");

    private final String value;
}
