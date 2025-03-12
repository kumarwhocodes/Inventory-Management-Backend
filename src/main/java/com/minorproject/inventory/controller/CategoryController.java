package com.minorproject.inventory.controller;

import com.minorproject.inventory.dto.CategoryDTO;
import com.minorproject.inventory.entity.Category;
import com.minorproject.inventory.repository.CategoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    
    private final CategoryRepository categoryRepository;
    
    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    
    @PostMapping
    public ResponseEntity<CategoryDTO> addCategory(@RequestBody CategoryDTO dto) {
        Category category = categoryRepository.save(dto.toCategory());
        return ResponseEntity.ok(new CategoryDTO(category.getId().toString(), category.getCategoryName()));
    }
    
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categories = categoryRepository.findAll().stream()
                .map(cat -> new CategoryDTO(cat.getId().toString(), cat.getCategoryName()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(categories);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable String id) {
        categoryRepository.deleteById(UUID.fromString(id));
        return ResponseEntity.noContent().build();
    }
}
