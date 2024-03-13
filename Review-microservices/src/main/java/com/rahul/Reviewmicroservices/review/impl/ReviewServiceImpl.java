package com.rahul.Reviewmicroservices.review.impl;

import com.rahul.Reviewmicroservices.review.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ReviewServiceImpl implements ReviewService{

    private ReviewRepository reviewRepository;

    private  ModelMapper model;

    public ReviewServiceImpl(ReviewRepository reviewRepository,  ModelMapper model) {
        this.reviewRepository = reviewRepository;
        this.model = model;
    }


    @Override
    public List<ReviewDto> getAllReviews(Long companyId) {

        List<Review> reviews = reviewRepository.findByCompanyId(companyId);



        return reviews.stream().map((review)->model.map(review, ReviewDto.class)).collect(Collectors.toList());
    }

    @Override
    public ReviewDto addReview(Long companyId, ReviewDto reviewDto) {

        List<Review> reviews = reviewRepository.findByCompanyId(companyId);


        Review review = model.map(reviewDto,Review.class);
        review.setCompanyId(companyId);

        Review savedReview = reviewRepository.save(review);
        return model.map(savedReview, ReviewDto.class);

    }

    @Override
    public ReviewDto getReviewById(Long reviewId) {

        Review review = reviewRepository.findById(reviewId).orElseThrow(
                ()-> new ResourceNotFound("review","id",reviewId));


        return model.map(review, ReviewDto.class);

    }

    @Override
    public ReviewDto updateReview(Long reviewId, ReviewDto reviewDto) {


  Review review = reviewRepository.findById(reviewId).orElseThrow(
          ()-> new ResourceNotFound("review","id",reviewId));


        review.setTitle(reviewDto.getTitle());
        review.setDescription(reviewDto.getDescription());
        review.setRating(reviewDto.getRating());
        review.setCompanyId(reviewDto.getCompanyId());
        review.setId(reviewId);

        Review updateReview = reviewRepository.save(review);
        return model.map(updateReview, ReviewDto.class);
    }

    @Override
    public void deleteReview(Long reviewId) {

        Review review = reviewRepository.findById(reviewId).orElseThrow(
                ()-> new ResourceNotFound("review","id",reviewId));

             reviewRepository.delete(review);

    }


}
