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
    public ResponseEntity<Employee> getEmployee(@PathVariable long id){
        Employee employee = employees.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
        if (employee == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(employee);
    }


    @GetMapping("/employees/gender")
    public List<Employee> queryEmployeeByGender(@RequestParam String gender){
        return employees.stream().filter(employee -> employee.getGender().equals(gender)).collect(Collectors.toList());
    }
    @GetMapping("/employees")
    public List<Employee> getEmployeeList() {
        return employees;
    }
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable long id, @RequestBody Employee updatedEmployee) {
        Employee employee = employees.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);
        if (employee == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        employee.setAge(updatedEmployee.getAge());
        employee.setSalary(updatedEmployee.getSalary());
        return ResponseEntity.ok(employee);
    }
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable long id) {
        boolean removed = employees.removeIf(employee -> employee.getId() == id);
        if (!removed) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @GetMapping("/employees/page")
    public ResponseEntity<List<Employee>> getEmployeesByPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size) {
        int fromIndex = (page - 1) * size;
        int toIndex = Math.min(fromIndex + size, employees.size());
        if (fromIndex >= employees.size()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        List<Employee> paginatedEmployees = employees.subList(fromIndex, toIndex);
        return ResponseEntity.ok(paginatedEmployees);
    }
}
