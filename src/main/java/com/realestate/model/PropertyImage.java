package com.realestate.model;

import jakarta.persistence.*;

@Entity
@Table(name = "property_images")
public class PropertyImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    public PropertyImage() {}

    public Long     getId()       { return id; }
    public Property getProperty() { return property; }
    public String   getImageUrl() { return imageUrl; }

    public void setId(Long id)              { this.id = id; }
    public void setProperty(Property p)     { this.property = p; }
    public void setImageUrl(String imageUrl){ this.imageUrl = imageUrl; }
}
