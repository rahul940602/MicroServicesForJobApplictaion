package com.rahul.companymicroservices.company;

import com.rahul.companymicroservices.company.Dto.CompanyDto;
import com.rahul.companymicroservices.company.Dto.ReviewDto;

import java.util.List;

public interface CompanyService {

    CompanyDto createCompany(CompanyDto companyDto);

    List<CompanyDto> findAll();

    CompanyDto getCompanyById(Long id);

    CompanyDto updateCompany(CompanyDto companyDto, Long id);

    void deleteCompany(Long id);

    public void updateCompanyRating(ReviewDto reviewDto);
}
