/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kz.ya.adventureworks.service;

import kz.ya.adventureworks.entity.ProductReview;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author yerlana
 */
@Service
public class EmailServiceImpl implements EmailService {

    private final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Override
    public void sendNotification(ProductReview review) {
        String reviewerName = review.getName();
        String email = review.getEmail();

        logger.info("EmailServiceImpl: Notification was sent to " + reviewerName + " on email " + email);
    }
}
