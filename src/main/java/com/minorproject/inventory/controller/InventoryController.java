package com.minorproject.inventory.controller;

import com.minorproject.inventory.dto.CustomResponse;
import com.minorproject.inventory.dto.InventoryDTO;
import com.minorproject.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {
    
    private final InventoryService service;
    
    @GetMapping
    public CustomResponse<List<InventoryDTO>> fetchAllInventoryHandler(
            @RequestHeader("Authorization") String token
    ) {
        return new CustomResponse<>(
                HttpStatus.OK,
                "Inventory items fetched successfully",
                service.fetchAllInventory(token)
        );
    }
    
    @PostMapping
    public CustomResponse<InventoryDTO> createInventoryHandler(
            @RequestHeader("Authorization") String token,
            @RequestBody InventoryDTO inventoryDTO
    ) {
        return new CustomResponse<>(
                HttpStatus.CREATED,
                "Inventory item created successfully",
                service.createInventory(token, inventoryDTO)
        );
    }
    
    @DeleteMapping("/{inventoryId}")
    public CustomResponse<Void> deleteInventoryHandler(
            @RequestHeader("Authorization") String token,
            @PathVariable String inventoryId
    ) {
        service.deleteInventory(token, inventoryId);
        return new CustomResponse<>(
                HttpStatus.OK,
                "Inventory item deleted successfully",
                null
        );
    }
    
    @GetMapping("/{inventoryId}")
    public CustomResponse<InventoryDTO> fetchInventoryByIdHandler(
            @RequestHeader("Authorization") String token,
            @PathVariable String inventoryId
    ) {
        return new CustomResponse<>(
                HttpStatus.OK,
                "Inventory item fetched successfully",
                service.fetchInventoryById(token, inventoryId)
        );
    }
    
    @PutMapping("/{inventoryId}")
    public CustomResponse<InventoryDTO> updateInventoryHandler(
            @RequestHeader("Authorization") String token,
            @PathVariable String inventoryId,
            @RequestBody InventoryDTO inventoryDTO
    ) {
        return new CustomResponse<>(
                HttpStatus.OK,
                "Inventory item updated successfully",
                service.updateInventory(token, inventoryId, inventoryDTO)
        );
    }
}