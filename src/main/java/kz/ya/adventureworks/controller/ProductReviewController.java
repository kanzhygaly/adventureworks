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

    @Autowired
    private ProductReviewRepository productReviewRepository;

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody ProductReview review, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.noContent().build();
        }

        productReviewRepository.save(review);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{reviewID}")
                .buildAndExpand(review.getId()).toUri();

        return ResponseEntity.created(location).build();
    }
}
