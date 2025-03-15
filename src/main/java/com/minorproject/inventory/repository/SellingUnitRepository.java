package com.minorproject.inventory.repository;

import com.minorproject.inventory.entity.Category;
import com.minorproject.inventory.entity.SellingUnit;
import com.minorproject.inventory.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SellingUnitRepository extends JpaRepository<SellingUnit, UUID> {
    List<SellingUnit> findByUser(User user);
    
    Optional<SellingUnit> findByIdAndUser(UUID id, User user);
    
    Optional<SellingUnit> findByUnitNameAndUser(String sellPriceUnit, User user);
}