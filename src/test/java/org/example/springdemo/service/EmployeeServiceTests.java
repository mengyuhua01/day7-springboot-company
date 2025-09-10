package org.example.springdemo.service;

import org.example.springdemo.dao.EmployeeRepository;
import org.example.springdemo.dao.entity.Employee;
import org.example.springdemo.exception.EmployeeNotFoundException;
import org.example.springdemo.exception.InvalidEmployeeAgeException;
import org.example.springdemo.exception.SalaryNotMatchAgeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class EmployeeServiceTests {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    public void should_not_create_employee_when_create_Employee_given_invalid_employee_age() {
        Employee employee = new Employee(1, "male", 17, "Tom", 3000.0);
        Employee employee1 = new Employee(1, "male", 66, "Tom", 3000.0);
        assertThrows(InvalidEmployeeAgeException.class, () -> employeeService.createEmployee(employee));
        assertThrows(InvalidEmployeeAgeException.class, () -> employeeService.createEmployee(employee1));
        verify(employeeRepository,times(0)).createEmployee(employee);
    }

    @Test
    public void should_not_create_employee_when_create_Employee_given_employee_age_later_30_and_salary_below_20000() {
        Employee employee = new Employee(1, "male", 31, "Tom", 3000.0);
        assertThrows(SalaryNotMatchAgeException.class, () -> employeeService.createEmployee(employee));
        verify(employeeRepository,times(0)).createEmployee(employee);
    }


    @Test
    public void should_return_employee_when_get_employee_given_valid_id() {
        Employee employee = new Employee(1, "male", 21, "Tom", 4000.0);
        when(employeeRepository.findEmployeeById(1)).thenReturn(employee);
        Employee foundEmployee = employeeService.getEmployee(1);
        assertEquals(employee, foundEmployee);
        verify(employeeRepository,times(1)).findEmployeeById(1);
    }

    @Test
    public void should_not_return_employee_when_get_employee_given_not_existed_id() {
        when(employeeRepository.findEmployeeById(1)).thenReturn(null);
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployee(1));
        verify(employeeRepository,times(1)).findEmployeeById(1);
    }

    @Test
    public void should_return_employee_and_status_is_true_when_create_employee_given_employee() {
        Employee employee = new Employee();
        employee.setGender("male");
        employee.setName("Tom");
        employee.setAge(20);
        employee.setSalary(30000.0);
        Employee createdEmployee = new Employee();
        createdEmployee.setId(1);
        createdEmployee.setGender("male");
        createdEmployee.setName("Tom");
        createdEmployee.setAge(20);
        createdEmployee.setSalary(30000.0);
        createdEmployee.setActiveStatus(true);
        when(employeeRepository.createEmployee(employee)).thenReturn(createdEmployee);
        Employee employee1 = employeeService.createEmployee(employee);
        assertTrue(employee1.isActiveStatus());
    }
}