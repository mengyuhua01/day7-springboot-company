package org.example.springdemo.exception.handler;

import org.example.springdemo.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmployeeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleEmployeeNotFoundException(Exception e) {
        return e.getMessage();
    }

    @ExceptionHandler(InvalidEmployeeAgeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleInvalidEmployeeAgeException(Exception e) {
        return Map.of("message", e.getMessage());
    }

    @ExceptionHandler(SalaryNotMatchAgeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleSalaryNotMatchAgeException(Exception e) {
        return Map.of("message", e.getMessage());
    }

    @ExceptionHandler(EmployeeInactiveException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleEmployeeInactiveException(Exception e) {
        return Map.of("message", e.getMessage());
    }

    @ExceptionHandler(CompanyNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleCompanyNotFoundException(Exception e) {
        return Map.of("message", e.getMessage());
    }
}
