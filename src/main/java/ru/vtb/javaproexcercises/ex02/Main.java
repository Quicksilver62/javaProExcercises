package ru.vtb.javaproexcercises.ex02;

import ru.vtb.javaproexcercises.ex02.domain.Employee;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        List<Integer> listInt = List.of(5, 2, 10, 9, 4, 3, 10, 1, 13);
        List<Employee> employees = List.of(
                new Employee ("Александр", 18, "Инженер"),
                new Employee ("Екатерина", 54, "Инженер"),
                new Employee ("Дмитрий", 34, "Инженер"),
                new Employee ("Анастасия", 28, "Менеджер"),
                new Employee ("Владимир", 65, "Директор"),
                new Employee ("Ольга", 43, "Инженер"),
                new Employee ("Сергей", 23, "Инженер")
        );
        List<String> wordList = List.of("Москва", "Санкт-Петербург", "Москва", "Казань", "Екатеринбург", "Москва",
                "Новосибирск", "Санкт-Петербург");
        String words = "Москва Санкт-Петербург Москва Казань Екатеринбург Москва Новосибирск Санкт-Петербург";
        List<String> lines = List.of(
                "apple banana cherry date elderberry",
                "fig grape honeydew kiwi lemon",
                "mango nectarine orange papaya quince",
                "raspberry strawberry tangerine lime vanilla");

        System.out.println("3-е наибольшее число: " + thirdMax(listInt));
        System.out.println("3-е наибольшее уникальное число: " + thirdUniqueMax(listInt));
        System.out.println("самые старшие инженеры: " + oldestEmployees(employees));
        System.out.println("средний возраст инженеров: " + averageAge(employees));
        System.out.println("самое длинное слово: " + longestWord(wordList));
        System.out.println("мапа слово - сколько раз оно встречается во входной строке: " + wordsMap(words));
        System.out.println("самое длинное слово из массива строк: " + longestWordFromStrings(lines));
        System.out.println("==============================================================================");
        printWords(wordList);
    }

    private static Integer thirdMax(List<Integer> list) {
        return list.stream()
                .sorted(Comparator.reverseOrder())
                .skip(2)
                .findFirst()
                .orElseThrow();
    }

    private static Integer thirdUniqueMax(List<Integer> list) {
        return list.stream()
                .distinct()
                .sorted(Comparator.reverseOrder())
                .skip(2)
                .findFirst()
                .orElseThrow();
    }

    private static List<Employee> oldestEmployees(List<Employee> employees) {
        return employees.stream()
                .filter(it -> "Инженер".equals(it.position()))
                .sorted((a,b) -> b.age() - a.age())
                .limit(3)
                .collect(Collectors.toList());
    }

    private static double averageAge(List<Employee> employees) {
        return employees.stream()
                .filter(it -> "Инженер".equals(it.position()))
                .mapToInt(Employee::age)
                .average()
                .orElse(0.0);
    }

    private static String longestWord(List<String> words) {
        return words.stream()
                .filter(Objects::nonNull)
                .max(Comparator.comparing(String::length))
                .orElse("");
    }

    private static Map<String, Integer> wordsMap(String words) {
        return Arrays.stream(words.split(" "))
                .collect(Collectors.toMap(Function.identity(), word -> 1, Integer::sum));
    }

    private static void printWords(List<String> words) {
        words.stream()
                .filter(Objects::nonNull)
                .distinct()
                .sorted(Comparator.comparing(String::length)
                        .thenComparing(String::compareTo))
                .forEach(System.out::println);
    }

    private static String longestWordFromStrings(List<String> words) {
        return words.stream()
                .flatMap(it -> Arrays.stream(it.split(" ")))
                .max(Comparator.comparing(String::length))
                .orElse("");
    }
}
