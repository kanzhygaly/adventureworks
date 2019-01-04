/**
 * Worker Layer #1 - Review processor
 * Worker consumes a review from the queue and scans for inappropriate language.
 * If a message contains a bad word, the message is marked as inappropriate.
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
import kz.ya.adventureworks.service.ProductReviewService;

/**
 *
 * @author yerlana
 */
public class ReviewWorker implements MessageListener {

    private final Logger logger = LoggerFactory.getLogger(ReviewWorker.class);
    private final String[] BAD_WORDS = {"fee", "nee", "cruul", "leent"};
    private final CountDownLatch latch;
    private final ProductReviewService productReviewService;

    @Autowired
    public ReviewWorker(CountDownLatch latch, ProductReviewService productReviewService) {
        this.latch = latch;
        this.productReviewService = productReviewService;
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
                productReviewService.declineProductReview(review);

                logger.info("ReviewWorker: archive " + review);
            } else {
                // approve and publish
                productReviewService.approveProductReview(review);

                logger.info("ReviewWorker: publish " + review);
            }
        }
        
        // release latch
        latch.countDown();
    }
}
