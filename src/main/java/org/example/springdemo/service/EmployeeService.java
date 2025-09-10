package org.example.springdemo.service;

import org.example.springdemo.dao.EmployeeRepository;
import org.example.springdemo.dao.entity.Employee;
import org.example.springdemo.exception.EmployeeNotFoundException;
import org.example.springdemo.exception.InvalidEmployeeAgeException;
import org.example.springdemo.exception.SalaryNotMatchAgeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee createEmployee(Employee employee) {
        if(employee.getAge() <18 || employee.getAge()>65){
            throw new InvalidEmployeeAgeException("age is invalid");
        }
        if(employee.getAge()>30 && employee.getSalary()<20000){
            throw new SalaryNotMatchAgeException("salary must be later than 20000 when age is later than 30");
        }
        return employeeRepository.createEmployee(employee);
    }

    public Employee getEmployee(long id) {

        Employee employee = employeeRepository.findEmployeeById(id);
        if(employee == null){
            throw new EmployeeNotFoundException("employee is not exist");
        }
        return employee;
    }

    public List<Employee> queryEmployeeByGender(String gender) {
        return employeeRepository.findEmployeeByGender(gender);
    }

    public List<Employee> getEmployeeList() {
        return employeeRepository.findAllEmployees();
    }

    public Employee updateEmployee(long id, Employee updatedEmployee) {
        Employee employee = employeeRepository.findEmployeeById(id);
        if(employee == null){
            throw new RuntimeException("id is not exist");
        }
        return employeeRepository.updateEmployee(employee,updatedEmployee);
    }

    public boolean deleteEmployee(@PathVariable long id) {
        return employeeRepository.deleteEmployee(id);
    }

    public List<Employee> getEmployeesByPage(int page, int size) {
        return employeeRepository.getEmployeesByPage(page,size);
    }


}
