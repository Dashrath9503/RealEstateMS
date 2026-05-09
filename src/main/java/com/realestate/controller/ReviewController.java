package com.realestate.controller;

import com.realestate.dto.ReviewRequest;
import com.realestate.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
@CrossOrigin(origins = "*")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    // POST /reviews/property/{propertyId}  →  Add review
    @PostMapping("/property/{propertyId}")
    public ResponseEntity<?> addReview(@PathVariable Long propertyId,
                                        @Valid @RequestBody ReviewRequest request,
                                        Authentication authentication) {
        try {
            return ResponseEntity.ok(reviewService.addReview(propertyId, request, authentication.getName()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // GET /reviews/property/{propertyId}  →  Get all reviews for a property
    @GetMapping("/property/{propertyId}")
    public ResponseEntity<?> getPropertyReviews(@PathVariable Long propertyId) {
        return ResponseEntity.ok(reviewService.getPropertyReviews(propertyId));
    }
}
