package com.rahul.jobmicroservices.job;


import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.List;

@Data
public class JobDto {

    private Long id;
    private String title;

    private String description;

    private String minSalary;

    private String maxSalary;
    private String location;

    private Long companyId;
    private CompanyDto companyDto;

    private List<ReviewDto> reviewDto;

}
