package com.minorproject.inventory.dto.reports;

import com.minorproject.inventory.entity.report.MoneyReport;
import com.minorproject.inventory.enums.ModeOfPayment;
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
public class MoneyReportDTO {
    private String id;
    private LocalDate date;
    private String partyId;
    private String partyName;
    private BigDecimal amount;
    private Boolean isMoneyIn;
    private String mode;
    private String billId;
    
    public MoneyReport toMoneyReport() {
        return MoneyReport.builder()
                .id(id != null ? java.util.UUID.fromString(id) : null)
                .date(date)
                .amount(amount)
                .isMoneyIn(isMoneyIn)
                .mode(ModeOfPayment.valueOf(mode))
                .build();
    }
}