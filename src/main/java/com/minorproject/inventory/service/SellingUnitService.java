package com.minorproject.inventory.service;

import com.minorproject.inventory.dto.SellingUnitDTO;
import com.minorproject.inventory.entity.SellingUnit;
import com.minorproject.inventory.entity.User;
import com.minorproject.inventory.exception.ResourceNotFoundException;
import com.minorproject.inventory.repository.SellingUnitRepository;
import com.minorproject.inventory.utils.FirebaseAuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SellingUnitService {
    
    private final SellingUnitRepository sellingUnitRepo;
    private final FirebaseAuthUtil firebaseAuthUtil;
    
    public List<SellingUnitDTO> fetchAllSellingUnits(String token) {
        User user = firebaseAuthUtil.getUserFromToken(token);
        
        List<SellingUnit> sellingUnits = sellingUnitRepo.findByUser(user);
        return sellingUnits.stream()
                .map(SellingUnit::toSellingUnitDTO)
                .collect(Collectors.toList());
    }
    
    public List<SellingUnitDTO> createSellingUnits(String token, List<SellingUnitDTO> sellingUnitDTOs) {
        User user = firebaseAuthUtil.getUserFromToken(token);
        
        List<SellingUnit> unitsToSave = new ArrayList<>();
        
        for (SellingUnitDTO unitDTO : sellingUnitDTOs) {
            SellingUnit unit = SellingUnit.builder()
                    .unitName(unitDTO.getUnitName())
                    .user(user)
                    .build();
            
            unitsToSave.add(unit);
        }
        
        List<SellingUnit> savedUnits = sellingUnitRepo.saveAll(unitsToSave);
        
        return savedUnits.stream()
                .map(SellingUnit::toSellingUnitDTO)
                .collect(Collectors.toList());
    }
    
    public void deleteSellingUnit(String token, String unitId) {
        User user = firebaseAuthUtil.getUserFromToken(token);
        
        SellingUnit unit = sellingUnitRepo.findByIdAndUser(UUID.fromString(unitId), user)
                .orElseThrow(() -> new ResourceNotFoundException("Selling unit not found with id: " + unitId));
        
        sellingUnitRepo.delete(unit);
    }
}