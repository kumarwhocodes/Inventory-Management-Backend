package com.minorproject.inventory.service;

import com.minorproject.inventory.dto.CategoryDTO;
import com.minorproject.inventory.entity.Category;
import com.minorproject.inventory.entity.User;
import com.minorproject.inventory.enums.CategoryType;
import com.minorproject.inventory.exception.ResourceNotFoundException;
import com.minorproject.inventory.exception.UserNotFoundException;
import com.minorproject.inventory.repository.CategoryRepository;
import com.minorproject.inventory.utils.FirebaseAuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    
    private final CategoryRepository categoryRepo;
    private final FirebaseAuthUtil firebaseAuthUtil;
    
    public List<CategoryDTO> fetchAllCategories(String token, String type) {
        User user = firebaseAuthUtil.getUserFromToken(token);
        
        List<Category> categories;
        if (type != null) {
            CategoryType categoryType;
            try {
                categoryType = CategoryType.valueOf(type.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new ResourceNotFoundException("Invalid category type: " + type);
            }
            categories = categoryRepo.findByUserAndType(user, categoryType);
        } else {
            // Fetch all categories for the user if no filter is applied
            categories = categoryRepo.findByUser(user);
        }
        
        return categories.stream()
                .map(Category::toCategoryDTO)
                .collect(Collectors.toList());
    }
    
    
    public List<CategoryDTO> createCategories(String token, List<CategoryDTO> categoryDTOs) {
        User user = firebaseAuthUtil.getUserFromToken(token);
        
        List<Category> categoriesToSave = new ArrayList<>();
        
        for (CategoryDTO categoryDTO : categoryDTOs) {
            Category category = Category.builder()
                    .categoryName(categoryDTO.getCategoryName())
                    .type(CategoryType.valueOf(categoryDTO.getType()))
                    .user(user)
                    .build();
            
            categoriesToSave.add(category);
        }
        
        List<Category> savedCategories = categoryRepo.saveAll(categoriesToSave);
        
        return savedCategories.stream()
                .map(Category::toCategoryDTO)
                .collect(Collectors.toList());
    }
    
    public CategoryDTO updateCategory(String token, CategoryDTO categoryDTO, String categoryId) {
        // Fetch category and ensure it belongs to the user
        User user = firebaseAuthUtil.getUserFromToken(token);
        Category existingCategory = categoryRepo.findById(UUID.fromString(categoryId))
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));
        if (!existingCategory.getUser().getUid().equals(user.getUid())) {
            throw new UserNotFoundException("Unauthorized: You can only update your own categories.");
        }
        
        // Update only non-null fields
        if (categoryDTO.getCategoryName() != null) existingCategory.setCategoryName(categoryDTO.getCategoryName());
        if (categoryDTO.getType() != null) existingCategory.setType(CategoryType.valueOf(categoryDTO.getType()));
        
        Category updatedCategory = categoryRepo.save(existingCategory);
        return updatedCategory.toCategoryDTO();
    }
    
    public void deleteCategory(String token, String categoryId) {
        User user = firebaseAuthUtil.getUserFromToken(token);
        
        Category category = categoryRepo.findByIdAndUser(UUID.fromString(categoryId), user)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));
        
        categoryRepo.delete(category);
    }
}