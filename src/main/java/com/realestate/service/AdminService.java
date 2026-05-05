package com.realestate.service;

import com.realestate.dto.AdminStatsResponse;
import com.realestate.dto.PropertyResponse;
import com.realestate.model.*;
import com.realestate.model.Property.PropertyStatus;
import com.realestate.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

    @Autowired private UserRepository     userRepository;
    @Autowired private PropertyRepository propertyRepository;
    @Autowired private InquiryRepository  inquiryRepository;
    @Autowired private FavoriteRepository favoriteRepository;
    @Autowired private ReviewRepository   reviewRepository;
    @Autowired private PropertyService    propertyService;
    @Autowired private EmailService       emailService;

    public AdminStatsResponse getStats() {
        return new AdminStatsResponse(
            userRepository.count(),
            propertyRepository.count(),
            propertyRepository.countByStatus(PropertyStatus.PENDING),
            propertyRepository.countByStatus(PropertyStatus.APPROVED),
            inquiryRepository.count(),
            favoriteRepository.count(),
            reviewRepository.count()
        );
    }

    public PropertyResponse approveProperty(Long propertyId) {
        Property property = propertyRepository.findById(propertyId)
            .orElseThrow(() -> new RuntimeException("Property not found"));
        property.setStatus(PropertyStatus.APPROVED);
        propertyRepository.save(property);
        try {
            emailService.sendPropertyApprovalEmail(
                property.getOwner().getEmail(),
                property.getOwner().getName(),
                property.getTitle()
            );
        } catch (Exception e) {
            System.err.println("Email failed: " + e.getMessage());
        }
        return propertyService.mapToResponse(property);
    }

    public PropertyResponse rejectProperty(Long propertyId) {
        Property property = propertyRepository.findById(propertyId)
            .orElseThrow(() -> new RuntimeException("Property not found"));
        property.setStatus(PropertyStatus.REJECTED);
        propertyRepository.save(property);
        return propertyService.mapToResponse(property);
    }

    public List<PropertyResponse> getPendingProperties() {
        return propertyRepository.findByStatus(PropertyStatus.PENDING)
            .stream().map(propertyService::mapToResponse).collect(Collectors.toList());
    }

    public List<PropertyResponse> getAllProperties() {
        return propertyRepository.findAll()
            .stream().map(propertyService::mapToResponse).collect(Collectors.toList());
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Pehle saari related data delete karo
        // 1. Favorites delete karo
        favoriteRepository.deleteAll(
            favoriteRepository.findByBuyerId(userId)
        );
        
        // 2. Inquiries delete karo  
        inquiryRepository.deleteAll(
            inquiryRepository.findByBuyerId(userId)
        );
        
        // 3. Reviews delete karo
        reviewRepository.deleteAll(
            reviewRepository.findByReviewerId(userId)
        );
        
        // 4. Ab user delete karo
        userRepository.delete(user);
    }

    public void deleteProperty(Long propertyId) {
        propertyRepository.findById(propertyId)
            .orElseThrow(() -> new RuntimeException("Property not found"));
        propertyRepository.deleteById(propertyId);
    }
}
