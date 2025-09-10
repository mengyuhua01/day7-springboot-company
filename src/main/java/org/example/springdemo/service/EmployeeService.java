package org.example.springdemo.service;

import org.example.springdemo.dao.EmployeeRepository;
import org.example.springdemo.dao.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee createEmployee(Employee employee) {
        return employeeRepository.createEmployee(employee);
    }

    public Employee getEmployee(long id) {
        return employeeRepository.findEmployeeById(id);
    }

    public List<Employee> queryEmployeeByGender(String gender) {
        return employeeRepository.findEmployeeByGender(gender);
    }

    public List<Employee> getEmployeeList() {
        return employeeRepository.findAllEmployees();
    }

    public Employee updateEmployee(long id, Employee updatedEmployee) {

        return employeeRepository.updateEmployee(id,updatedEmployee);
    }

    public boolean deleteEmployee(@PathVariable long id) {
        return employeeRepository.deleteEmployee(id);
    }

    public List<Employee> getEmployeesByPage(int page, int size) {
        return employeeRepository.getEmployeesByPage(page,size);
    }


}
