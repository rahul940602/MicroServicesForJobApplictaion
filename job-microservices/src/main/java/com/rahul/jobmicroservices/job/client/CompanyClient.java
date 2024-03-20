package com.rahul.jobmicroservices.job.client;

import com.rahul.jobmicroservices.job.CompanyDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "COMPANY-SERVICE", url = "http://localhost:8081")
public interface CompanyClient {

    @GetMapping("/company/{id}")
    CompanyDto getCompanyById(@PathVariable("id") Long id);
}
