package org.example.springdemo.service;

import org.example.springdemo.dao.entity.Employee;
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
    private List<Employee> employees = new ArrayList<>();

    private long employeeId = 0;

    public Employee createEmployee(Employee employee){
        employee.setId(++employeeId);
        employees.add(employee);
        return employee;
    }
    public Employee getEmployee(long id){
        Employee employee = employees.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
        return employee;
    }

    public List<Employee> queryEmployeeByGender(String gender){
        return employees.stream().filter(employee -> employee.getGender().equals(gender)).collect(Collectors.toList());
    }

    public List<Employee> getEmployeeList() {
        return employees;
    }
    public Employee updateEmployee(long id, Employee updatedEmployee) {
        Employee employee = employees.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);
        if (employee == null) {
            return null;
        }
        employee.setName(updatedEmployee.getName());
        employee.setAge(updatedEmployee.getAge());
        employee.setSalary(updatedEmployee.getSalary());
        employee.setGender(updatedEmployee.getGender());
        return employee;
    }
    public boolean deleteEmployee(@PathVariable long id) {
        return employees.removeIf(employee -> employee.getId() == id);
    }
    public List<Employee> getEmployeesByPage(int page,int size) {
        int fromIndex = (page - 1) * size;
        int toIndex = Math.min(fromIndex + size, employees.size());
        if (fromIndex >= employees.size()) {
            return null;
        }
        return employees.subList(fromIndex, toIndex);
    }

    public void clear(){
        employeeId = 0;
        employees.clear();
    }
}
