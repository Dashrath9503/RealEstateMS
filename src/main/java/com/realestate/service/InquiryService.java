package com.realestate.service;

import com.realestate.dto.InquiryRequest;
import com.realestate.dto.InquiryResponse;
import com.realestate.model.*;
import com.realestate.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InquiryService {

    @Autowired private InquiryRepository  inquiryRepository;
    @Autowired private PropertyRepository propertyRepository;
    @Autowired private UserRepository     userRepository;
    @Autowired private EmailService       emailService;

    public InquiryResponse createInquiry(Long propertyId, InquiryRequest request, String buyerEmail) {
        User buyer = userRepository.findByEmail(buyerEmail)
            .orElseThrow(() -> new RuntimeException("User not found"));
        Property property = propertyRepository.findById(propertyId)
            .orElseThrow(() -> new RuntimeException("Property not found"));

        Inquiry inquiry = new Inquiry();
        inquiry.setProperty(property);
        inquiry.setBuyer(buyer);
        inquiry.setMessage(request.getMessage());
        inquiryRepository.save(inquiry);

        try {
            emailService.sendInquiryNotification(
                property.getOwner().getEmail(),
                property.getOwner().getName(),
                property.getTitle(),
                buyer.getName(),
                buyer.getEmail(),
                request.getMessage()
            );
        } catch (Exception e) {
            System.err.println("Email notification failed: " + e.getMessage());
        }
        return mapToResponse(inquiry);
    }

    public List<InquiryResponse> getBuyerInquiries(String buyerEmail) {
        User buyer = userRepository.findByEmail(buyerEmail)
            .orElseThrow(() -> new RuntimeException("User not found"));
        return inquiryRepository.findByBuyer_Id(buyer.getId())
            .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public List<InquiryResponse> getOwnerInquiries(String ownerEmail) {
        User owner = userRepository.findByEmail(ownerEmail)
            .orElseThrow(() -> new RuntimeException("User not found"));
        return inquiryRepository.findByProperty_Owner_Id(owner.getId())
            .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public List<InquiryResponse> getPropertyInquiries(Long propertyId) {
        return inquiryRepository.findByProperty_Id(propertyId)
            .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    private InquiryResponse mapToResponse(Inquiry i) {
        InquiryResponse r = new InquiryResponse();
        r.setId(i.getId());
        r.setMessage(i.getMessage());
        r.setStatus(i.getStatus() != null ? i.getStatus().name() : null);
        r.setCreatedAt(i.getCreatedAt());
        if (i.getBuyer() != null) {
            r.setBuyerId(i.getBuyer().getId());
            r.setBuyerName(i.getBuyer().getName());
            r.setBuyerEmail(i.getBuyer().getEmail());
        }
        if (i.getProperty() != null) {
            r.setPropertyId(i.getProperty().getId());
            r.setPropertyTitle(i.getProperty().getTitle());
        }
        return r;
    }
    
    public InquiryResponse replyToInquiry(
            Long inquiryId, 
            String replyMessage, 
            String ownerEmail) {
        
        Inquiry inquiry = inquiryRepository.findById(inquiryId)
            .orElseThrow(() -> 
                new RuntimeException("Inquiry not found"));
        
        // Status REPLIED kar do
        inquiry.setStatus(Inquiry.InquiryStatus.REPLIED);
        inquiryRepository.save(inquiry);
        
        // Buyer ko email bhejo
        try {
            User buyer = userRepository
                .findById(inquiry.getBuyer().getId())
                .orElseThrow();
            
            emailService.sendReplyNotification(
                buyer.getEmail(),
                buyer.getName(),
                replyMessage,
                inquiry.getProperty().getTitle()
            );
        } catch (Exception e) {
            System.out.println("Reply email failed: " 
                + e.getMessage());
        }
        
        return convertToResponse(inquiry);
    }
    
    private InquiryResponse convertToResponse(Inquiry inquiry) {
        InquiryResponse response = new InquiryResponse();
        response.setId(inquiry.getId());
        response.setMessage(inquiry.getMessage());
        response.setStatus(inquiry.getStatus().toString());
        response.setCreatedAt(inquiry.getCreatedAt());
        
        // Buyer details
        if (inquiry.getBuyer() != null) {
            response.setBuyerId(inquiry.getBuyer().getId());
            response.setBuyerName(inquiry.getBuyer().getName());
            response.setBuyerEmail(inquiry.getBuyer().getEmail());
        }
        
        // Property details
        if (inquiry.getProperty() != null) {
            response.setPropertyId(inquiry.getProperty().getId());
            response.setPropertyTitle(
                inquiry.getProperty().getTitle());
        }
        
        return response;
    }
}
