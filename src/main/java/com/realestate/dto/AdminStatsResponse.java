package com.realestate.dto;

public class AdminStatsResponse {

    private Long totalUsers;
    private Long totalProperties;
    private Long pendingProperties;
    private Long approvedProperties;
    private Long totalInquiries;
    private Long totalFavorites;
    private Long totalReviews;

    public AdminStatsResponse() {}

    public AdminStatsResponse(Long totalUsers, Long totalProperties,
                               Long pendingProperties, Long approvedProperties,
                               Long totalInquiries, Long totalFavorites, Long totalReviews) {
        this.totalUsers          = totalUsers;
        this.totalProperties     = totalProperties;
        this.pendingProperties   = pendingProperties;
        this.approvedProperties  = approvedProperties;
        this.totalInquiries      = totalInquiries;
        this.totalFavorites      = totalFavorites;
        this.totalReviews        = totalReviews;
    }

    public Long getTotalUsers()         { return totalUsers; }
    public Long getTotalProperties()    { return totalProperties; }
    public Long getPendingProperties()  { return pendingProperties; }
    public Long getApprovedProperties() { return approvedProperties; }
    public Long getTotalInquiries()     { return totalInquiries; }
    public Long getTotalFavorites()     { return totalFavorites; }
    public Long getTotalReviews()       { return totalReviews; }

    public void setTotalUsers(Long v)         { this.totalUsers = v; }
    public void setTotalProperties(Long v)    { this.totalProperties = v; }
    public void setPendingProperties(Long v)  { this.pendingProperties = v; }
    public void setApprovedProperties(Long v) { this.approvedProperties = v; }
    public void setTotalInquiries(Long v)     { this.totalInquiries = v; }
    public void setTotalFavorites(Long v)     { this.totalFavorites = v; }
    public void setTotalReviews(Long v)       { this.totalReviews = v; }
}
