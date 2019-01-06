package kz.ya.adventureworks.service;

import kz.ya.adventureworks.dto.ProductReviewDTO;
import kz.ya.adventureworks.entity.ProductReview;

/**
 *
 * @author yerlana
 */
public interface ProductReviewService {

    boolean isApproved(Integer reviewId);

    ProductReview newProductReview(ProductReviewDTO dto);

    ProductReview approveProductReview(ProductReview review);

    ProductReview declineProductReview(ProductReview review);
}
