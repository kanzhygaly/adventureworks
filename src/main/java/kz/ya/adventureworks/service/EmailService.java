package kz.ya.adventureworks.service;

import kz.ya.adventureworks.entity.ProductReview;

/**
 *
 * @author yerlana
 */
public interface EmailService {
    
    void sendNotification(ProductReview review);
}
