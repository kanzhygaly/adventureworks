/**
 * Redis Configuration Class
 */
package kz.ya.adventureworks.config;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

import kz.ya.adventureworks.listener.NotifyWorker;
import kz.ya.adventureworks.listener.ReviewWorker;
import kz.ya.adventureworks.service.EmailService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import kz.ya.adventureworks.service.ProductReviewService;

/**
 *
 * @author yerlana
 */
@Configuration
@ComponentScan("kz.ya.adventureworks")
public class RedisConfig {
    
    public static final String REVIEW_PROCESS_TOPIC = "pubsub:to-review";
    public static final String NOTIFY_PROCESS_TOPIC = "pubsub:to-notify";

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        final RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        return template;
    }

    @Bean
    RedisMessageListenerContainer redisContainer(RedisConnectionFactory redisConnectionFactory, 
            @Qualifier("reviewProcessListener") MessageListenerAdapter reviewProcessListener,
            @Qualifier("notifyProcessListener") MessageListenerAdapter notifyProcessListener) {
        
        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener(reviewProcessListener, new ChannelTopic(REVIEW_PROCESS_TOPIC));
        container.addMessageListener(notifyProcessListener, new ChannelTopic(NOTIFY_PROCESS_TOPIC));
        container.setTaskExecutor(Executors.newFixedThreadPool(4));
        return container;
    }
    
    @Bean("reviewProcessListener")
    MessageListenerAdapter reviewProcessListener(ReviewWorker worker) {
        return new MessageListenerAdapter(worker);
    }

    @Bean("notifyProcessListener")
    MessageListenerAdapter notifyProcessListener(EmailService emailService, ProductReviewService reviewService) {
        return new MessageListenerAdapter(new NotifyWorker(emailService, reviewService));
    }
    
    @Bean
    ReviewWorker reviewWorker(CountDownLatch latch, ProductReviewService reviewService) {
        return new ReviewWorker(latch, reviewService);
    }

    @Bean
    CountDownLatch latch() {
        return new CountDownLatch(1);
    }
}
