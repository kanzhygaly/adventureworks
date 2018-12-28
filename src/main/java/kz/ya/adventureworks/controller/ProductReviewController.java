/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kz.ya.adventureworks.controller;

import java.net.URI;
import javax.validation.Valid;
import kz.ya.adventureworks.entity.ProductReview;
import kz.ya.adventureworks.repository.ProductReviewRepository;
import kz.ya.adventureworks.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
    private ProductReviewRepository productReviewRepository;
    @Autowired
    private MessageService messageService;

    @PostMapping
    public ResponseEntity<Object> newProductReview(@Valid @RequestBody ProductReview review, BindingResult bindingResult) {
        // validate input request
        if (bindingResult.hasErrors()) {
            // print errors in logs
            bindingResult.getAllErrors().forEach((objError) -> {
                logger.warn(objError.toString());
            });
            // return 204 HTTP status code
            return ResponseEntity.noContent().build();
        }

        // Save the Product Review in DB
        productReviewRepository.save(review);

        // Put the Product Review onto a queue for processing
        messageService.publish(review);

        // built location
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{reviewID}")
                .buildAndExpand(review.getId()).toUri();

        // return 201 HTTP status code
        return ResponseEntity.created(location).build();
    }
}
