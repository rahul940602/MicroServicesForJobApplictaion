package com.rahul.companymicroservices.company.messaging;

import com.rahul.companymicroservices.company.CompanyService;
import com.rahul.companymicroservices.company.Dto.ReviewDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class ReviewMessageConsumer {

    private final CompanyService companyService;

    public ReviewMessageConsumer(CompanyService companyService) {

        this.companyService =companyService;
    }

    @RabbitListener(queues = "companyRatingQueue")
    public  void consumeMessage(ReviewDto reviewDto){

        companyService.updateCompanyRating(reviewDto);

    }
}
