package com.minorproject.inventory.service;

import com.minorproject.inventory.dto.InventoryDTO;
import com.minorproject.inventory.entity.Category;
import com.minorproject.inventory.entity.Inventory;
import com.minorproject.inventory.entity.SellingUnit;
import com.minorproject.inventory.entity.User;
import com.minorproject.inventory.exception.ResourceNotFoundException;
import com.minorproject.inventory.repository.CategoryRepository;
import com.minorproject.inventory.repository.InventoryRepository;
import com.minorproject.inventory.repository.SellingUnitRepository;
import com.minorproject.inventory.utils.FirebaseAuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryService {
    
    private final InventoryRepository inventoryRepo;
    private final CategoryRepository categoryRepo;
    private final SellingUnitRepository sellingUnitRepo;
    private final FirebaseAuthUtil firebaseAuthUtil;
    
    public List<InventoryDTO> fetchAllInventory(String token) {
        User user = firebaseAuthUtil.getUserFromToken(token);
        
        List<Inventory> inventoryItems = inventoryRepo.findByUser(user);
        return inventoryItems.stream()
                .map(Inventory::toInventoryDTO)
                .collect(Collectors.toList());
    }
    
    public InventoryDTO createInventory(String token, InventoryDTO inventoryDTO) {
        User user = firebaseAuthUtil.getUserFromToken(token);
        
        Category category = categoryRepo.findByCategoryNameAndUser(inventoryDTO.getCategory(), user)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + inventoryDTO.getCategory()));
        
        SellingUnit sellingUnit = sellingUnitRepo.findByUnitNameAndUser(inventoryDTO.getSellPriceUnit(), user)
                .orElseThrow(() -> new ResourceNotFoundException("Selling unit not found: " + inventoryDTO.getSellPriceUnit()));
        
        Inventory inventory = inventoryDTO.toInventory();
        inventory.setUser(user);
        inventory.setCategory(category);
        inventory.setSellPriceUnit(sellingUnit);
        
        Inventory savedInventory = inventoryRepo.save(inventory);
        
        return savedInventory.toInventoryDTO();
    }
    
    public void deleteInventory(String token, String inventoryId) {
        User user = firebaseAuthUtil.getUserFromToken(token);
        
        Inventory inventory = inventoryRepo.findByIdAndUser(UUID.fromString(inventoryId), user)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory item not found with id: " + inventoryId));
        
        inventoryRepo.delete(inventory);
    }
    
    public InventoryDTO fetchInventoryById(String token, String inventoryId) {
        User user = firebaseAuthUtil.getUserFromToken(token);
        
        Inventory inventory = inventoryRepo.findByIdAndUser(UUID.fromString(inventoryId), user)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory item not found with id: " + inventoryId));
        
        return inventory.toInventoryDTO();
    }
    
    public InventoryDTO updateInventory(String token, String inventoryId, InventoryDTO inventoryDTO) {
        User user = firebaseAuthUtil.getUserFromToken(token);
        
        Inventory existingInventory = inventoryRepo.findByIdAndUser(UUID.fromString(inventoryId), user)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory item not found with id: " + inventoryId));
        
        // Get references to Category and SellingUnit
        Category category = categoryRepo.findByCategoryNameAndUser(inventoryDTO.getCategory(), user)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + inventoryDTO.getCategory()));
        
        SellingUnit sellingUnit = sellingUnitRepo.findByUnitNameAndUser(inventoryDTO.getSellPriceUnit(), user)
                .orElseThrow(() -> new ResourceNotFoundException("Selling unit not found: " + inventoryDTO.getSellPriceUnit()));
        
        // Update the inventory with new values
        existingInventory.setName(inventoryDTO.getName());
        existingInventory.setPhoto(inventoryDTO.getPhoto());
        existingInventory.setSellPrice(inventoryDTO.getSellPrice());
        existingInventory.setSellPriceUnit(sellingUnit);
        existingInventory.setMrp(inventoryDTO.getMrp());
        existingInventory.setPurchasePrice(inventoryDTO.getPurchasePrice());
        existingInventory.setTax(inventoryDTO.getTax());
        existingInventory.setItemCode(inventoryDTO.getItemCode());
        existingInventory.setBarcode(inventoryDTO.getBarcode());
        existingInventory.setItemDescription(inventoryDTO.getItemDescription());
        existingInventory.setLowStockAlertQuantity(inventoryDTO.getLowStockAlertQuantity());
        existingInventory.setExpiryDate(inventoryDTO.getExpiryDate());
        existingInventory.setQuantity(inventoryDTO.getQuantity());
        existingInventory.setStorageLocation(inventoryDTO.getStorageLocation());
        existingInventory.setCategory(category);
        
        Inventory updatedInventory = inventoryRepo.save(existingInventory);
        
        return updatedInventory.toInventoryDTO();
    }
}