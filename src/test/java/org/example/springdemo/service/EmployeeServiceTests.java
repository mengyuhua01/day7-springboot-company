package org.example.springdemo.service;

import org.example.springdemo.dao.EmployeeRepository;
import org.example.springdemo.dao.entity.Employee;
import org.example.springdemo.exception.InvalidEmployeeAgeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
public class EmployeeServiceTests {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private  EmployeeService employeeService;

    @Test
    public void should_not_create_employee_when_create_Employee_given_invalid_employee_age(){
        Employee employee = new Employee(1,"male",17,"Tom",3000.0);
        Employee employee1 = new Employee(1,"male",66,"Tom",3000.0);
        assertThrows(InvalidEmployeeAgeException.class,()-> employeeService.createEmployee(employee));
        assertThrows(InvalidEmployeeAgeException.class,()-> employeeService.createEmployee(employee1));
    }

}
