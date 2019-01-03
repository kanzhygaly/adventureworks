package kz.ya.adventureworks.service;

import kz.ya.adventureworks.entity.ProductReview;

/**
 *
 * @author yerlana
 */
public interface ProductReviewService {

    boolean isApproved(long productReviewId);

    void newProductReview(ProductReview productReview);

    void approveProductReview(ProductReview productReview);

    void declineProductReview(ProductReview productReview);
}
