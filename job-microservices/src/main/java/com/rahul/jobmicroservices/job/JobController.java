package com.rahul.jobmicroservices.job;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class JobController {

    private JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping("/jobs")
    public ResponseEntity<JobDto> createJob(@RequestBody JobDto jobDto){

        return new ResponseEntity<>(jobService.createdJob(jobDto),HttpStatus.CREATED);
    }

    @GetMapping("/jobs")
    public ResponseEntity<List<JobDto>> findAll(){

       return new ResponseEntity<>(jobService.findAll(),HttpStatus.OK);
    }

    @GetMapping("/jobs/{id}")
    public ResponseEntity<JobDto> getJobsById(@PathVariable Long id){



        return new ResponseEntity<>(jobService.getJobById(id),HttpStatus.OK);
    }

    @PutMapping("/jobs/{id}")
    public ResponseEntity<JobDto> updateJob(@PathVariable (value = "id") Long id,
                                            @RequestBody JobDto jobDto){


        return new ResponseEntity<>(jobService.updateJob(jobDto,id),HttpStatus.OK);

    }


    @DeleteMapping("/jobs/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable Long id){

        jobService.deleteJob(id);

       return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);

    }


}
