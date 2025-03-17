package com.minorproject.inventory.dto.reports;

import com.minorproject.inventory.entity.report.TransactionReport;
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
public class TransactionReportDTO {
    private String id;
    private LocalDate date;
    private String billId;
    private String partyId;
    private String partyName;
    private BigDecimal totalAmount;
    private String paymentMethod;
    private Boolean isInvoice;
    
    public TransactionReport toTransactionReport() {
        return TransactionReport.builder()
                .id(id != null ? java.util.UUID.fromString(id) : null)
                .date(date)
                .totalAmount(totalAmount)
                .paymentMethod(paymentMethod)
                .isInvoice(isInvoice)
                .build();
    }
}