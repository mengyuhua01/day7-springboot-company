package org.example.springdemo.Controller;

import org.example.springdemo.dao.entity.Company;
import org.example.springdemo.dao.entity.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@RestController
public class CompanyController {
    private List<Company> companies = new ArrayList<>();

    @PostMapping("/companies")
    public ResponseEntity<Company> createCompany(@RequestBody Company company){
        company.setId(companies.size()+1);
        companies.add(company);
        return ResponseEntity.status(HttpStatus.CREATED).body(company);
    }
    @GetMapping("/companies/{id}")
    public ResponseEntity<Company> getCompany(@PathVariable long id){
        Company company = companies.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
        if (company == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(company);
    }

}
