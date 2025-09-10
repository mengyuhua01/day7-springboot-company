package org.example.springdemo.service;

import org.example.springdemo.dao.entity.Company;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyService {
    private List<Company> companies = new ArrayList<>();
    private long companyId = 0;

    public Company createCompany(Company company) {
        company.setId(++companyId);
        companies.add(company);
        return company;
    }

    public Company getCompany(long id) {
       return companies.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public Company updateCompany(long id, Company updatedCompany) {
        Company company = companies.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
        if (company == null) {
            return null;
        }
        company.setName(updatedCompany.getName());
        return company;
    }

    public boolean deleteCompany(long id) {
       return  companies.removeIf(company -> company.getId() == id);
    }

    public List<Company> getCompaniesByPage(int page, int size) {
        int fromIndex = (page - 1) * size;
        int toIndex = Math.min(fromIndex + size, companies.size());
        if (fromIndex >= companies.size()) {
            return null;
        }
        return companies.subList(fromIndex,toIndex);
    }
    public void clear() {
        companies.clear();
        companyId = 0;
    }
}
