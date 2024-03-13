package com.rahul.companymicroservices.company;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CompanyController {

    private CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("/company")
    public ResponseEntity<CompanyDto> createCompany(@RequestBody CompanyDto companyDto){

        return new ResponseEntity<>(companyService.createCompany(companyDto), HttpStatus.CREATED);
    }
    @GetMapping("/company")
    public ResponseEntity<List<CompanyDto>> findAll(){
        return new ResponseEntity<>(companyService.findAll(),HttpStatus.OK);
    }

    @GetMapping("/company/{id}")
    public ResponseEntity<CompanyDto> getCompanyById(@PathVariable Long id){

        return new ResponseEntity<>(companyService.getCompanyById(id),HttpStatus.OK);
    }

    @PutMapping("/company/{id}")
    public ResponseEntity<CompanyDto> updatedCompany( @RequestBody CompanyDto companyDto,
                                                      @PathVariable (value = "id") Long id
                                                      )
    {
        return new ResponseEntity<>(companyService.updateCompany(companyDto,id),HttpStatus.OK);
    }

    @DeleteMapping("/company/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable  Long id){

        companyService.deleteCompany(id);
        return new ResponseEntity<>("Deleted Successfully",HttpStatus.OK);
    }

}
