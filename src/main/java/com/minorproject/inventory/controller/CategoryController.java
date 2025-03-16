package com.minorproject.inventory.controller;

import com.minorproject.inventory.dto.CategoryDTO;
import com.minorproject.inventory.dto.CustomResponse;
import com.minorproject.inventory.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    
    private final CategoryService service;
    
    @GetMapping
    public CustomResponse<List<CategoryDTO>> fetchAllCategoriesHandler(
            @RequestHeader("Authorization") String token,
            @RequestParam(required = false) String type
    ) {
        return new CustomResponse<>(
                HttpStatus.OK,
                "Categories fetched successfully",
                service.fetchAllCategories(token, type)
        );
    }
    
    @PostMapping
    public CustomResponse<List<CategoryDTO>> createCategoriesHandler(
            @RequestHeader("Authorization") String token,
            @RequestBody List<CategoryDTO> categoryDTOs
    ) {
        return new CustomResponse<>(
                HttpStatus.CREATED,
                "Categories created successfully",
                service.createCategories(token, categoryDTOs)
        );
    }
    
    @PutMapping("/{categoryId}")
    public CustomResponse<CategoryDTO> updateCategoryHandler(
            @RequestHeader("Authorization") String token,
            @RequestBody CategoryDTO categoryDTO,
            @PathVariable String categoryId
    ) {
        return new CustomResponse<>(
                HttpStatus.OK,
                "Category updated successfully",
                service.updateCategory(token, categoryDTO, categoryId)
        );
    }
    
    @DeleteMapping("/{categoryId}")
    public CustomResponse<Void> deleteCategoryHandler(
            @RequestHeader("Authorization") String token,
            @PathVariable String categoryId
    ) {
        service.deleteCategory(token, categoryId);
        return new CustomResponse<>(
                HttpStatus.OK,
                "Category deleted successfully",
                null
        );
    }
}