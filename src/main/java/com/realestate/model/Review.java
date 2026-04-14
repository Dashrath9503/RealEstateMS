package com.realestate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    @JsonIgnore
    private Property property;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User reviewer;

    @Column(nullable = false)
    private Integer rating;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() { this.createdAt = LocalDateTime.now(); }

    public Review() {}

    public Long          getId()        { return id; }
    public Property      getProperty()  { return property; }
    public User          getReviewer()  { return reviewer; }
    public Integer       getRating()    { return rating; }
    public String        getComment()   { return comment; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setId(Long id)                { this.id = id; }
    public void setProperty(Property p)       { this.property = p; }
    public void setReviewer(User reviewer)    { this.reviewer = reviewer; }
    public void setRating(Integer rating)     { this.rating = rating; }
    public void setComment(String comment)    { this.comment = comment; }
    public void setCreatedAt(LocalDateTime t) { this.createdAt = t; }
}