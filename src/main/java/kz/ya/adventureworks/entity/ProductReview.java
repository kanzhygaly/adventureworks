package kz.ya.adventureworks.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.util.Date;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

/**
 *
 * @author yerlana
 */
@Entity
@Table(name = "ProductReview")
@JsonIgnoreProperties(
        value = {"id", "reviewDate", "rating", "modifiedDate"},
        allowGetters = true
)
public class ProductReview extends AuditEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ProductReviewID")
    private Long id;
    
    @Column(name = "ProductID", nullable = false)
    private Long productid;
    
    @Column(name = "ReviewerName", nullable = false)
    private String name;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ReviewDate", nullable = false, updatable = false)
    @CreatedDate
    private Date reviewDate;
    
    @Column(name = "EmailAddress", nullable = false, length = 50)
    private String email;
    
    @Column(name = "Rating", nullable = false)
    private Integer rating;
    
    @Column(name = "Comments", nullable = false, length = 3850)
    private String review;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ModifiedDate", nullable = false)
    @LastModifiedDate
    private Date modifiedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductid() {
        return productid;
    }

    public void setProductid(Long productid) {
        this.productid = productid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
