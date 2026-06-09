package ru.vtb.javaproexcercises.ex01.exceptions;

public class BadTestClassError extends RuntimeException{

    public BadTestClassError(String message) {
        super(message);
    }
}
