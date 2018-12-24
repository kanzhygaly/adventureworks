/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kz.ya.adventureworks.config;

import java.util.concurrent.Executors;
import kz.ya.adventureworks.mq.MessagePublisher;
import kz.ya.adventureworks.mq.RedisMessagePublisher;
import kz.ya.adventureworks.mq.ReviewWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

/**
 *
 * @author yerlana
 */
@Configuration
@ComponentScan("kz.ya.adventureworks")
public class RedisConfig {

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        final RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
        return template;
    }

    @Bean
    RedisMessageListenerContainer redisContainer() {
        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(jedisConnectionFactory());
        container.addMessageListener(reviewProcessListener(), reviewProcessTopic());
        container.setTaskExecutor(Executors.newFixedThreadPool(4));
        return container;
    }

    @Bean
    MessagePublisher redisPublisher() {
        return new RedisMessagePublisher(redisTemplate(), reviewProcessTopic());
    }
    
    @Bean
    MessageListenerAdapter reviewProcessListener() {
        return new MessageListenerAdapter(new ReviewWorker());
    }

    @Bean
    ChannelTopic reviewProcessTopic() {
        return new ChannelTopic("pubsub:jsa-channel");
    }
}
