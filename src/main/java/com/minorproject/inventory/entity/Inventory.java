package com.minorproject.inventory.entity;

import com.minorproject.inventory.dto.InventoryDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "inventory_db")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String image;
    private BigDecimal sellPrice;
    @ManyToOne
    @JoinColumn(name = "sell_price_unit_id", nullable = false)
    private SellingUnit sellPriceUnit; // Dynamic from backend
    private BigDecimal mrp;
    private BigDecimal purchasePrice;
    private Double taxPercentage;
    private String itemCode;
    private String barCodeNumber;
    private String itemDescription;
    private Integer lowStockAlertQuantity;
    private LocalDate expiryDate;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category; // Dynamic from backend
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    public InventoryDTO toInventoryDTO() {
        return InventoryDTO.builder()
                .id(id != null ? id.toString() : null)
                .name(name)
                .image(image)
                .sellPrice(sellPrice)
                .sellPriceUnit(sellPriceUnit != null ? sellPriceUnit.getUnitName() : null)
                .mrp(mrp)
                .purchasePrice(purchasePrice)
                .taxPercentage(taxPercentage)
                .itemCode(itemCode)
                .barCodeNumber(barCodeNumber)
                .itemDescription(itemDescription)
                .lowStockAlertQuantity(lowStockAlertQuantity)
                .expiryDate(expiryDate)
                .category(category != null ? category.getCategoryName() : null)
                .build();
    }
}
