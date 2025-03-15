package com.minorproject.inventory.dto;

import com.minorproject.inventory.entity.Inventory;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDTO {
    private String id;
    private String name;
    private String photo;
    private BigDecimal sellPrice;
    private String sellPriceUnit; // Dynamic from backend
    private BigDecimal mrp;
    private BigDecimal purchasePrice;
    private Double tax;
    private String itemCode;
    private String barcode;
    private String itemDescription;
    private Integer lowStockAlertQuantity;
    private LocalDate expiryDate;
    private String category; // Dynamic from backend
    
    public Inventory toInventory() {
        return Inventory.builder()
                .id(id != null ? UUID.fromString(id) : null)
                .name(name)
                .photo(photo)
                .sellPrice(sellPrice)
                .mrp(mrp)
                .purchasePrice(purchasePrice)
                .tax(tax)
                .itemCode(itemCode)
                .barcode(barcode)
                .itemDescription(itemDescription)
                .lowStockAlertQuantity(lowStockAlertQuantity)
                .expiryDate(expiryDate)
                .build();
    }
}
