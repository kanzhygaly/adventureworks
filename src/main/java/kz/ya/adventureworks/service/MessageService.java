package kz.ya.adventureworks.service;

import kz.ya.adventureworks.entity.ProductReview;

/**
 *
 * @author yerlana
 */
public interface MessageService {
    
    void publish(final ProductReview review);
}
