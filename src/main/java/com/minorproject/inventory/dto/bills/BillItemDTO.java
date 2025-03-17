package com.minorproject.inventory.dto.bills;

import com.minorproject.inventory.entity.bill.BillItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillItemDTO {
    private String id;
    private String inventoryId;
    private String name;
    private BigDecimal quantity;
    private BigDecimal price;
    private BigDecimal totalPrice;
    
    public BillItem toBillItem() {
        return BillItem.builder()
                .id(id != null ? java.util.UUID.fromString(id) : null)
                .quantity(quantity)
                .price(price)
                .name(name)
                .totalPrice(totalPrice)
                .build();
    }
}