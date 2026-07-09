package ru.vtb.java.pro.paymentservice.exceptions;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(String message) { super(message); }
}
