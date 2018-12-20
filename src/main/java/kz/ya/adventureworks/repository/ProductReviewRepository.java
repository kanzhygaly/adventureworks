package kz.ya.adventureworks.repository;

import kz.ya.adventureworks.entity.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author yerlana
 */
@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {
}
