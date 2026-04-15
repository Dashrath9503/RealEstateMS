package com.realestate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "favorites", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "property_id"})
})
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User buyer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    @JsonIgnore
    private Property property;

    @Column(name = "added_at")
    private LocalDateTime addedAt;

    @PrePersist
    protected void onCreate() { this.addedAt = LocalDateTime.now(); }

    public Favorite() {}

    public Long          getId()       { return id; }
    public User          getBuyer()    { return buyer; }
    public Property      getProperty() { return property; }
    public LocalDateTime getAddedAt()  { return addedAt; }

    public void setId(Long id)              { this.id = id; }
    public void setBuyer(User buyer)        { this.buyer = buyer; }
    public void setProperty(Property p)     { this.property = p; }
    public void setAddedAt(LocalDateTime t) { this.addedAt = t; }
}