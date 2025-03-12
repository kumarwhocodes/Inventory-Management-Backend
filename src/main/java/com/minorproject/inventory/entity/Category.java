package com.minorproject.inventory.entity;

import com.minorproject.inventory.dto.CategoryDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category_db")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, unique = true)
    private String categoryName;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    public CategoryDTO toCategoryDTO() {
        return CategoryDTO.builder()
                .id(id != null ? id.toString() : null)
                .categoryName(categoryName)
                .build();
    }
}
