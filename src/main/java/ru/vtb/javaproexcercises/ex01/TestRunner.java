package ru.vtb.javaproexcercises.ex01;

import ru.vtb.javaproexcercises.ex01.annotations.*;
import ru.vtb.javaproexcercises.ex01.domain.TestExecution;
import ru.vtb.javaproexcercises.ex01.domain.TestInfo;
import ru.vtb.javaproexcercises.ex01.enums.TestResult;
import ru.vtb.javaproexcercises.ex01.exceptions.BadTestClassError;
import ru.vtb.javaproexcercises.ex01.exceptions.TestAssertionError;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TestRunner {

    public static Map<TestResult, List<TestExecution>> runTests(Class<?> c){
        Object testInstance = validateTestClass(c);

        Map<TestResult, List<TestExecution>> result = new HashMap<>();
        for (TestResult tr : TestResult.values()) {
            result.put(tr, new ArrayList<>());
        }

        Map<Method, TestInfo> testMethods = new HashMap<>();
        Method beforeSuite = null;
        Method afterSuite = null;
        Method beforeEach = null;
        Method afterEach = null;

        for (Method method : c.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Test.class)) {
                if (Modifier.isStatic(method.getModifiers())) {
                    throw new BadTestClassError("@Test cannot be on static method: " + method.getName());
                }
                Test testAnno = method.getAnnotation(Test.class);
                Order orderAnno = method.getAnnotation(Order.class);
                int order = orderAnno != null ? orderAnno.value() : testAnno.priority();
                if (order < 1 || order > 10) {
                    throw new BadTestClassError("@Order value must be between 1 and 10, but got " + order + " for method: " + method.getName());
                }
                String name = testAnno.name().isEmpty() ? method.getName() : testAnno.name();
                boolean disabled = method.isAnnotationPresent(Disabled.class);
                testMethods.put(method, new TestInfo(name, order, disabled));
            } else if (method.isAnnotationPresent(BeforeEach.class)) {
                if (Modifier.isStatic(method.getModifiers())) {
                    throw new BadTestClassError("@BeforeEach cannot be on static method: " + method.getName());
                }
                if (beforeEach != null) {
                    throw new BadTestClassError("Only one @BeforeEach method allowed, found: " + beforeEach.getName() + " and " + method.getName());
                }
                beforeEach = method;
            } else if (method.isAnnotationPresent(AfterEach.class)) {
                if (Modifier.isStatic(method.getModifiers())) {
                    throw new BadTestClassError("@AfterEach cannot be on static method: " + method.getName());
                }
                if (afterEach != null) {
                    throw new BadTestClassError("Only one @AfterEach method allowed, found: " + afterEach.getName() + " and " + method.getName());
                }
                afterEach = method;
            } else if (method.isAnnotationPresent(BeforeSuite.class)) {
                if (!Modifier.isStatic(method.getModifiers())) {
                    throw new BadTestClassError("@BeforeSuite must be static: " + method.getName());
                }
                if (beforeSuite != null) {
                    throw new BadTestClassError("Only one @BeforeSuite method allowed, found: " + beforeSuite.getName() + " and " + method.getName());
                }
                beforeSuite = method;
            } else if (method.isAnnotationPresent(AfterSuite.class)) {
                if (!Modifier.isStatic(method.getModifiers())) {
                    throw new BadTestClassError("@AfterSuite must be static: " + method.getName());
                }
                if (afterSuite != null) {
                    throw new BadTestClassError("Only one @AfterSuite method allowed, found: " + afterSuite.getName() + " and " + method.getName());
                }
                afterSuite = method;
            }
        }

        if (beforeSuite != null) {
            try {
                beforeSuite.invoke(null);
            } catch (Exception e) {
                throw new BadTestClassError("Failed to execute @BeforeSuite method: " + beforeSuite.getName() + ". Cause: " + e.getMessage());
            }
        }

        List<Map.Entry<Method, TestInfo>> sortedTests = sortTests(testMethods);
        for (Map.Entry<Method, TestInfo> entry : sortedTests) {
            Method method = entry.getKey();
            TestInfo info = entry.getValue();

            if (info.disabled()) {
                result.get(TestResult.Skipped).add(new TestExecution(TestResult.Skipped, info.name(), null));
                continue;
            }

            try {
                if (beforeEach != null) {
                    beforeEach.invoke(testInstance);
                }

                method.invoke(testInstance);

                result.get(TestResult.Success).add(new TestExecution(TestResult.Success, info.name(), null));

            } catch (InvocationTargetException e) {
                if (e.getCause() instanceof TestAssertionError) {
                    result.get(TestResult.Failed).add(new TestExecution(TestResult.Failed, info.name(), e));
                } else {
                    result.get(TestResult.Error).add(new TestExecution(TestResult.Error, info.name(), e));
                }
            } catch (Exception e) {
                result.get(TestResult.Error).add(new TestExecution(TestResult.Error, info.name(), e));
            } finally {
                if (afterEach != null) {
                    try {
                        afterEach.invoke(testInstance);
                    } catch (Exception ex) {
                        result.get(TestResult.Error).add(new TestExecution(TestResult.Error, info.name(), ex));
                    }
                }
            }
        }

        if (afterSuite != null) {
            try {
                afterSuite.invoke(null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return result;
    }

    private static Object validateTestClass(Class<?> c) {
        try {
            return c.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new BadTestClassError("Cannot instantiate test class: " + c.getName() + ". Cause: " + e.getMessage());
        }
    }

    private static List<Map.Entry<Method, TestInfo>> sortTests(Map<Method, TestInfo> testMethods) {
        return testMethods.entrySet().stream()
            .sorted((e1, e2) -> {
                int cmp = Integer.compare(e2.getValue().order(), e1.getValue().order());
                if (cmp != 0) return cmp;
                return e1.getValue().name().compareTo(e2.getValue().name());
            })
            .toList();
    }
}
