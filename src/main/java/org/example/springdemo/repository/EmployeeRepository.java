package org.example.springdemo.repository;

import org.example.springdemo.repository.entity.Employee;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeRepository {


    Employee createEmployee(Employee employee);

    Employee findEmployeeById(long id);

    List<Employee> findEmployeeByGender(String gender);

    List<Employee> findAllEmployees();

    Employee updateEmployee(Employee employee);

    void deleteEmployee(Employee employee);

    List<Employee> getEmployeesByPage(Pageable pageable);

    void clear();
}
