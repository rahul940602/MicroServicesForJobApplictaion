package com.rahul.Reviewmicroservices.review;

import com.rahul.Reviewmicroservices.review.messaging.ReviewMessageProducer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewMessageProducer reviewMessageProducer;

    public ReviewController(ReviewService reviewService,
                            ReviewMessageProducer reviewMessageProducer) {

        this.reviewService = reviewService;
        this.reviewMessageProducer = reviewMessageProducer;
    }

    @GetMapping
    public ResponseEntity<List<ReviewDto>> getAllReviews(@RequestParam Long companyId){

        return new ResponseEntity<>(reviewService.getAllReviews(companyId),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ReviewDto> addReview(@RequestParam Long companyId,
                                               @RequestBody ReviewDto reviewDto){
        ReviewDto addedReview = reviewService.addReview(companyId, reviewDto);
        reviewMessageProducer.sendMessage(addedReview);

        return new ResponseEntity<>(addedReview,HttpStatus.OK);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewDto> getReviewById(@PathVariable Long reviewId){
        return new ResponseEntity<>(reviewService.getReviewById(reviewId),HttpStatus.OK);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewDto> updateReview(@PathVariable Long reviewId,
                                                  @RequestBody ReviewDto reviewDto){
        return new ResponseEntity<>(reviewService.updateReview(reviewId,reviewDto),HttpStatus.OK);
    }
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId){
           reviewService.deleteReview(  reviewId);
        return new ResponseEntity<>("DeletedSuccessfully",HttpStatus.OK);
    }
}
