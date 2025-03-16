package com.minorproject.inventory.dto;

import com.minorproject.inventory.entity.Category;
import com.minorproject.inventory.enums.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private String id;
    private String categoryName;
    private String type;
    
    public Category toCategory() {
        return Category.builder()
                .id(id != null ? UUID.fromString(id) : null)
                .categoryName(categoryName)
                .type(type != null ? CategoryType.valueOf(type) : null)
                .build();
    }
}
