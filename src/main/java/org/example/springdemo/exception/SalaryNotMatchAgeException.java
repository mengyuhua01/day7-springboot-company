package org.example.springdemo.exception;

public class SalaryNotMatchAgeException extends RuntimeException {
    public SalaryNotMatchAgeException(String message) {
        super(message);
    }
}
