package com.rahul.jobmicroservices.job.impl;


import com.rahul.jobmicroservices.job.*;
import com.rahul.jobmicroservices.job.client.CompanyClient;
import com.rahul.jobmicroservices.job.client.ReviewClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

    private JobRepository jobRepository;
    private ModelMapper model;

    @Autowired
    private RestTemplate restTemplate;

    private CompanyClient companyClient;
    private ReviewClient reviewClient;

    int attempt =0;




    public JobServiceImpl(JobRepository jobRepository,ModelMapper model, RestTemplate restTemplate,
                          CompanyClient companyClient,ReviewClient reviewClient
    ) {
        this.jobRepository = jobRepository;
        this.model =model;
        this.restTemplate =restTemplate;
        this.companyClient= companyClient;
        this.reviewClient = reviewClient;

    }

     @Override
//    @CircuitBreaker(name = "companyBreaker",
//                      fallbackMethod = "companyBreakerFallback")
//     @Retry(name = "companyBreaker",
//                      fallbackMethod = "companyBreakerFallback")
     @RateLimiter(name = "companyBreaker")
//             fallbackMethod = "companyBreakerFallback")

    public List<JobDto> findAll() {

//        List<Job> jobs = jobRepository.findAll();
//
//        JobDto jobDto = model.map(jobs,JobDto.class);

        System.out.println("Attempt : "+ ++attempt);

        List<Job> jobs = jobRepository.findAll();
        List<JobDto> jobDtos = new ArrayList<>();



        for (Job job : jobs) {
            JobDto jobDto = model.map(job, JobDto.class);
            Long companyId = job.getCompanyId(); // Assuming companyId is a Long

            // Check if companyId is not null
            if (companyId != null) {
//                CompanyDto companyDto = restTemplate.getForObject(
//                        "http://localhost:8081/company/{id}",
//                        CompanyDto.class,
//                        companyId
                //                );
                CompanyDto companyDto = companyClient.getCompanyById(job.getCompanyId());

                // Feign Client
                List<ReviewDto> reviews = reviewClient.getReviews(jobDto.getCompanyId());

//                ResponseEntity<List<ReviewDto>> reviewResponse = restTemplate.exchange("http://localhost:8083/reviews?companyId="+
//                        job.getCompanyId(),
//                        HttpMethod.GET,
//                        null,
//                        new ParameterizedTypeReference<List<ReviewDto>>(){
//                        });
//                List<ReviewDto> reviews = reviewResponse.getBody();
                jobDto.setCompanyDto(companyDto);
                jobDto.setReviewDto(reviews);

            }

            else {
                // Handle the case where companyId is null
                System.err.println("Company ID is null for job ID " + job.getId());
            }

            jobDtos.add(jobDto);
        }

        return jobDtos;


    }

    //Fallback

    public List<String> companyBreakerFallback(Exception e){
        List<String> list = new ArrayList<>();
        list.add("Dummy");
        return list;
    }

    @Override
    public JobDto createdJob(JobDto jobDto) {


        Job job =model.map(jobDto,Job.class);

        Job savedJob = jobRepository.save(job);
        return model.map(savedJob, JobDto.class);

    }

    @Override
    public JobDto getJobById(Long id) {

        //companyDto -> set data by making a rest api call

        //job -> jobDto
        Job job = jobRepository.findById(id).orElseThrow(()-> new ResourceNotFound("job", "id", id));
        JobDto jobDto = model.map(job,JobDto.class);

//        Rest Template
//        CompanyDto companyDto = restTemplate.getForObject("http://localhost:8081/company/{id}", CompanyDto.class, job.getCompanyId());

        //Feign Client
        CompanyDto companyDto = companyClient.getCompanyById(job.getCompanyId());

        // Rest Template
//        ResponseEntity<List<ReviewDto>> reviewResponse = restTemplate.exchange("http://localhost:8083/reviews?companyId="+
//                        job.getCompanyId(),
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<ReviewDto>>(){
//                });
//        List<ReviewDto> reviews = reviewResponse.getBody();

        // Feign Client
        List<ReviewDto> reviews = reviewClient.getReviews(jobDto.getCompanyId());

        jobDto.setCompanyDto(companyDto);
       jobDto.setReviewDto(reviews);

        return jobDto;
    }
    @Override
    public JobDto updateJob(JobDto jobDto,Long id) {

             Job job = jobRepository.findById(id).orElseThrow(()-> new ResourceNotFound("job", "id", id));

          job.setTitle(jobDto.getTitle());
          job.setDescription(jobDto.getDescription());
          job.setMinSalary(jobDto.getMinSalary());
          job.setMaxSalary(jobDto.getMaxSalary());
          job.setLocation(job.getLocation());
          job.setCompanyId(job.getCompanyId());
          job.setId(id);

          Job updatedJob = jobRepository.save(job);

          return model.map(updatedJob,JobDto.class);


    }

    @Override
    public void deleteJob(Long id) {

        Job job = jobRepository.findById(id).orElseThrow(()-> new ResourceNotFound("job", "id", id));

        jobRepository.delete(job);

    }

}
