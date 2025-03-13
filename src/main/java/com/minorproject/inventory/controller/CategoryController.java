package com.minorproject.inventory.controller;

import com.minorproject.inventory.dto.CategoryDTO;
import com.minorproject.inventory.dto.CustomResponse;
import com.minorproject.inventory.entity.Category;
import com.minorproject.inventory.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    
    private final CategoryRepository categoryRepository;
    
    @PostMapping
    public CustomResponse<CategoryDTO> addCategory(@RequestBody CategoryDTO dto) {
        //TODO Category and Selling Units data not isolated by user and add header accordingly in all
        //TODO Poora category hi theek krna hai, add krne p conflict aarha hai
        Category category = categoryRepository.save(dto.toCategory());
        return new CustomResponse<>(
                HttpStatus.OK,
                "Category added successfully",
                new CategoryDTO(category.getId().toString(), category.getCategoryName())
        );
    }
    
    @GetMapping
    public CustomResponse<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categories = categoryRepository.findAll().stream()
                .map(cat -> new CategoryDTO(cat.getId().toString(), cat.getCategoryName()))
                .collect(Collectors.toList());
        return new CustomResponse<>(
                HttpStatus.OK,
                "Categories fetched successfully.",
                categories
        );
    }
    
    @DeleteMapping("/{id}")
    public CustomResponse<Void> deleteCategory(@PathVariable String id) {
        categoryRepository.deleteById(UUID.fromString(id));
        return new CustomResponse<>(
                HttpStatus.OK,
                "Category deleted successfully!",
                null
        );
    }
}
