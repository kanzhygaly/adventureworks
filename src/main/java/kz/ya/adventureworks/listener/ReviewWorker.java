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

    @Autowired
    public ReviewWorker(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void onMessage(final Message message, final byte[] pattern) {
        boolean containsBadWord = false;
        
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ProductReview review = objectMapper.readValue(message.getBody(), ProductReview.class);
            for (String badWord : BAD_WORDS) {
                if (review.getReview().toLowerCase().contains(badWord)) {
                    containsBadWord = true;
                    break;
                }
            }
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
        
        if (containsBadWord) {
            logger.info("The product review contains bad words in text.");
            
            // mark as inappropriate
        } else {
            logger.info("ReviewWorker: " + new String(message.getBody()));
            
            // approve and publish
        }
        
        // release latch
        latch.countDown();
    }
}
