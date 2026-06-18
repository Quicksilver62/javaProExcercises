package ru.vtb.javaproexcercises.ex01;

import ru.vtb.javaproexcercises.ex01.annotations.AfterEach;
import ru.vtb.javaproexcercises.ex01.annotations.AfterSuite;
import ru.vtb.javaproexcercises.ex01.annotations.BeforeEach;
import ru.vtb.javaproexcercises.ex01.annotations.BeforeSuite;
import ru.vtb.javaproexcercises.ex01.annotations.Disabled;
import ru.vtb.javaproexcercises.ex01.annotations.Order;
import ru.vtb.javaproexcercises.ex01.annotations.Test;
import ru.vtb.javaproexcercises.ex01.exceptions.TestAssertionError;

public class SampleTest {

    private static int suiteCounter = 0;
    private int instanceCounter = 0;

    @BeforeSuite
    public static void beforeSuite() {
        System.out.println("=== Before Suite ===");
        suiteCounter++;
    }

    @BeforeEach
    public void beforeEach() {
        System.out.println("Before each test");
        instanceCounter++;
    }

    @AfterEach
    public void afterEach() {
        System.out.println("After each test");
    }

    @AfterSuite
    public static void afterSuite() {
        System.out.println("=== After Suite ===");
    }

    @Test(name = "Test 1", priority = 10)
    public void testOne() {
        System.out.println("Running testOne");
        if (instanceCounter != 1) {
            try {
                throw new TestAssertionError("instanceCounter should be 1");
            } catch (TestAssertionError e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Test(name = "Test 2", priority = 5)
    public void testTwo() {
        System.out.println("Running testTwo");
    }

    @Test(priority = 1)
    public void testThree() {
        System.out.println("Running testThree");
        throw new RuntimeException("Unexpected error!");
    }

    @Test(name = "Skipped Test", priority = 8)
    @Disabled
    public void testFour() {
        System.out.println("This should not run");
    }

    @Order(3)
    @Test
    public void testFive() {
        System.out.println("Running testFive with @Order");
    }
}
