package com.minorproject.inventory.controller;

import com.minorproject.inventory.dto.SellingUnitDTO;
import com.minorproject.inventory.dto.CustomResponse;
import com.minorproject.inventory.service.SellingUnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/unit")
@RequiredArgsConstructor
public class SellingUnitController {
    
    private final SellingUnitService service;
    
    @GetMapping
    public CustomResponse<List<SellingUnitDTO>> fetchAllSellingUnitsHandler(
            @RequestHeader("Authorization") String token
    ) {
        return new CustomResponse<>(
                HttpStatus.OK,
                "Selling units fetched successfully",
                service.fetchAllSellingUnits(token)
        );
    }
    
    @PostMapping
    public CustomResponse<List<SellingUnitDTO>> createSellingUnitsHandler(
            @RequestHeader("Authorization") String token,
            @RequestBody List<SellingUnitDTO> sellingUnitDTOs
    ) {
        return new CustomResponse<>(
                HttpStatus.CREATED,
                "Selling units created successfully",
                service.createSellingUnits(token, sellingUnitDTOs)
        );
    }
    
    @DeleteMapping("/{unitId}")
    public CustomResponse<Void> deleteSellingUnitHandler(
            @RequestHeader("Authorization") String token,
            @PathVariable String unitId
    ) {
        service.deleteSellingUnit(token, unitId);
        return new CustomResponse<>(
                HttpStatus.OK,
                "Selling unit deleted successfully",
                null
        );
    }
}