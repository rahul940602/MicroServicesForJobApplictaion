package com.rahul.jobmicroservices.job;


import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class JobDto {

    private Long id;
    private String title;

    private String description;

    private String minSalary;

    private String maxSalary;
    private String location;

    private Long companyId;

}
