package com.realestate.dto;

import java.time.LocalDateTime;

public class InquiryResponse {

    private Long          id;
    private String        message;
    private String        status;
    private Long          buyerId;
    private String        buyerName;
    private String        buyerEmail;
    private Long          propertyId;
    private String        propertyTitle;
    private LocalDateTime createdAt;

    public Long          getId()            { return id; }
    public String        getMessage()       { return message; }
    public String        getStatus()        { return status; }
    public Long          getBuyerId()       { return buyerId; }
    public String        getBuyerName()     { return buyerName; }
    public String        getBuyerEmail()    { return buyerEmail; }
    public Long          getPropertyId()    { return propertyId; }
    public String        getPropertyTitle() { return propertyTitle; }
    public LocalDateTime getCreatedAt()     { return createdAt; }

    public void setId(Long id)                          { this.id = id; }
    public void setMessage(String message)              { this.message = message; }
    public void setStatus(String status)                { this.status = status; }
    public void setBuyerId(Long buyerId)                { this.buyerId = buyerId; }
    public void setBuyerName(String buyerName)          { this.buyerName = buyerName; }
    public void setBuyerEmail(String buyerEmail)        { this.buyerEmail = buyerEmail; }
    public void setPropertyId(Long propertyId)          { this.propertyId = propertyId; }
    public void setPropertyTitle(String propertyTitle)  { this.propertyTitle = propertyTitle; }
    public void setCreatedAt(LocalDateTime createdAt)   { this.createdAt = createdAt; }
}
