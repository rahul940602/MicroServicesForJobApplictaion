package com.rahul.Reviewmicroservices.review;


import lombok.Data;

@Data
public class ReviewDto {

    private Long id;
    private String title;
    private String description;
    private double rating;

    private Long companyId;
}
