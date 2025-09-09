package org.example.springdemo.Controller;

import org.example.springdemo.dao.entity.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class EmployeeController {

    private List<Employee> employees = new ArrayList<>();

    @PostMapping("/employees")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee){
        employee.setId(employees.size()+1);
        employees.add(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(employee);
    }

    @GetMapping("/employees/{id}")
    public Employee getEmployee(@PathVariable long id){
        return employees.stream().filter(employee -> employee.getId() == id).findFirst().get();
    }

    @GetMapping("/employees/gender")
    public List<Employee> queryEmployeeByGender(@RequestParam String gender){
        return employees.stream().filter(employee -> employee.getGender().equals(gender)).collect(Collectors.toList());
    }
    @GetMapping("/employees")
    public List<Employee> getEmployeeList() {
        return employees;
    }
}
