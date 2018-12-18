package kz.ya.adventureworks.entity;

import javax.persistence.*;
import java.util.Date;

/**
 *
 * @author yerlana
 */
@Entity
public class ProductReview {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "ProductReviewID")
    private Long id;
    @Column(name = "ProductID", nullable = false)
    private Long productId;
    @Column(name = "ReviewerName", nullable = false)
    private String name;
    @Column(name = "ReviewDate", nullable = false)
    private Date reviewDate;
    @Column(name = "EmailAddress", nullable = false, length = 50)
    private String email;
    @Column(name = "Rating", nullable = false)
    private Integer rating;
    @Column(name = "Comments", nullable = false, length = 3850)
    private String comments;
    @Column(name = "ModifiedDate", nullable = false)
    private Date modifiedDate;
}
