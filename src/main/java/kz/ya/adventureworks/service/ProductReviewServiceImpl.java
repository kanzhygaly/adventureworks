/**
 * Product Review Service
 * Service class for product reviews
 */
package kz.ya.adventureworks.service;

import kz.ya.adventureworks.entity.ProductReview;
import kz.ya.adventureworks.entity.ReviewStatus;
import kz.ya.adventureworks.repository.ProductReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author yerlana
 */
@Service
public class ProductReviewServiceImpl implements ProductReviewService {

    private final ProductReviewRepository productReviewRepository;

    @Autowired
    public ProductReviewServiceImpl(ProductReviewRepository productReviewRepository) {
        this.productReviewRepository = productReviewRepository;
    }

    /**
     * Checks if Product Review was approved
     * 
     * @param productReviewId review ID
     * @return TRUE if review is approved, else FALSE
     */
    @Override
    public boolean isApproved(long productReviewId) {
        ProductReview productReview = productReviewRepository.getOne(productReviewId);
        return productReview.getReviewStatus().equals(ReviewStatus.APPROVED);
    }

    /**
     * Create new Product Review
     * 
     * @param productReview Product Review data
     */
    @Override
    public void newProductReview(ProductReview productReview) {
        productReview.setReviewStatus(ReviewStatus.NEW);
        productReviewRepository.save(productReview);
    }

    /**
     * Approve Product Review for publication
     * 
     * @param productReview Product Review data
     */
    @Override
    public void approveProductReview(ProductReview productReview) {
        productReview.setReviewStatus(ReviewStatus.APPROVED);
        productReviewRepository.save(productReview);
    }

    /**
     * Decline Product Review from publication
     * 
     * @param productReview Product Review data
     */
    @Override
    public void declineProductReview(ProductReview productReview) {
        productReview.setReviewStatus(ReviewStatus.DECLINED);
        productReviewRepository.save(productReview);
    }
}
