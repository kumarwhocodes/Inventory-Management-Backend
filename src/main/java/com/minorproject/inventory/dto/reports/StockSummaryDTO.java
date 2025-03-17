package com.minorproject.inventory.dto.reports;

import com.minorproject.inventory.entity.report.StockSummary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockSummaryDTO {
    private String id;
    private String inventoryId;
    private String inventoryName;
    private BigDecimal purchased;
    private BigDecimal sold;
    private BigDecimal leftStock;
    private LocalDate lastUpdated;
    
    public StockSummary toStockSummary() {
        return StockSummary.builder()
                .id(id != null ? java.util.UUID.fromString(id) : null)
                .purchased(purchased)
                .sold(sold)
                .leftStock(leftStock)
                .lastUpdated(lastUpdated)
                .build();
    }
}