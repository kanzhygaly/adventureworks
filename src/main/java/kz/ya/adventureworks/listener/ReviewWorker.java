/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kz.ya.adventureworks.listener;

import java.util.Date;
import java.util.concurrent.CountDownLatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

/**
 *
 * @author yerlana
 */
public class ReviewWorker implements MessageListener {
    
    private final CountDownLatch latch;

    @Autowired
    public ReviewWorker(CountDownLatch latch) {
        this.latch = latch;
    }
    
    @Override
    public void onMessage(final Message message, final byte[] pattern) {
//        messageList.add(message.toString());
        System.out.println("ReviewWorker: " + new String(message.getBody()) + " " + new Date());
        latch.countDown();
    }
}
