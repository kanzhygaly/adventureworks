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
public class ReviewServiceImpl implements ReviewService {

    private final ProductReviewRepository productReviewRepository;

    @Autowired
    public ReviewServiceImpl(ProductReviewRepository productReviewRepository) {
        this.productReviewRepository = productReviewRepository;
    }

    @Override
    public boolean isApproved(long productReviewId) {
        ProductReview productReview = productReviewRepository.getOne(productReviewId);
        return productReview.getReviewStatus().equals(ReviewStatus.APPROVED);
    }

    @Override
    public void newProductReview(ProductReview productReview) {
        productReview.setReviewStatus(ReviewStatus.NEW);
        productReviewRepository.save(productReview);
    }

    @Override
    public void approveProductReview(ProductReview productReview) {
        productReview.setReviewStatus(ReviewStatus.APPROVED);
        productReviewRepository.save(productReview);
    }

    @Override
    public void declineProductReview(ProductReview productReview) {
        productReview.setReviewStatus(ReviewStatus.DECLINED);
        productReviewRepository.save(productReview);
    }
}
