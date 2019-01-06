/*
  Rest Controller Class for Product Review
 */
package kz.ya.adventureworks.controller;

import java.net.URI;
import javax.validation.Valid;

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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import kz.ya.adventureworks.service.QueueService;
import kz.ya.adventureworks.service.ProductReviewService;

/**
 *
 * @author yerlana
 */
@RestController
@RequestMapping("/api/reviews")
//@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class ProductReviewController {
    
    private final Logger logger = LoggerFactory.getLogger(ProductReviewController.class);
    @Autowired
    private ProductReviewService productReviewService;
    @Autowired
    private QueueService queueService;

    @PostMapping
    public ResponseEntity<Object> newProductReview(@Valid @RequestBody ProductReviewDTO dto, BindingResult bindingResult) {
        // validate input request
        if (bindingResult.hasErrors()) {
            // print errors in logs
            bindingResult.getAllErrors().stream().map(ObjectError::toString).forEach(logger::warn);
            // return 204 HTTP status code
            return ResponseEntity.noContent().build();
        }

        // Save the Product Review in DB
        ProductReview productReview = productReviewService.newProductReview(dto);
        
        // Put the Product Review onto a queue for processing
        queueService.publish(productReview);

        // built location
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{reviewID}")
                .buildAndExpand(productReview.getId()).toUri();

        // return 201 HTTP status code
        return ResponseEntity.created(location).build();
    }
}
