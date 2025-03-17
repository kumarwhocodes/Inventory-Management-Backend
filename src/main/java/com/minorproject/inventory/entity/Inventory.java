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
    private String photo;
    private Float sellPrice;
    @ManyToOne
    @JoinColumn(name = "sell_price_unit_id", nullable = false)
    private SellingUnit sellPriceUnit; // Dynamic from backend
    private Float mrp;
    private Float purchasePrice;
    private Double tax;
    private String itemCode;
    private String barcode;
    private String itemDescription;
    private Integer lowStockAlertQuantity;
    private String storageLocation;
    private LocalDate expiryDate;
    @Column(nullable = false)
    private BigDecimal quantity;
    
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
                .photo(photo)
                .sellPrice(sellPrice)
                .sellPriceUnit(sellPriceUnit != null ? sellPriceUnit.getUnitName() : null)
                .mrp(mrp)
                .purchasePrice(purchasePrice)
                .tax(tax)
                .itemCode(itemCode)
                .barcode(barcode)
                .itemDescription(itemDescription)
                .lowStockAlertQuantity(lowStockAlertQuantity)
                .expiryDate(expiryDate)
                .quantity(quantity)
                .category(category != null ? category.getCategoryName() : null)
                .build();
    }
}
