package com.minorproject.inventory.dto;

import com.minorproject.inventory.entity.Category;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private String id;
    private String categoryName;
    
    public Category toCategory() {
        return Category.builder()
                .id(id != null ? UUID.fromString(id) : null)
                .categoryName(categoryName)
                .build();
    }
}
