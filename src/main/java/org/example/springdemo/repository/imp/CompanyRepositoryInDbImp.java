package org.example.springdemo.repository.imp;

import org.example.springdemo.repository.CompanyRepository;
import org.example.springdemo.repository.dao.CompanyJpaRepository;
import org.example.springdemo.repository.entity.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CompanyRepositoryInDbImp implements CompanyRepository {

    @Autowired
    private CompanyJpaRepository companyJpaRepository;

    @Override
    public Company createCompany(Company company) {
        return companyJpaRepository.save(company);
    }

    @Override
    public Company findCompanyById(long id) {
        return companyJpaRepository.findById(id).orElse(null);
    }

    @Override
    public List<Company> findAllCompanies() {
        return companyJpaRepository.findAll();
    }

    @Override
    public Company updateCompany(Company company) {
        return companyJpaRepository.save(company);
    }

    @Override
    public void deleteCompany(Company company) {
        companyJpaRepository.delete(company);
    }

    @Override
    public List<Company> getCompaniesByPage(Pageable pageable) {
        return companyJpaRepository.findAll(pageable).getContent();
    }

    @Override
    public void clear() {
        companyJpaRepository.deleteAll();
    }
}
