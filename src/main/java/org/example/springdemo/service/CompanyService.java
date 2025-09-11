package org.example.springdemo.service;

import org.example.springdemo.dto.UpdateCompanyReq;
import org.example.springdemo.repository.CompanyRepository;
import org.example.springdemo.repository.entity.Company;
import org.example.springdemo.exception.CompanyNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public Company updateCompany(UpdateCompanyReq updatedCompany) {
        Company company = getCompany(updatedCompany.getId());
        if(company  == null){
            throw new CompanyNotFoundException("company not found");
        }
        company.setName(updatedCompany.getName());
        return companyRepository.updateCompany(company);
    }

    public void deleteCompany(long id) {
        Company company = getCompany(id);
        companyRepository.deleteCompany(company);
    }

    public List<Company> getCompaniesByPage(int page, int size) {
        Pageable pageable = PageRequest.of(page -1, size);
        return companyRepository.getCompaniesByPage(pageable);
    }


}
