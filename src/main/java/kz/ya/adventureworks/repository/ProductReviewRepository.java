/**
 * Repository for Product Review entity
 */
package kz.ya.adventureworks.repository;

import java.util.Optional;
import kz.ya.adventureworks.entity.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author yerlana
 */
@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {
    
    @Query("SELECT r FROM ProductReview r WHERE r.name = :name and r.email = :email and r.productid = :productid and r.rating = :rating")
    Optional<ProductReview> findOne(@Param("name") String name, @Param("email") String email,
            @Param("productid") Integer productid, @Param("rating") int rating);
}
