package com.minorproject.inventory.repository;

import com.minorproject.inventory.entity.Inventory;
import com.minorproject.inventory.entity.User;
import com.minorproject.inventory.entity.report.StockSummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StockSummaryRepository extends JpaRepository<StockSummary, UUID> {
    Optional<StockSummary> findByUserAndInventory(User user, Inventory inventory);
    
    List<StockSummary> findByUser(User user);
    
}
