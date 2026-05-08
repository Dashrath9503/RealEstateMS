package com.realestate.controller;

import com.realestate.dto.*;
import com.realestate.service.PropertyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/properties")
@CrossOrigin(origins = "*")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    // POST /properties  →  Create property (OWNER only)
    @PostMapping
    public ResponseEntity<?> createProperty(@Valid @RequestBody PropertyRequest request,
                                             Authentication authentication) {
        try {
            PropertyResponse response = propertyService.createProperty(request, authentication.getName());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // GET /properties  →  List all approved properties
    @GetMapping
    public ResponseEntity<List<PropertyResponse>> getAllProperties() {
        return ResponseEntity.ok(propertyService.getAllApproved());
    }

    // GET /properties/top  →  Top 6 by views
    @GetMapping("/top")
    public ResponseEntity<List<PropertyResponse>> getTopProperties() {
        return ResponseEntity.ok(propertyService.getTopProperties());
    }

    // GET /properties/search  →  Search with filters
    @GetMapping("/search")
    public ResponseEntity<List<PropertyResponse>> searchProperties(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String propertyType,
            @RequestParam(required = false) Integer bedrooms,
            @RequestParam(required = false) Double minArea,
            @RequestParam(required = false) Double maxArea) {

        return ResponseEntity.ok(propertyService.searchProperties(
                city, minPrice, maxPrice, propertyType, bedrooms, minArea, maxArea));
    }

    // GET /properties/my  →  Owner's own properties
    @GetMapping("/my")
    public ResponseEntity<List<PropertyResponse>> getMyProperties(Authentication authentication) {
        return ResponseEntity.ok(propertyService.getOwnerProperties(authentication.getName()));
    }

    // GET /properties/{id}  →  Property detail
    @GetMapping("/{id}")
    public ResponseEntity<?> getPropertyById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(propertyService.getById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // PUT /properties/{id}  →  Update property
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProperty(@PathVariable Long id,
                                             @Valid @RequestBody PropertyRequest request,
                                             Authentication authentication) {
        try {
            return ResponseEntity.ok(propertyService.updateProperty(id, request, authentication.getName()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // DELETE /properties/{id}  →  Delete property
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProperty(@PathVariable Long id, Authentication authentication) {
        try {
            propertyService.deleteProperty(id, authentication.getName());
            return ResponseEntity.ok("Property deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // POST /properties/{id}/images  →  Upload images
    @PostMapping("/{id}/images")
    public ResponseEntity<?> uploadImages(@PathVariable Long id,
                                           @RequestParam("files") List<MultipartFile> files,
                                           Authentication authentication) {
        try {
            return ResponseEntity.ok(propertyService.uploadImages(id, files, authentication.getName()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
