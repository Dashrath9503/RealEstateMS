package com.realestate.dto;

import java.time.LocalDateTime;
import java.util.List;

public class PropertyResponse {

    private Long          id;
    private String        title;
    private String        description;
    private Double        price;
    private String        propertyType;
    private Integer       bedrooms;
    private Integer       bathrooms;
    private Double        areaSqft;
    private String        city;
    private String        address;
    private String        status;
    private Integer       viewCount;
    private Long          ownerId;
    private String        ownerName;
    private String        ownerEmail;
    private String        ownerPhone;
    private List<String>  imageUrls;
    private Double        averageRating;
    private LocalDateTime createdAt;

    public Long          getId()            { return id; }
    public String        getTitle()         { return title; }
    public String        getDescription()   { return description; }
    public Double        getPrice()         { return price; }
    public String        getPropertyType()  { return propertyType; }
    public Integer       getBedrooms()      { return bedrooms; }
    public Integer       getBathrooms()     { return bathrooms; }
    public Double        getAreaSqft()      { return areaSqft; }
    public String        getCity()          { return city; }
    public String        getAddress()       { return address; }
    public String        getStatus()        { return status; }
    public Integer       getViewCount()     { return viewCount; }
    public Long          getOwnerId()       { return ownerId; }
    public String        getOwnerName()     { return ownerName; }
    public String        getOwnerEmail()    { return ownerEmail; }
    public String        getOwnerPhone()    { return ownerPhone; }
    public List<String>  getImageUrls()     { return imageUrls; }
    public Double        getAverageRating() { return averageRating; }
    public LocalDateTime getCreatedAt()     { return createdAt; }

    public void setId(Long id)                        { this.id = id; }
    public void setTitle(String title)                { this.title = title; }
    public void setDescription(String description)    { this.description = description; }
    public void setPrice(Double price)                { this.price = price; }
    public void setPropertyType(String propertyType)  { this.propertyType = propertyType; }
    public void setBedrooms(Integer bedrooms)         { this.bedrooms = bedrooms; }
    public void setBathrooms(Integer bathrooms)       { this.bathrooms = bathrooms; }
    public void setAreaSqft(Double areaSqft)          { this.areaSqft = areaSqft; }
    public void setCity(String city)                  { this.city = city; }
    public void setAddress(String address)            { this.address = address; }
    public void setStatus(String status)              { this.status = status; }
    public void setViewCount(Integer viewCount)       { this.viewCount = viewCount; }
    public void setOwnerId(Long ownerId)              { this.ownerId = ownerId; }
    public void setOwnerName(String ownerName)        { this.ownerName = ownerName; }
    public void setOwnerEmail(String ownerEmail)      { this.ownerEmail = ownerEmail; }
    public void setOwnerPhone(String ownerPhone)      { this.ownerPhone = ownerPhone; }
    public void setImageUrls(List<String> imageUrls)  { this.imageUrls = imageUrls; }
    public void setAverageRating(Double averageRating){ this.averageRating = averageRating; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
