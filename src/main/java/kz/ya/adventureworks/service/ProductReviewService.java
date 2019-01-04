package kz.ya.adventureworks.service;

import kz.ya.adventureworks.entity.ProductReview;

/**
 *
 * @author yerlana
 */
public interface ProductReviewService {

    boolean isApproved(long reviewId);

    ProductReview newProductReview(ProductReview review);

    ProductReview approveProductReview(ProductReview review);

    ProductReview declineProductReview(ProductReview review);
}
