package com.rahul.Reviewmicroservices.review;

import java.util.List;

public interface ReviewService {


List<ReviewDto> getAllReviews(Long companyId);

ReviewDto addReview(Long companyId, ReviewDto reviewDto);

ReviewDto getReviewById(Long reviewId);

ReviewDto updateReview(Long reviewId, ReviewDto reviewDto);

void deleteReview(Long ReviewId);
}
