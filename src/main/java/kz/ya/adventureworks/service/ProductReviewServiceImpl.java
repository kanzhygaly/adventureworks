/**
 * Product Review Service
 * Service class for product reviews
 */
package kz.ya.adventureworks.service;

import kz.ya.adventureworks.entity.ProductReview;
import kz.ya.adventureworks.entity.ReviewStatus;
import kz.ya.adventureworks.exception.ProductReviewNotFoundException;
import kz.ya.adventureworks.repository.ProductReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author yerlana
 */
@Service
public class ProductReviewServiceImpl implements ProductReviewService {

    @Autowired
    private ProductReviewRepository productReviewRepository;

    /**
     * Checks if Product Review was approved
     * 
     * @param reviewId review ID
     * @return TRUE if review is approved, else FALSE
     */
    @Override
    public boolean isApproved(long reviewId) {
        ProductReview productReview = productReviewRepository.findById(reviewId)
                .orElseThrow(() -> new ProductReviewNotFoundException(
                        "Product Review not found with id " + reviewId));
        return productReview.getReviewStatus().equals(ReviewStatus.APPROVED);
    }

    /**
     * Create new Product Review
     * 
     * @param review Product Review data
     * @return created Product Review object
     */
    @Override
    public ProductReview newProductReview(ProductReview review) {
        review.setReviewStatus(ReviewStatus.NEW);
        return productReviewRepository.save(review);
    }

    /**
     * Approve Product Review for publication
     * 
     * @param review Product Review data
     * @return update Product Review object
     */
    @Override
    public ProductReview approveProductReview(ProductReview review) {
        review.setReviewStatus(ReviewStatus.APPROVED);
        return productReviewRepository.save(review);
    }

    /**
     * Decline Product Review from publication
     * 
     * @param review Product Review data
     * @return update Product Review object
     */
    @Override
    public ProductReview declineProductReview(ProductReview review) {
        review.setReviewStatus(ReviewStatus.DECLINED);
        return productReviewRepository.save(review);
    }
}
