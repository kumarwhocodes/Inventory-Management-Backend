package com.minorproject.inventory.dto.reports;

import com.minorproject.inventory.entity.Party;
import com.minorproject.inventory.entity.User;
import com.minorproject.inventory.entity.bill.Bill;
import com.minorproject.inventory.entity.report.MoneyReport;
import com.minorproject.inventory.enums.ModeOfPayment;
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
public class MoneyReportDTO {
    private String id;
    private LocalDate date;
    private String partyId;
    private String partyName;
    private BigDecimal amount;
    private Boolean isMoneyIn;
    private String mode;
    private String billId;
    
    public MoneyReport toMoneyReport(User user) {
        Party partyObj = null;
        if (partyId != null) {
            partyObj = new Party();
            partyObj.setId(UUID.fromString(partyId));
        }
        
        Bill billObj = null;
        if (billId != null) {
            billObj = new Bill();
            billObj.setId(UUID.fromString(billId));
        }
        
        return MoneyReport.builder()
                .id(id != null ? java.util.UUID.fromString(id) : null)
                .date(date)
                .party(partyObj)
                .amount(amount)
                .isMoneyIn(isMoneyIn)
                .mode(mode != null ? ModeOfPayment.valueOf(mode) : null)
                .bill(billObj)
                .user(user)
                .build();
    }
}