package kz.ya.adventureworks.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 *
 * @author yerlana
 */
public class ProductReviewDTO implements Serializable {

    @NotNull(message = "'productid' cannot be null")
    private Integer productid;

    @NotBlank
    @Size(min = 2, max = 50, message = "'name' must be between 2 and 50 characters")
    private String name;

    @NotBlank
    @Email(message = "'email' should be valid")
    private String email;

    @NotNull
    @Min(value = 1, message = "'rating' should not be less than 1")
    @Max(value = 5, message = "'rating' should not be greater than 5")
    private int rating;

    @NotBlank
    @Size(min = 10, max = 3850, message = "'review' must be between 10 and 3850 characters")
    private String review;

    public ProductReviewDTO() {
    }

    public ProductReviewDTO(String name, String email, Integer productid, int rating, String review) {
        this.name = name;
        this.email = email;
        this.productid = productid;
        this.rating = rating;
        this.review = review;
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
}
