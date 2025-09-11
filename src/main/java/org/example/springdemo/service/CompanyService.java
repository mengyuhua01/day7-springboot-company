package org.example.springdemo.service;

import org.example.springdemo.repository.imp.CompanyRepository;
import org.example.springdemo.repository.entity.Company;
import org.example.springdemo.exception.CompanyNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    public Company createCompany(Company company) {

        return companyRepository.createCompany(company);
    }

    public Company getCompany(long id) {
        Company company = companyRepository.findCompanyById(id);
        if (company == null) {
            throw new CompanyNotFoundException("id don't exist");
        }
        return company;
    }

    public List<Company> getCompanies() {
        return companyRepository.findAllCompanies();
    }

    public Company updateCompany(long id, Company updatedCompany) {
        Company company = getCompany(id);
        return companyRepository.updateCompany(company, updatedCompany);
    }

    public void deleteCompany(long id) {
        Company company = getCompany(id);
        companyRepository.deleteCompany(company);
    }

    public List<Company> getCompaniesByPage(int page, int size) {
        return companyRepository.getCompaniesByPage(page, size);
    }


}
