/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kz.ya.adventureworks.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import kz.ya.adventureworks.entity.ProductReview;
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
public class ReviewWorker implements MessageListener {

    private final Logger logger = LoggerFactory.getLogger(ReviewWorker.class);
    private final String[] BAD_WORDS = {"fee", "nee", "cruul", "leent"};
    private final CountDownLatch latch;
    private final ReviewService reviewService;

    @Autowired
    public ReviewWorker(CountDownLatch latch, ReviewService reviewService) {
        this.latch = latch;
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
            boolean containsBadWord = false;

            // check for bad words in review text
            for (String badWord : BAD_WORDS) {
                if (review.getReview().toLowerCase().contains(badWord)) {
                    containsBadWord = true;
                    break;
                }
            }

            if (containsBadWord) {
                logger.info("The product review contains bad words in text.");

                // mark as inappropriate
                reviewService.declineProductReview(review);

                logger.info("ReviewWorker: archive " + review);
            } else {
                // approve and publish
                reviewService.approveProductReview(review);

                logger.info("ReviewWorker: publish " + review);
            }
        }
        
        // release latch
        latch.countDown();
    }
}
