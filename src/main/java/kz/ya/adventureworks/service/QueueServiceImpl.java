/**
 * Queue Service
 * responsible for putting reviews onto a queues in right order
 */
package kz.ya.adventureworks.service;

import java.util.concurrent.CountDownLatch;
import kz.ya.adventureworks.config.RedisConfig;
import kz.ya.adventureworks.entity.ProductReview;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author yerlana
 */
@Service
public class QueueServiceImpl implements QueueService {
    
    private final Logger logger = LoggerFactory.getLogger(QueueServiceImpl.class);
    private final RedisTemplate<String, Object> redisTemplate;
    private final CountDownLatch latch;

    @Autowired
    public QueueServiceImpl(RedisTemplate<String, Object> redisTemplate, CountDownLatch latch) {
        this.redisTemplate = redisTemplate;
        this.latch = latch;
    }
    
    @Override
    public void publish(final ProductReview review) {
        // put the product review onto a review process queue
        redisTemplate.convertAndSend(RedisConfig.REVIEW_PROCESS_TOPIC, review);
        
        try {
            // wait for completion
            latch.await();
            logger.info("Review process is completed on product review " + review.getId());
        } catch (InterruptedException ex) {
            logger.error(ex.getMessage());
        }
        
        // put the product review onto a notify process queue
        redisTemplate.convertAndSend(RedisConfig.NOTIFY_PROCESS_TOPIC, review);
    }
}
