package org.example.springdemo.repository.imp;

import org.example.springdemo.repository.EmployeeRepository;
import org.example.springdemo.repository.entity.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepositoryInMemoryImp {

    private List<Employee> employees = new ArrayList<>();

    private long employeeId = 0;

    public void clear() {
        employeeId = 0;
        employees.clear();
    }

    public Employee createEmployee(Employee employee) {
        employee.setId(++employeeId);
        employee.setActiveStatus(true);
        employees.add(employee);
        return employee;
    }

    public Employee findEmployeeById(long id) {
        return employees.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
    }

    public List<Employee> findEmployeeByGender(String gender) {
        return employees.stream().filter(employee -> employee.getGender().equals(gender)).collect(Collectors.toList());
    }

    public List<Employee> findAllEmployees() {
        return employees;
    }

    public Employee updateEmployee(Employee employee,Employee updatedEmployee) {
        employee.setName(updatedEmployee.getName());
        employee.setAge(updatedEmployee.getAge());
        employee.setSalary(updatedEmployee.getSalary());
        employee.setGender(updatedEmployee.getGender());
        employee.setActiveStatus(updatedEmployee.isActiveStatus());
        return employee;
    }

    public void deleteEmployee(Employee employee) {
        employee.setActiveStatus(false);
    }

    public List<Employee> getEmployeesByPage(int page, int size) {
        int fromIndex = (page - 1) * size;
        int toIndex = Math.min(fromIndex + size, employees.size());
        if (fromIndex >= toIndex) {
            return null;
        }
        return employees.subList(fromIndex, toIndex);
    }
}
