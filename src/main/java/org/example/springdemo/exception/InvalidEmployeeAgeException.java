package org.example.springdemo.exception;

public class InvalidEmployeeAgeException extends RuntimeException {
    public InvalidEmployeeAgeException(String message) {
        super(message);
    }
}
