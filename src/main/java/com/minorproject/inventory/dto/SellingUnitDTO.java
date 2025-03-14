package com.minorproject.inventory.dto;

import com.minorproject.inventory.entity.SellingUnit;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellingUnitDTO {
    private String id;
    private String unitName;
    
    public SellingUnit toSellingUnit() {
        return SellingUnit.builder()
                .id(id != null ? UUID.fromString(id) : null)
                .unitName(unitName)
                .build();
    }
}
