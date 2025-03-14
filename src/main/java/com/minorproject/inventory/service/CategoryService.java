package com.minorproject.inventory.service;

import com.minorproject.inventory.dto.CategoryDTO;
import com.minorproject.inventory.entity.Category;
import com.minorproject.inventory.entity.User;
import com.minorproject.inventory.exception.ResourceNotFoundException;
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
    
    public List<CategoryDTO> fetchAllCategories(String token) {
        User user = firebaseAuthUtil.getUserFromToken(token);
        
        List<Category> categories = categoryRepo.findByUser(user);
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
                    .user(user)
                    .build();
            
            categoriesToSave.add(category);
        }
        
        List<Category> savedCategories = categoryRepo.saveAll(categoriesToSave);
        
        return savedCategories.stream()
                .map(Category::toCategoryDTO)
                .collect(Collectors.toList());
    }
    
    public void deleteCategory(String token, String categoryId) {
        User user = firebaseAuthUtil.getUserFromToken(token);
        
        Category category = categoryRepo.findByIdAndUser(UUID.fromString(categoryId), user)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));
        
        categoryRepo.delete(category);
    }
}