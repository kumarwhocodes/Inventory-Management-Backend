package com.minorproject.inventory.entity.report;

import com.minorproject.inventory.dto.reports.DayBookReportDTO;
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
@Table(name = "daybook_report_db")
public class DayBookReport {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    private LocalDate date;
    
    private BigDecimal moneyIn;
    private BigDecimal moneyInCash;
    private BigDecimal moneyInCheque;
    private BigDecimal moneyInUPI;
    
    private BigDecimal moneyOut;
    private BigDecimal moneyOutCash;
    private BigDecimal moneyOutCheque;
    private BigDecimal moneyOutUPI;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    public DayBookReportDTO toDayBookReportDTO() {
        return DayBookReportDTO.builder()
                .id(id != null ? id.toString() : null)
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