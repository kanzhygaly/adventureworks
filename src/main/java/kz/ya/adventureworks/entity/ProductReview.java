/*
  Product Review entity class
  Also used as DTO for Rest requests
 */
package kz.ya.adventureworks.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author yerlana
 */
@Entity
@Table(name = "productreview", schema="production")
//@JsonIgnoreProperties(value = {"reviewDate", "modifiedDate"}, allowGetters = true)
public class ProductReview extends AuditEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "productreviewid")
    private Integer id;
    
    @NotNull
    @Column(name = "productid", nullable = false)
    private Integer productid;
    
    @NotBlank
    @Column(name = "reviewername", nullable = false, length = 50)
    private String name;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "reviewdate", nullable = false, updatable = false)
    @CreatedDate
    private Date reviewDate;
    
    @NotBlank
    @Column(name = "emailaddress", nullable = false, length = 50)
    private String email;
    
    @NotNull
    @Column(name = "rating", nullable = false)
    @Min(value = 1)
    @Max(value = 5)
    private int rating;
    
    @NotBlank
    @Column(name = "comments", nullable = false, length = 3850)
    private String review;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modifieddate", nullable = false)
    @LastModifiedDate
    private Date modifiedDate;

    @Column(name = "reviewstatus", length = 10)
    @Enumerated(EnumType.STRING)
    private ReviewStatus reviewStatus;

    public ProductReview() {
    }

    public ProductReview(String name, String email, Integer productid, int rating, String review) {
        this.name = name;
        this.email = email;
        this.productid = productid;
        this.rating = rating;
        this.review = review;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductid() {
        return productid;
    }

    public void setProductid(Integer productid) {
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
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

    public ReviewStatus getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(ReviewStatus reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProductReview other = (ProductReview) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "ProductReview{" +
                "id=" + id +
                ", productid=" + productid +
                ", name='" + name + '\'' +
                ", reviewDate=" + reviewDate +
                ", email='" + email + '\'' +
                ", rating=" + rating +
                ", review='" + review + '\'' +
                ", modifiedDate=" + modifiedDate +
                ", reviewStatus=" + reviewStatus +
                '}';
    }
}
