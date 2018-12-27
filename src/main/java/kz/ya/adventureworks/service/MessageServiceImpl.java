/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kz.ya.adventureworks.service;

import java.util.concurrent.CountDownLatch;
import kz.ya.adventureworks.config.RedisConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author yerlana
 */
@Service
public class MessageServiceImpl implements MessageService {
    
    @Autowired
    private final RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private final CountDownLatch latch;

    public MessageServiceImpl(RedisTemplate<String, Object> redisTemplate, CountDownLatch latch) {
        this.redisTemplate = redisTemplate;
        this.latch = latch;
    }
    
    @Override
    public void publish(final String message) {
        redisTemplate.convertAndSend(RedisConfig.REVIEW_PROCESS_TOPIC, message);
        try {
            latch.await();
            System.out.println("Latch released");
        } catch (InterruptedException ex) {
            System.err.println(ex.getMessage());
        }
        redisTemplate.convertAndSend(RedisConfig.NOTIFY_PROCESS_TOPIC, message);
    }
}
