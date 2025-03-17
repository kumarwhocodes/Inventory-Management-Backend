package com.minorproject.inventory.entity.bill;

import com.minorproject.inventory.dto.bills.BillItemDTO;
import com.minorproject.inventory.entity.Inventory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bill_item_db")
public class BillItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @ManyToOne
    @JoinColumn(name = "inventory_id", nullable = false)
    private Inventory inventory;
    private String name;
    
    private BigDecimal quantity;
    
    private BigDecimal price; // Price per unit
    
    private BigDecimal totalPrice; // Price * quantity
    
    @ManyToOne
    @JoinColumn(name = "bill_id", nullable = false)
    private Bill bill;
    
    public BillItemDTO toBillItemDTO() {
        return BillItemDTO.builder()
                .id(id != null ? id.toString() : null)
                .inventoryId(inventory != null ? inventory.getId().toString() : null)
                .name(inventory != null ? inventory.getName() : null)
                .quantity(quantity)
                .price(price)
                .totalPrice(totalPrice)
                .build();
    }
}