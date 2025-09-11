package org.example.springdemo.repository.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "t_employee")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int age;
    private double salary;
    private String gender;
    private boolean activeStatus;
    @Column(name = "company_id")
    private long companyId;



    public Employee(long id, String gender, int age, String name, double salary) {
        this.age = age;
        this.gender = gender;
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

}
