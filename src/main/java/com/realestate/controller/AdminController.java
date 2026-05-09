package com.realestate.controller;

import com.realestate.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // GET /admin/stats  →  Platform overview
    @GetMapping("/stats")
    public ResponseEntity<?> getStats() {
        return ResponseEntity.ok(adminService.getStats());
    }

    // GET /admin/properties  →  All properties
    @GetMapping("/properties")
    public ResponseEntity<?> getAllProperties() {
        return ResponseEntity.ok(adminService.getAllProperties());
    }

    // GET /admin/properties/pending  →  Pending approvals
    @GetMapping("/properties/pending")
    public ResponseEntity<?> getPendingProperties() {
        return ResponseEntity.ok(adminService.getPendingProperties());
    }

    // PUT /admin/properties/{id}/approve  →  Approve a property
    @PutMapping("/properties/{id}/approve")
    public ResponseEntity<?> approveProperty(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(adminService.approveProperty(id));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // PUT /admin/properties/{id}/reject  →  Reject a property
    @PutMapping("/properties/{id}/reject")
    public ResponseEntity<?> rejectProperty(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(adminService.rejectProperty(id));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // DELETE /admin/properties/{id}  →  Delete any property
    @DeleteMapping("/properties/{id}")
    public ResponseEntity<?> deleteProperty(@PathVariable Long id) {
        try {
            adminService.deleteProperty(id);
            return ResponseEntity.ok("Property deleted");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // GET /admin/users  →  All users
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    // DELETE /admin/users/{id}  →  Delete user
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            adminService.deleteUser(id);
            return ResponseEntity.ok("User deleted");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
