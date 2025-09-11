package org.example.springdemo.repository;

import org.example.springdemo.repository.entity.Company;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CompanyRepository {
    Company createCompany(Company company);

    Company findCompanyById(long id);

    List<Company> findAllCompanies();

    Company updateCompany(Company company);

    void deleteCompany(Company company);

    List<Company> getCompaniesByPage(Pageable pageable);

    void clear();
}
