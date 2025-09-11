package org.example.springdemo.repository.imp;

import org.example.springdemo.repository.EmployeeRepository;
import org.example.springdemo.repository.dao.EmployeeJpaRepository;
import org.example.springdemo.repository.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeRepositoryInDbImp implements EmployeeRepository {

    @Autowired
    private EmployeeJpaRepository employeeJpaRepository;

    @Override
    public Employee createEmployee(Employee employee) {
        employee.setActiveStatus(true);
        return employeeJpaRepository.save(employee);
    }

    @Override
    public Employee findEmployeeById(long id) {
        return employeeJpaRepository.findById(id).orElse(null);
    }

    @Override
    public List<Employee> findEmployeeByGender(String gender) {
        return employeeJpaRepository.getByGender(gender);
    }

    @Override
    public List<Employee> findAllEmployees() {
        return employeeJpaRepository.findAll();
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        return employeeJpaRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Employee employee) {
        employee.setActiveStatus(false);
        employeeJpaRepository.save(employee);
    }

    @Override
    public List<Employee> getEmployeesByPage(Pageable pageable) {
        return employeeJpaRepository.findAll(pageable).getContent();
    }

    @Override
    public void clear() {
        employeeJpaRepository.deleteAll();
    }
}
