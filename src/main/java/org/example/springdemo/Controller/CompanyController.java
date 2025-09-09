package org.example.springdemo.Controller;

import org.example.springdemo.dao.entity.Company;

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
    @GetMapping("/companies")
    public List<Company> getCompanies() {
        return companies;
    }
    @PutMapping("/companies/{id}")
    public ResponseEntity<Company> updateCompany(@PathVariable long id, @RequestBody Company updatedCompany) {
        Company company = companies.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
        if (company == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
         company.setName(updatedCompany.getName());
         return ResponseEntity.ok(company);
    }
    @DeleteMapping("/companies/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable long id) {
        boolean removed = companies.removeIf(company -> company.getId() == id);
        if (!removed) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @GetMapping("/companies/page")
    public ResponseEntity<List<Company>> getCompaniesByPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size) {
        int fromIndex = (page - 1) * size;
        int toIndex = Math.min(fromIndex + size, companies.size());
        if (fromIndex >= companies.size()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        List<Company> paginatedCompanies = companies.subList(fromIndex, toIndex);
        return ResponseEntity.ok(paginatedCompanies);
    }
}
