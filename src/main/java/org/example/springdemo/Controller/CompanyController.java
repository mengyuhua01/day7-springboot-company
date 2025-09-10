package org.example.springdemo.Controller;

import org.example.springdemo.dao.entity.Company;

import org.example.springdemo.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping()
    public ResponseEntity<Company> createCompany(@RequestBody Company company) {

        return ResponseEntity.status(HttpStatus.CREATED).body(companyService.createCompany(company));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompany(@PathVariable long id) {
        Company company = companyService.getCompany(id);
        if (company == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(company);
    }

    @GetMapping("")
    public List<Company> getCompanies() {
        return companyService.getCompanies();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Company> updateCompany(@PathVariable long id, @RequestBody Company updatedCompany) {
        Company company = companyService.updateCompany(id, updatedCompany);
        if (company == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        company.setName(updatedCompany.getName());
        return ResponseEntity.ok(company);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable long id) {
        boolean removed = companyService.deleteCompany(id);
        if (!removed) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/page")
    public ResponseEntity<List<Company>> getCompaniesByPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size) {
        List<Company> paginatedCompanies = companyService.getCompaniesByPage(page, size);
        if (paginatedCompanies == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(paginatedCompanies);
    }

}
