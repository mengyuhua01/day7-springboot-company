package org.example.springdemo.repository.imp;

import org.example.springdemo.repository.entity.Company;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CompanyRepository {
    private List<Company> companies = new ArrayList<>();
    private long companyId = 0;

    public Company createCompany(Company company) {
        company.setId(++companyId);
        companies.add(company);
        return company;
    }

    public Company findCompanyById(long id) {
        return companies.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
    }

    public List<Company> findAllCompanies() {
        return companies;
    }

    public Company updateCompany(Company company, Company updatedCompany) {
        company.setName(updatedCompany.getName());
        return company;
    }

    public void deleteCompany(Company company) {
        companies.remove(company);
    }

    public List<Company> getCompaniesByPage(int page, int size) {
        int fromIndex = (page - 1) * size;
        int toIndex = Math.min(fromIndex + size, companies.size());
        if (fromIndex >= toIndex) {
            return null;
        }
        return companies.subList(fromIndex, toIndex);
    }

    public void clear() {
        companies.clear();
        companyId = 0;
    }
}
