package com.minorproject.inventory.entity;

import com.minorproject.inventory.dto.SellingUnitDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "selling_units_db")
public class SellingUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, unique = true)
    private String unitName;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    public SellingUnitDTO toSellingUnitDTO() {
        return SellingUnitDTO.builder()
                .id(id != null ? id.toString() : null)
                .unitName(unitName)
                .build();
    }
}
