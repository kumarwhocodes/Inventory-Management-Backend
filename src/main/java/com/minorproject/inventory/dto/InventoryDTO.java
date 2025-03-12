package com.minorproject.inventory.dto;

import com.minorproject.inventory.entity.Inventory;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDTO {
    private String id;
    private String name;
    private String image;
    private BigDecimal sellPrice;
    private String sellPriceUnit; // Dynamic from backend
    private BigDecimal mrp;
    private BigDecimal purchasePrice;
    private Double taxPercentage;
    private String itemCode;
    private String barCodeNumber;
    private String itemDescription;
    private Integer lowStockAlertQuantity;
    private LocalDate expiryDate;
    private String category; // Dynamic from backend
    
    public Inventory toInventory() {
        return Inventory.builder()
                .id(id != null ? UUID.fromString(id) : null)
                .name(name)
                .image(image)
                .sellPrice(sellPrice)
                .mrp(mrp)
                .purchasePrice(purchasePrice)
                .taxPercentage(taxPercentage)
                .itemCode(itemCode)
                .barCodeNumber(barCodeNumber)
                .itemDescription(itemDescription)
                .lowStockAlertQuantity(lowStockAlertQuantity)
                .expiryDate(expiryDate)
                .build();
    }
}
