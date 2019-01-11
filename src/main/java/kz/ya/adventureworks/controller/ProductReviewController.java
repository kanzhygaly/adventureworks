/*
  Rest Controller Class for Product Review
 */
package kz.ya.adventureworks.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import kz.ya.adventureworks.dto.BaseResponse;

import kz.ya.adventureworks.dto.ProductReviewDTO;
import kz.ya.adventureworks.entity.ProductReview;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import kz.ya.adventureworks.service.QueueService;
import kz.ya.adventureworks.service.ProductReviewService;

/**
 *
 * @author yerlana
 */
@RestController
@RequestMapping("/api/reviews")
//@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@Api(value = "/api/reviews", description = "Product Review Service", consumes = "application/json")
public class ProductReviewController {

    private final Logger logger = LoggerFactory.getLogger(ProductReviewController.class);
    @Autowired
    private ProductReviewService productReviewService;
    @Autowired
    private QueueService queueService;

    @ApiOperation(value = "Send new product review", response = ResponseEntity.class)
    @PostMapping
    public ResponseEntity<BaseResponse> newProductReview(@Valid @RequestBody ProductReviewDTO requestBody, BindingResult bindingResult) {
        // validate input request
        if (bindingResult.hasErrors()) {
            // print errors in logs
            bindingResult.getAllErrors().stream().map(ObjectError::toString).forEach(logger::warn);
            // return 204 No Content HTTP status code
            return ResponseEntity.noContent().build();
        }

        // Save the Product Review in DB
        ProductReview productReview = productReviewService.newProductReview(requestBody);

        // Put the Product Review onto a queue for processing
        queueService.publish(productReview);

        // return 200 OK HTTP status code
        return ResponseEntity.ok(new BaseResponse(true, productReview.getId()));
    }
}
