package com.realestate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "inquiries")
public class Inquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    @JsonIgnore
    private Property property;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id", nullable = false)
    @JsonIgnore
    private User buyer;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String message;

    @Enumerated(EnumType.STRING)
    private InquiryStatus status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) this.status = InquiryStatus.PENDING;
    }

    public enum InquiryStatus { PENDING, REPLIED, CLOSED }

    public Inquiry() {}

    public Long          getId()        { return id; }
    public Property      getProperty()  { return property; }
    public User          getBuyer()     { return buyer; }
    public String        getMessage()   { return message; }
    public InquiryStatus getStatus()    { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setId(Long id)                  { this.id = id; }
    public void setProperty(Property property)  { this.property = property; }
    public void setBuyer(User buyer)            { this.buyer = buyer; }
    public void setMessage(String message)      { this.message = message; }
    public void setStatus(InquiryStatus status) { this.status = status; }
    public void setCreatedAt(LocalDateTime t)   { this.createdAt = t; }
}