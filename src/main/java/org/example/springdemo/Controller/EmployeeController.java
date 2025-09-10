package org.example.springdemo.Controller;

import org.example.springdemo.dao.entity.Employee;
import org.example.springdemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
   private EmployeeService employeeService;

    @PostMapping("") //extract
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.createEmployee(employee));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable long id){
        Employee employee = employeeService.getEmployee(id);
        if (employee == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(employee);
    }


    @GetMapping(path = "",params = {"genders"} )  //modify api
    public List<Employee> queryEmployeeByGender(@RequestParam String gender){
        return employeeService.queryEmployeeByGender(gender);
    }
    @GetMapping("")
    public List<Employee> getEmployeeList() {
        return employeeService.getEmployeeList();
    }
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable long id, @RequestBody Employee updatedEmployee) {
       Employee employee = employeeService.updateEmployee(id,updatedEmployee);
        if (employee == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(employee);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable long id) {
        boolean removed = employeeService.deleteEmployee(id);
        if (!removed) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @GetMapping("/page")
    public ResponseEntity<List<Employee>> getEmployeesByPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size) {
        List<Employee> paginatedEmployees = employeeService.getEmployeesByPage(page,size);
        if(paginatedEmployees == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(paginatedEmployees);
    }
}
