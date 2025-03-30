package com.minorproject.inventory.repository;

import com.minorproject.inventory.entity.Inventory;
import com.minorproject.inventory.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, UUID> {
    List<Inventory> findByUser(User user);
    
    Optional<Inventory> findByIdAndUser(UUID id, User user);
}