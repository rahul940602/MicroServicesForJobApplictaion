package com.rahul.Reviewmicroservices.review.messaging;

import com.rahul.Reviewmicroservices.review.Review;
import com.rahul.Reviewmicroservices.review.ReviewDto;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class ReviewMessageProducer {

    private final RabbitTemplate rabbitTemplate;
    private final ModelMapper model;


    public ReviewMessageProducer(RabbitTemplate rabbitTemplate,
                                 ModelMapper model
    ) {
        this.rabbitTemplate = rabbitTemplate;
        this.model=model;
    }

    public void sendMessage(ReviewDto reviewDto){


                System.out.println(reviewDto.getId());
        System.out.println(reviewDto.getTitle());
        System.out.println(reviewDto.getDescription());
        System.out.println(reviewDto.getRating());
        System.out.println(reviewDto.getCompanyId());




        rabbitTemplate.convertAndSend("companyRatingQueue",reviewDto);
    }
}
