package com.realestate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "properties")
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Double price;

    @Enumerated(EnumType.STRING)
    @Column(name = "property_type")
    private PropertyType propertyType;

    private Integer bedrooms;
    private Integer bathrooms;

    @Column(name = "area_sqft")
    private Double areaSqft;

    private String city;
    private String address;

    @Enumerated(EnumType.STRING)
    private PropertyStatus status;

    @Column(name = "view_count")
    private Integer viewCount = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    @JsonIgnore
    private User owner;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<PropertyImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Inquiry> inquiries;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Favorite> favorites;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Review> reviews;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null)    this.status    = PropertyStatus.PENDING;
        if (this.viewCount == null) this.viewCount = 0;
    }

    public enum PropertyType   { APARTMENT, VILLA, PLOT, COMMERCIAL }
    public enum PropertyStatus { PENDING, APPROVED, REJECTED, SOLD }

    public Property() {}

    public Long             getId()           { return id; }
    public String           getTitle()        { return title; }
    public String           getDescription()  { return description; }
    public Double           getPrice()        { return price; }
    public PropertyType     getPropertyType() { return propertyType; }
    public Integer          getBedrooms()     { return bedrooms; }
    public Integer          getBathrooms()    { return bathrooms; }
    public Double           getAreaSqft()     { return areaSqft; }
    public String           getCity()         { return city; }
    public String           getAddress()      { return address; }
    public PropertyStatus   getStatus()       { return status; }
    public Integer          getViewCount()    { return viewCount; }
    public User             getOwner()        { return owner; }
    public List<PropertyImage> getImages()    { return images; }
    public List<Inquiry>    getInquiries()    { return inquiries; }
    public List<Favorite>   getFavorites()    { return favorites; }
    public List<Review>     getReviews()      { return reviews; }
    public LocalDateTime    getCreatedAt()    { return createdAt; }

    public void setId(Long id)                         { this.id = id; }
    public void setTitle(String title)                 { this.title = title; }
    public void setDescription(String d)               { this.description = d; }
    public void setPrice(Double price)                 { this.price = price; }
    public void setPropertyType(PropertyType t)        { this.propertyType = t; }
    public void setBedrooms(Integer b)                 { this.bedrooms = b; }
    public void setBathrooms(Integer b)                { this.bathrooms = b; }
    public void setAreaSqft(Double a)                  { this.areaSqft = a; }
    public void setCity(String city)                   { this.city = city; }
    public void setAddress(String address)             { this.address = address; }
    public void setStatus(PropertyStatus status)       { this.status = status; }
    public void setViewCount(Integer v)                { this.viewCount = v; }
    public void setOwner(User owner)                   { this.owner = owner; }
    public void setImages(List<PropertyImage> images)  { this.images = images; }
    public void setInquiries(List<Inquiry> i)          { this.inquiries = i; }
    public void setFavorites(List<Favorite> f)         { this.favorites = f; }
    public void setReviews(List<Review> r)             { this.reviews = r; }
    public void setCreatedAt(LocalDateTime t)          { this.createdAt = t; }
}