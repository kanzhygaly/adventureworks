package kz.ya.adventureworks.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 *
 * @author yerlana
 */
@ApiModel(description = "Customer review of product they have purchased.")
public class ProductReviewDTO implements Serializable {

    @ApiModelProperty(value = "Product identification number.", required = true)
    @NotNull(message = "'productid' cannot be null")
    private Integer productid;

    @ApiModelProperty(value = "Name of the reviewer.", required = true)
    @NotBlank
    @Size(min = 2, max = 50, message = "'name' must be between 2 and 50 characters")
    private String name;

    @ApiModelProperty(value = "Reviewer's email address", required = true)
    @NotBlank
    @Email(message = "'email' should be valid")
    private String email;

    @ApiModelProperty(value = "Product rating given by the reviewer. Scale is 1 to 5 with 5 as the highest rating.", required = true)
    @NotNull
    @Min(value = 1, message = "'rating' should not be less than 1")
    @Max(value = 5, message = "'rating' should not be greater than 5")
    private int rating;

    @ApiModelProperty(value = "Reviewer's comments", required = true)
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
