/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kz.ya.adventureworks.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import kz.ya.adventureworks.entity.ProductReview;
import kz.ya.adventureworks.service.EmailService;
import kz.ya.adventureworks.service.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

/**
 *
 * @author yerlana
 */
public class NotifyWorker implements MessageListener {

    private final Logger logger = LoggerFactory.getLogger(NotifyWorker.class);
    private final EmailService emailService;
    private final ReviewService reviewService;
    
    @Autowired
    public NotifyWorker(EmailService emailService, ReviewService reviewService) {
        this.emailService = emailService;
        this.reviewService = reviewService;
    }

    @Override
    public void onMessage(final Message message, final byte[] pattern) {
        ProductReview review = null;
        try {
            // cast JSON object to entity
            ObjectMapper objectMapper = new ObjectMapper();
            review = objectMapper.readValue(message.getBody(), ProductReview.class);
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }

        if (review != null) {
            // check whether product review is approved
            boolean isReviewApproved = reviewService.isApproved(review.getId());

            if (isReviewApproved) {
                logger.info("NotifyWorker: " + review);

                // send notification to reviewer
                emailService.sendNotification(review);
            }
        }
    }
}
