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
    
    @Autowired
    public NotifyWorker(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void onMessage(final Message message, final byte[] pattern) {
        boolean isReviewApproved = true;

        if (isReviewApproved) {
            logger.info("NotifyWorker: " + new String(message.getBody()));

            try {
                ObjectMapper objectMapper = new ObjectMapper();
                ProductReview review = objectMapper.readValue(message.getBody(), ProductReview.class);
                
                emailService.sendNotification(review);                
            } catch (IOException ex) {
                logger.error(ex.getMessage());
            }
        }
    }
}
