package com.rahul.companymicroservices.company.impl;



import com.rahul.companymicroservices.company.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

        private CompanyRepository companyRepository;
        private ModelMapper model;

    public CompanyServiceImpl(CompanyRepository companyRepository, ModelMapper model) {
        this.companyRepository = companyRepository;
        this.model = model;
    }

    @Override
    public CompanyDto createCompany(CompanyDto companyDto) {

        Company company = model.map(companyDto,Company.class);
        Company savedCompany = companyRepository.save(company);
        return model.map(savedCompany, CompanyDto.class);

    }

    @Override
    public List<CompanyDto> findAll() {

        List<Company> companyies = companyRepository.findAll();
        return companyies.stream().map((company)->model.map(company,CompanyDto.class)).collect(Collectors.toList());
    }

    @Override
    public CompanyDto getCompanyById(Long id) {

        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("company","id", id) );
        return model.map(company,CompanyDto.class);
    }

    @Override
    public CompanyDto updateCompany(CompanyDto companyDto, Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("company","id", id));

        company.setName(companyDto.getName());
        company.setDescription(companyDto.getDescription());
        company.setId(id);

        Company updatedCompany = companyRepository.save(company);
        return model.map(updatedCompany, CompanyDto.class);
    }

    @Override
    public void deleteCompany(Long id) {



        Company company = companyRepository.findById(id)
                        .orElseThrow(()->new ResourceNotFound("company","id",id));

        companyRepository.delete(company);

    }
}
