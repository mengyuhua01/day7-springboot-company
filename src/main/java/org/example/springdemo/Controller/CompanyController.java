package org.example.springdemo.Controller;

import org.example.springdemo.dto.UpdateCompanyReq;
import org.example.springdemo.repository.entity.Company;
import org.example.springdemo.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok(companyService.getCompany(id));
    }

    @GetMapping("")
    public List<Company> getCompanies() {
        return companyService.getCompanies();
    }

    @PutMapping("")
    public ResponseEntity<Company> updateCompany(@RequestBody UpdateCompanyReq updatedCompany) {
        Company company = companyService.updateCompany(updatedCompany);
        return ResponseEntity.ok(company);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable long id) {
        companyService.deleteCompany(id);
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
