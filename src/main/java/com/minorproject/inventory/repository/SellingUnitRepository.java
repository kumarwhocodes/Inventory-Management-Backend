package com.minorproject.inventory.repository;

import com.minorproject.inventory.entity.SellingUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SellingUnitRepository extends JpaRepository<SellingUnit, UUID> {
}
