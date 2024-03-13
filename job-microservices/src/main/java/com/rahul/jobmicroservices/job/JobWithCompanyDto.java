package com.rahul.jobmicroservices.job;

import com.rahul.jobmicroservices.job.external.Company;

import java.util.List;

public class JobWithCompanyDto {

    private List<Job> job;

    private List<Company> company;

    public List<Job> getJob() {
        return job;
    }

    public void setJob(List<Job> job) {
        this.job = job;
    }

    public List<Company> getCompany() {
        return company;
    }

    public void setCompany(List<Company> company) {
        this.company = company;
    }
}
