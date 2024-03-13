package com.rahul.jobmicroservices.job.impl;


import com.rahul.jobmicroservices.job.*;
import com.rahul.jobmicroservices.job.external.Company;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

    private JobRepository jobRepository;
    private ModelMapper model;



    public JobServiceImpl(JobRepository jobRepository,ModelMapper model) {
        this.jobRepository = jobRepository;
        this.model =model;

    }

    @Override
    public List<JobDto> findAll() {

        List<Job> jobs = jobRepository.findAll();
        List<JobWithCompanyDto> jobWithCompanyDtos = new ArrayList<>();
//        RestTemplate restTemplate = new RestTemplate();
//
//        Company company = restTemplate.getForObject("Http://localhost:8081/company/", Company.class);

        return jobs.stream().map((job) -> model.map(job,JobDto.class)).collect(Collectors.toList());
    }

    @Override
    public JobDto createdJob(JobDto jobDto) {


        Job job =model.map(jobDto,Job.class);
        Job savedJob = jobRepository.save(job);
        return model.map(savedJob, JobDto.class);
    }

    @Override
    public JobDto getJobById(Long id) {

        Job job = jobRepository.findById(id).orElseThrow(()-> new ResourceNotFound("job", "id", id));

        return model.map(job,JobDto.class);
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
