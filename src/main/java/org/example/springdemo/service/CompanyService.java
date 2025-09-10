package org.example.springdemo.service;

import org.example.springdemo.dao.CompanyRepository;
import org.example.springdemo.dao.entity.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    public Company createCompany(Company company) {

        return companyRepository.createCompany(company);
    }

    public Company getCompany(long id) {
        return companyRepository.findCompanyById(id);
    }

    public List<Company> getCompanies() {
        return companyRepository.findAllCompanies();
    }

    public Company updateCompany(long id, Company updatedCompany) {

        Company company = companyRepository.updateCompany(id,updatedCompany);
        if (company == null) {
            return null;
        }
        company.setName(updatedCompany.getName());
        return company;
    }

    public boolean deleteCompany(long id) {
        return companyRepository.deleteCompany(id);
    }

    public List<Company> getCompaniesByPage(int page, int size) {
       return companyRepository.getCompaniesByPage(page,size);
    }


}
