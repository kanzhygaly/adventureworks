/*
 * Response for Product Review Request
 */
package kz.ya.adventureworks.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 *
 * @author yerlana
 */
@ApiModel(description = "API HTTP Response for Product review request.")
public class BaseResponse {
    
    @ApiModelProperty(value = "Result of adding new product review.")
    private boolean success;
    @ApiModelProperty(value = "Product review ID.")
    private int reviewID;

    public BaseResponse(boolean success, int reviewID) {
        this.success = success;
        this.reviewID = reviewID;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getReviewID() {
        return reviewID;
    }

    public void setReviewID(int reviewID) {
        this.reviewID = reviewID;
    }
}
