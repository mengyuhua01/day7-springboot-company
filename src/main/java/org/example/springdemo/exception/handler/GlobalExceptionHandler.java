package org.example.springdemo.exception.handler;

import org.example.springdemo.exception.EmployeeNotFoundException;
import org.example.springdemo.exception.InvalidEmployeeAgeException;
import org.example.springdemo.exception.SalaryNotMatchAgeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmployeeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleEmployeeNotFoundException(Exception e){
        return  e.getMessage();
    }

    @ExceptionHandler(InvalidEmployeeAgeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidEmployeeAgeException(Exception e){
        return  e.getMessage();
    }

    @ExceptionHandler(SalaryNotMatchAgeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleSalaryNotMatchAgeException(Exception e){
        return  e.getMessage();
    }
}
