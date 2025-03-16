package com.minorproject.inventory.entity;

import com.minorproject.inventory.dto.CategoryDTO;
import com.minorproject.inventory.enums.CategoryType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    
    @Column(nullable = false)
    private String categoryName;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CategoryType type;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    public CategoryDTO toCategoryDTO() {
        return CategoryDTO.builder()
                .id(id != null ? id.toString() : null)
                .categoryName(categoryName)
                .type(type != null ? type.name() : null)
                .build();
    }
}
