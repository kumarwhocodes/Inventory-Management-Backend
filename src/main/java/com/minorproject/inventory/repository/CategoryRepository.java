package com.minorproject.inventory.repository;

import com.minorproject.inventory.entity.Category;
import com.minorproject.inventory.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    List<Category> findByUser(User user);
    
    Optional<Category> findByIdAndUser(UUID uuid, User user);
    
    Optional<Category> findByCategoryNameAndUser(String category, User user);
}
