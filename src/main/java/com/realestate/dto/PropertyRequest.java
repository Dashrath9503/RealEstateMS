package com.realestate.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PropertyRequest {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotNull(message = "Price is required")
    private Double price;

    @NotBlank(message = "Property type is required")
    private String propertyType;

    private Integer bedrooms;
    private Integer bathrooms;
    private Double  areaSqft;

    @NotBlank(message = "City is required")
    private String city;

    private String address;

    public String  getTitle()        { return title; }
    public String  getDescription()  { return description; }
    public Double  getPrice()        { return price; }
    public String  getPropertyType() { return propertyType; }
    public Integer getBedrooms()     { return bedrooms; }
    public Integer getBathrooms()    { return bathrooms; }
    public Double  getAreaSqft()     { return areaSqft; }
    public String  getCity()         { return city; }
    public String  getAddress()      { return address; }

    public void setTitle(String title)              { this.title = title; }
    public void setDescription(String description)  { this.description = description; }
    public void setPrice(Double price)              { this.price = price; }
    public void setPropertyType(String propertyType){ this.propertyType = propertyType; }
    public void setBedrooms(Integer bedrooms)       { this.bedrooms = bedrooms; }
    public void setBathrooms(Integer bathrooms)     { this.bathrooms = bathrooms; }
    public void setAreaSqft(Double areaSqft)        { this.areaSqft = areaSqft; }
    public void setCity(String city)                { this.city = city; }
    public void setAddress(String address)          { this.address = address; }
}
