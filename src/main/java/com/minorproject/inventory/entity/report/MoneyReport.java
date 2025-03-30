package com.minorproject.inventory.entity.report;

import com.minorproject.inventory.dto.reports.MoneyReportDTO;
import com.minorproject.inventory.entity.Party;
import com.minorproject.inventory.entity.User;
import com.minorproject.inventory.entity.bill.Bill;
import com.minorproject.inventory.enums.ModeOfPayment;
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
@Table(name = "money_report_db")
public class MoneyReport {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    private LocalDate date;
    
    @ManyToOne
    @JoinColumn(name = "party_id", nullable = false)
    private Party party;
    
    private BigDecimal amount;
    
    private Boolean isMoneyIn; // true for money in, false for money out
    
    @Enumerated(EnumType.STRING)
    private ModeOfPayment mode; // CASH, UPI, CHEQUE, etc.
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "bill_id")
    private Bill bill;
    
    public MoneyReportDTO toMoneyReportDTO() {
        return MoneyReportDTO.builder()
                .id(id != null ? id.toString() : null)
                .date(date)
                .partyId(party != null ? party.getId().toString() : null)
                .partyName(party != null ? party.getName() : null)
                .amount(amount)
                .isMoneyIn(isMoneyIn)
                .mode(mode != null ? mode.toString() : null)
                .billId(bill != null ? bill.getId().toString() : null)
                .build();
    }
}