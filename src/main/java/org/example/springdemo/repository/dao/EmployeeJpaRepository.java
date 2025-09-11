package org.example.springdemo.repository.dao;

import org.example.springdemo.repository.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeJpaRepository extends JpaRepository<Employee,Long> {

    List<Employee> getByGender(String gender);
}
