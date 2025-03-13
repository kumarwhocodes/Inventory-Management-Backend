package com.minorproject.inventory.controller;

import com.minorproject.inventory.dto.CustomResponse;
import com.minorproject.inventory.dto.SellingUnitDTO;
import com.minorproject.inventory.entity.SellingUnit;
import com.minorproject.inventory.repository.SellingUnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/selling-units")
@RequiredArgsConstructor
public class SellingUnitController {
    
    private final SellingUnitRepository sellingUnitRepository;
    
    @PostMapping
    public CustomResponse<SellingUnitDTO> addSellingUnit(@RequestBody SellingUnitDTO dto) {
        SellingUnit sellingUnit = sellingUnitRepository.save(dto.toSellingUnit());
        return new CustomResponse<>(
                HttpStatus.OK,
                "Selling Unit added successfully",
                new SellingUnitDTO(dto.getId(), dto.getUnitName())
        );
    }
    
    @GetMapping
    public CustomResponse<List<SellingUnitDTO>> getAllSellingUnits() {
        List<SellingUnitDTO> units = sellingUnitRepository.findAll().stream()
                .map(unit -> new SellingUnitDTO(unit.getId().toString(), unit.getUnitName()))
                .collect(Collectors.toList());
        return new CustomResponse<>(
                HttpStatus.OK,
                "Selling Unit fetched successfully",
                units
        );
    }
    
    @DeleteMapping("/{id}")
    public CustomResponse<Void> deleteSellingUnit(@PathVariable String id) {
        sellingUnitRepository.deleteById(UUID.fromString(id));
        return new CustomResponse<>(
                HttpStatus.OK,
                "Selling Unit deleted successfully!",
                null
        );
    }
}
