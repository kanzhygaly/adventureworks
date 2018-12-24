/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kz.ya.adventureworks.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

/**
 *
 * @author yerlana
 */
@Service
public class RedisMessagePublisher implements MessagePublisher {
    
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private ChannelTopic topic;

    public RedisMessagePublisher() {
    }

    public RedisMessagePublisher(RedisTemplate<String, Object> redisTemplate, ChannelTopic topic) {
        this.redisTemplate = redisTemplate;
        this.topic = topic;
    }
    
    @Override
    public void publish(final String message) {
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }
}
