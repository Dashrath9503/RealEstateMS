package com.realestate.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realestate.dto.InquiryRequest;
import com.realestate.dto.InquiryResponse;
import com.realestate.service.InquiryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/inquiries")
@CrossOrigin(origins = "*")
public class InquiryController {

    @Autowired
    private InquiryService inquiryService;

    // POST /inquiries/property/{propertyId}  →  Send inquiry to owner
    @PostMapping("/property/{propertyId}")
    public ResponseEntity<?> createInquiry(@PathVariable Long propertyId,
                                            @Valid @RequestBody InquiryRequest request,
                                            Authentication authentication) {
        try {
            return ResponseEntity.ok(inquiryService.createInquiry(propertyId, request, authentication.getName()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // GET /inquiries/my  →  Buyer's sent inquiries
    @GetMapping("/my")
    public ResponseEntity<?> getMyInquiries(Authentication authentication) {
        return ResponseEntity.ok(inquiryService.getBuyerInquiries(authentication.getName()));
    }

    // GET /inquiries/received  →  Owner's received inquiries
    @GetMapping("/received")
    public ResponseEntity<?> getReceivedInquiries(Authentication authentication) {
        return ResponseEntity.ok(inquiryService.getOwnerInquiries(authentication.getName()));
    }

    // GET /inquiries/property/{propertyId}  →  All inquiries on a property
    @GetMapping("/property/{propertyId}")
    public ResponseEntity<?> getPropertyInquiries(@PathVariable Long propertyId) {
        return ResponseEntity.ok(inquiryService.getPropertyInquiries(propertyId));
    }
    
    @PutMapping("/{inquiryId}/reply")
    public ResponseEntity<?> replyToInquiry(
            @PathVariable Long inquiryId,
            @RequestBody Map<String, String> request,
            @AuthenticationPrincipal UserDetails userDetails) {
        try {
            String replyMessage = request.get("replyMessage");
            InquiryResponse response = inquiryService
                .replyToInquiry(inquiryId, replyMessage, 
                    userDetails.getUsername());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(e.getMessage());
        }
    }
}
