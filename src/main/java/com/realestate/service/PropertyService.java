package com.realestate.service;

import com.realestate.dto.PropertyRequest;
import com.realestate.dto.PropertyResponse;
import com.realestate.model.*;
import com.realestate.model.Property.PropertyStatus;
import com.realestate.model.Property.PropertyType;
import com.realestate.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PropertyService {

    @Autowired private PropertyRepository propertyRepository;
    @Autowired private UserRepository     userRepository;
    @Autowired private ReviewRepository   reviewRepository;

    @Value("${app.upload.dir}")
    private String uploadDir;

    public PropertyResponse createProperty(PropertyRequest request, String ownerEmail) {
        User owner = userRepository.findByEmail(ownerEmail)
            .orElseThrow(() -> new RuntimeException("Owner not found"));

        Property property = new Property();
        property.setTitle(request.getTitle());
        property.setDescription(request.getDescription());
        property.setPrice(request.getPrice());
        property.setPropertyType(PropertyType.valueOf(request.getPropertyType().toUpperCase()));
        property.setBedrooms(request.getBedrooms());
        property.setBathrooms(request.getBathrooms());
        property.setAreaSqft(request.getAreaSqft());
        property.setCity(request.getCity());
        property.setAddress(request.getAddress());
        property.setOwner(owner);
        property.setStatus(PropertyStatus.PENDING);
        property.setImages(new ArrayList<>());

        return mapToResponse(propertyRepository.save(property));
    }

    public List<PropertyResponse> getAllApproved() {
        return propertyRepository.findByStatus(PropertyStatus.APPROVED)
            .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public PropertyResponse getById(Long id) {
        Property property = propertyRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Property not found with id: " + id));
        property.setViewCount(property.getViewCount() + 1);
        propertyRepository.save(property);
        return mapToResponse(property);
    }

    public List<PropertyResponse> getOwnerProperties(String ownerEmail) {
        User owner = userRepository.findByEmail(ownerEmail)
            .orElseThrow(() -> new RuntimeException("Owner not found"));
        return propertyRepository.findByOwner_Id(owner.getId())
            .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public List<PropertyResponse> searchProperties(String city, Double minPrice, Double maxPrice,
                                                    String propertyType, Integer bedrooms,
                                                    Double minArea, Double maxArea) {
        PropertyType type = null;
        if (propertyType != null && !propertyType.isEmpty()) {
            type = PropertyType.valueOf(propertyType.toUpperCase());
        }
        return propertyRepository
            .searchProperties(city, minPrice, maxPrice, type, bedrooms, minArea, maxArea)
            .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public List<PropertyResponse> getTopProperties() {
        return propertyRepository
            .findTop6ByStatusOrderByViewCountDesc(PropertyStatus.APPROVED)
            .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public PropertyResponse updateProperty(Long id, PropertyRequest request, String userEmail) {
        Property property = propertyRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Property not found"));
        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new RuntimeException("User not found"));

        if (!property.getOwner().getId().equals(user.getId()) && user.getRole() != User.Role.ADMIN) {
            throw new RuntimeException("Unauthorized: you don't own this property");
        }
        property.setTitle(request.getTitle());
        property.setDescription(request.getDescription());
        property.setPrice(request.getPrice());
        property.setPropertyType(PropertyType.valueOf(request.getPropertyType().toUpperCase()));
        property.setBedrooms(request.getBedrooms());
        property.setBathrooms(request.getBathrooms());
        property.setAreaSqft(request.getAreaSqft());
        property.setCity(request.getCity());
        property.setAddress(request.getAddress());

        return mapToResponse(propertyRepository.save(property));
    }

    public void deleteProperty(Long id, String userEmail) {
        Property property = propertyRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Property not found"));
        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new RuntimeException("User not found"));
        if (!property.getOwner().getId().equals(user.getId()) && user.getRole() != User.Role.ADMIN) {
            throw new RuntimeException("Unauthorized: you don't own this property");
        }
        propertyRepository.delete(property);
    }

    public PropertyResponse uploadImages(Long propertyId, List<MultipartFile> files, String userEmail) throws IOException {
        Property property = propertyRepository.findById(propertyId)
            .orElseThrow(() -> new RuntimeException("Property not found"));
        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new RuntimeException("User not found"));
        if (!property.getOwner().getId().equals(user.getId()) && user.getRole() != User.Role.ADMIN) {
            throw new RuntimeException("Unauthorized");
        }

        Path uploadPath = Paths.get(uploadDir + "properties/" + propertyId + "/");
        Files.createDirectories(uploadPath);

        for (MultipartFile file : files) {
            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Files.copy(file.getInputStream(), uploadPath.resolve(filename),
                       StandardCopyOption.REPLACE_EXISTING);
            PropertyImage image = new PropertyImage();
            image.setProperty(property);
            image.setImageUrl("/uploads/properties/" + propertyId + "/" + filename);
            property.getImages().add(image);
        }
        return mapToResponse(propertyRepository.save(property));
    }

    public PropertyResponse mapToResponse(Property p) {
        PropertyResponse r = new PropertyResponse();
        r.setId(p.getId());
        r.setTitle(p.getTitle());
        r.setDescription(p.getDescription());
        r.setPrice(p.getPrice());
        r.setPropertyType(p.getPropertyType() != null ? p.getPropertyType().name() : null);
        r.setBedrooms(p.getBedrooms());
        r.setBathrooms(p.getBathrooms());
        r.setAreaSqft(p.getAreaSqft());
        r.setCity(p.getCity());
        r.setAddress(p.getAddress());
        r.setStatus(p.getStatus() != null ? p.getStatus().name() : null);
        r.setViewCount(p.getViewCount());
        r.setCreatedAt(p.getCreatedAt());

        if (p.getOwner() != null) {
            r.setOwnerId(p.getOwner().getId());
            r.setOwnerName(p.getOwner().getName());
            r.setOwnerEmail(p.getOwner().getEmail());
            r.setOwnerPhone(p.getOwner().getPhone());
        }
        if (p.getImages() != null) {
            r.setImageUrls(p.getImages().stream()
                .map(PropertyImage::getImageUrl).collect(Collectors.toList()));
        }
        Double avg = reviewRepository.findAverageRatingByPropertyId(p.getId());
        r.setAverageRating(avg);
        return r;
    }
}
