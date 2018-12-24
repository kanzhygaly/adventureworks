/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kz.ya.adventureworks.mq;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

/**
 *
 * @author yerlana
 */
@Service
public class ReviewWorker implements MessageListener {
    
    @Override
    public void onMessage(final Message message, final byte[] pattern) {
//        messageList.add(message.toString());
        System.out.println("Message received: " + new String(message.getBody()));
    }
}
