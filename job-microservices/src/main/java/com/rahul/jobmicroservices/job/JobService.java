package com.rahul.jobmicroservices.job;

import java.util.List;

public interface JobService {


   JobDto createdJob(JobDto jobDto);
   List<JobDto> findAll();

   JobDto getJobById(Long id);

   JobDto updateJob(JobDto jobDto,Long id);

   void deleteJob(Long id);



}
