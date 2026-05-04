package com.realestate.service;

import com.realestate.dto.PropertyResponse;
import com.realestate.model.*;
import com.realestate.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteService {

    @Autowired private FavoriteRepository  favoriteRepository;
    @Autowired private PropertyRepository  propertyRepository;
    @Autowired private UserRepository      userRepository;
    @Autowired private PropertyService     propertyService;

    public String addFavorite(Long propertyId, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new RuntimeException("User not found"));
        Property property = propertyRepository.findById(propertyId)
            .orElseThrow(() -> new RuntimeException("Property not found"));

        if (favoriteRepository.existsByBuyer_IdAndProperty_Id(user.getId(), propertyId)) {
            return "Property already in favorites";
        }
        Favorite favorite = new Favorite();
        favorite.setBuyer(user);
        favorite.setProperty(property);
        favoriteRepository.save(favorite);
        return "Added to favorites!";
    }

    public String removeFavorite(Long propertyId, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new RuntimeException("User not found"));
        Favorite favorite = favoriteRepository
            .findByBuyer_IdAndProperty_Id(user.getId(), propertyId)
            .orElseThrow(() -> new RuntimeException("Favorite not found"));
        favoriteRepository.delete(favorite);
        return "Removed from favorites";
    }

    public List<PropertyResponse> getUserFavorites(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new RuntimeException("User not found"));
        return favoriteRepository.findByBuyer_Id(user.getId())
            .stream()
            .map(fav -> propertyService.mapToResponse(fav.getProperty()))
            .collect(Collectors.toList());
    }

    public boolean isFavorite(Long propertyId, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new RuntimeException("User not found"));
        return favoriteRepository.existsByBuyer_IdAndProperty_Id(user.getId(), propertyId);
    }
}
