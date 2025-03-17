package com.minorproject.inventory.entity.report;

import com.minorproject.inventory.dto.reports.StockSummaryDTO;
import com.minorproject.inventory.entity.Inventory;
import com.minorproject.inventory.entity.User;
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
@Table(name = "stock_summary_db")
public class StockSummary {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @ManyToOne
    @JoinColumn(name = "inventory_id", nullable = false)
    private Inventory inventory;
    
    private BigDecimal purchased;
    private BigDecimal sold;
    private BigDecimal leftStock;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    private LocalDate lastUpdated;
    
    public StockSummaryDTO toStockSummaryDTO() {
        return StockSummaryDTO.builder()
                .id(id != null ? id.toString() : null)
                .inventoryId(inventory != null ? inventory.getId().toString() : null)
                .inventoryName(inventory != null ? inventory.getName() : null)
                .purchased(purchased)
                .sold(sold)
                .leftStock(leftStock)
                .lastUpdated(lastUpdated)
                .build();
    }
}