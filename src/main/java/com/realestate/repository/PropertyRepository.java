package com.realestate.repository;

import com.realestate.model.Property;
import com.realestate.model.Property.PropertyStatus;
import com.realestate.model.Property.PropertyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long>,
        JpaSpecificationExecutor<Property> {

    List<Property> findByOwner_Id(Long ownerId);

    List<Property> findByStatus(PropertyStatus status);

    long countByStatus(PropertyStatus status);

    @Query("SELECT p FROM Property p WHERE p.status = 'APPROVED' " +
           "AND (:city IS NULL OR LOWER(p.city) LIKE LOWER(CONCAT('%', :city, '%'))) " +
           "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
           "AND (:maxPrice IS NULL OR p.price <= :maxPrice) " +
           "AND (:propertyType IS NULL OR p.propertyType = :propertyType) " +
           "AND (:bedrooms IS NULL OR p.bedrooms >= :bedrooms) " +
           "AND (:minArea IS NULL OR p.areaSqft >= :minArea) " +
           "AND (:maxArea IS NULL OR p.areaSqft <= :maxArea) " +
           "ORDER BY p.createdAt DESC")
    List<Property> searchProperties(
            @Param("city") String city,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("propertyType") PropertyType propertyType,
            @Param("bedrooms") Integer bedrooms,
            @Param("minArea") Double minArea,
            @Param("maxArea") Double maxArea
    );

    List<Property> findTop6ByStatusOrderByViewCountDesc(PropertyStatus status);
}
