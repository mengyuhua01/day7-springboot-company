package org.example.springdemo.repository;

import org.example.springdemo.dto.UpdateEmployeeReq;
import org.example.springdemo.repository.entity.Employee;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeRepository {


    public Employee createEmployee(Employee employee);

    public Employee findEmployeeById(long id);

    public List<Employee> findEmployeeByGender(String gender);

    public List<Employee> findAllEmployees();

    public Employee updateEmployee(Employee employee);

    public void deleteEmployee(Employee employee);

    public List<Employee> getEmployeesByPage(Pageable pageable);

    public void clear();
}
