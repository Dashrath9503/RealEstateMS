package com.realestate.repository;

import com.realestate.model.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

    List<Inquiry> findByBuyer_Id(Long buyerId);
    
    List<Inquiry> findByBuyerId(Long buyerId);

    List<Inquiry> findByProperty_Id(Long propertyId);

    List<Inquiry> findByProperty_Owner_Id(Long ownerId);
}
