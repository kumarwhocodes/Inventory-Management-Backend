package com.minorproject.inventory.entity.report;

import com.minorproject.inventory.dto.reports.TransactionReportDTO;
import com.minorproject.inventory.entity.Party;
import com.minorproject.inventory.entity.User;
import com.minorproject.inventory.entity.bill.Bill;
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
@Table(name = "transaction_report_db")
public class TransactionReport {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    private LocalDate date;
    
    @ManyToOne
    @JoinColumn(name = "bill_id", nullable = false)
    private Bill bill;
    
    @ManyToOne
    @JoinColumn(name = "party_id", nullable = false)
    private Party party;
    
    private BigDecimal totalAmount;
    private String paymentMethod;
    
    private Boolean isInvoice; // true for sales invoice, false for purchase invoice
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    public TransactionReportDTO toTransactionReportDTO() {
        return TransactionReportDTO.builder()
                .id(id != null ? id.toString() : null)
                .date(date)
                .billId(bill != null ? bill.getId().toString() : null)
                .partyId(party != null ? party.getId().toString() : null)
                .partyName(party != null ? party.getName() : null)
                .totalAmount(totalAmount)
                .paymentMethod(paymentMethod)
                .isInvoice(isInvoice)
                .build();
    }
}