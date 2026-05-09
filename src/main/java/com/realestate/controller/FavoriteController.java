package com.realestate.controller;

import com.realestate.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/favorites")
@CrossOrigin(origins = "*")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    // POST /favorites/{propertyId}  →  Add to favorites
    @PostMapping("/{propertyId}")
    public ResponseEntity<?> addFavorite(@PathVariable Long propertyId,
                                          Authentication authentication) {
        try {
            return ResponseEntity.ok(favoriteService.addFavorite(propertyId, authentication.getName()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // GET /favorites  →  Get all favorites
    @GetMapping
    public ResponseEntity<?> getMyFavorites(Authentication authentication) {
        return ResponseEntity.ok(favoriteService.getUserFavorites(authentication.getName()));
    }

    // DELETE /favorites/{propertyId}  →  Remove from favorites
    @DeleteMapping("/{propertyId}")
    public ResponseEntity<?> removeFavorite(@PathVariable Long propertyId,
                                             Authentication authentication) {
        try {
            return ResponseEntity.ok(favoriteService.removeFavorite(propertyId, authentication.getName()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // GET /favorites/check/{propertyId}  →  Check if favorited
    @GetMapping("/check/{propertyId}")
    public ResponseEntity<?> checkFavorite(@PathVariable Long propertyId,
                                            Authentication authentication) {
        return ResponseEntity.ok(favoriteService.isFavorite(propertyId, authentication.getName()));
    }
}
