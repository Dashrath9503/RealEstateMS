package com.realestate.service;

import com.realestate.dto.ReviewRequest;
import com.realestate.model.*;
import com.realestate.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired private ReviewRepository   reviewRepository;
    @Autowired private PropertyRepository propertyRepository;
    @Autowired private UserRepository     userRepository;

    public String addReview(Long propertyId, ReviewRequest request, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new RuntimeException("User not found"));
        Property property = propertyRepository.findById(propertyId)
            .orElseThrow(() -> new RuntimeException("Property not found"));

        if (reviewRepository.existsByProperty_IdAndReviewer_Id(propertyId, user.getId())) {
            throw new RuntimeException("You have already reviewed this property");
        }
        Review review = new Review();
        review.setProperty(property);
        review.setReviewer(user);
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        reviewRepository.save(review);
        return "Review submitted successfully!";
    }

    public List<Review> getPropertyReviews(Long propertyId) {
        return reviewRepository.findByProperty_Id(propertyId);
    }
}
