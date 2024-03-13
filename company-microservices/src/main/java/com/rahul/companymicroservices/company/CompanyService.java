package com.rahul.companymicroservices.company;

import java.util.List;

public interface CompanyService {

    CompanyDto createCompany(CompanyDto companyDto);

    List<CompanyDto> findAll();

    CompanyDto getCompanyById(Long id);

    CompanyDto updateCompany(CompanyDto companyDto, Long id);

    void deleteCompany(Long id);
}
