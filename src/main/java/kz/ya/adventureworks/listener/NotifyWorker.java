/**
 * Worker Layer #2 - Notifications
 * Worker consumes a review from the queue and checks if it was approved and
 * published. If yes then it sends notification to the reviewer via email.
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
import kz.ya.adventureworks.service.ProductReviewService;

/**
 *
 * @author yerlana
 */
public class NotifyWorker implements MessageListener {

    private final Logger logger = LoggerFactory.getLogger(NotifyWorker.class);
    private final EmailService emailService;
    private final ProductReviewService reviewService;
    
    @Autowired
    public NotifyWorker(EmailService emailService, ProductReviewService reviewService) {
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
