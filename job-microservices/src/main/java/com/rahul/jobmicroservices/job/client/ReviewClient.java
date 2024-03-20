package com.rahul.jobmicroservices.job.client;

import com.rahul.jobmicroservices.job.ReviewDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "REVIEW-SERVICE", url = "http://localhost:8083")
public interface ReviewClient {

    @GetMapping("/reviews")
    List<ReviewDto> getReviews(@RequestParam("companyId")Long companyId);
}
