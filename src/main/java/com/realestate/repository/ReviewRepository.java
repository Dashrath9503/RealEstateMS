package com.realestate.repository;

import com.realestate.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByProperty_Id(Long propertyId);
    
    List<Review> findByReviewerId(Long reviewerId);

    boolean existsByProperty_IdAndReviewer_Id(Long propertyId, Long userId);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.property.id = :propertyId")
    Double findAverageRatingByPropertyId(@Param("propertyId") Long propertyId);
}
