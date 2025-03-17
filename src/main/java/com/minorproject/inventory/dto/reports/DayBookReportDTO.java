package com.minorproject.inventory.dto.reports;

import com.minorproject.inventory.entity.report.DayBookReport;
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
public class DayBookReportDTO {
    private String id;
    private LocalDate date;
    
    private BigDecimal moneyIn;
    private BigDecimal moneyInCash;
    private BigDecimal moneyInCheque;
    private BigDecimal moneyInUPI;
    
    private BigDecimal moneyOut;
    private BigDecimal moneyOutCash;
    private BigDecimal moneyOutCheque;
    private BigDecimal moneyOutUPI;
    
    public DayBookReport toDayBookReport() {
        return DayBookReport.builder()
                .id(id != null ? UUID.fromString(id) : null)
                .date(date)
                .moneyIn(moneyIn)
                .moneyInCash(moneyInCash)
                .moneyInCheque(moneyInCheque)
                .moneyInUPI(moneyInUPI)
                .moneyOut(moneyOut)
                .moneyOutCash(moneyOutCash)
                .moneyOutCheque(moneyOutCheque)
                .moneyOutUPI(moneyOutUPI)
                .build();
    }
    
}
