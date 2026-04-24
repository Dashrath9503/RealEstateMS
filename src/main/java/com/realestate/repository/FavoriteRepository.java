package com.realestate.repository;

import com.realestate.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    List<Favorite> findByBuyer_Id(Long userId);
    
    List<Favorite> findByBuyerId(Long buyerId);

    Optional<Favorite> findByBuyer_IdAndProperty_Id(Long userId, Long propertyId);

    boolean existsByBuyer_IdAndProperty_Id(Long userId, Long propertyId);

    long count();
}
