package com.minorproject.inventory.controller;

import com.minorproject.inventory.dto.SellingUnitDTO;
import com.minorproject.inventory.entity.SellingUnit;
import com.minorproject.inventory.repository.SellingUnitRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/selling-units")
public class SellingUnitController {
    
    private final SellingUnitRepository sellingUnitRepository;
    
    public SellingUnitController(SellingUnitRepository sellingUnitRepository) {
        this.sellingUnitRepository = sellingUnitRepository;
    }
    
    @PostMapping
    public ResponseEntity<SellingUnitDTO> addSellingUnit(@RequestBody SellingUnitDTO dto) {
        SellingUnit sellingUnit = sellingUnitRepository.save(dto.toSellingUnit());
        return ResponseEntity.ok(new SellingUnitDTO(sellingUnit.getId().toString(), sellingUnit.getUnitName()));
    }
    
    @GetMapping
    public ResponseEntity<List<SellingUnitDTO>> getAllSellingUnits() {
        List<SellingUnitDTO> units = sellingUnitRepository.findAll().stream()
                .map(unit -> new SellingUnitDTO(unit.getId().toString(), unit.getUnitName()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(units);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSellingUnit(@PathVariable String id) {
        sellingUnitRepository.deleteById(UUID.fromString(id));
        return ResponseEntity.noContent().build();
    }
}
