package com.minorproject.inventory.dto.bills;

import com.minorproject.inventory.entity.bill.Bill;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillDTO {
    private String id;
    private LocalDate billDate;
    private LocalTime billTime;
    private String partyId;
    private String partyName;
    private String paymentMethod;
    private BigDecimal totalAmount;
    private Boolean isInvoice; // true for sales invoice, false for purchase invoice
    private List<BillItemDTO> items;
    
    public Bill toBill() {
        return Bill.builder()
                .id(id != null ? java.util.UUID.fromString(id) : null)
                .billDate(billDate)
                .billTime(billTime)
                .paymentMethod(paymentMethod)
                .totalAmount(totalAmount)
                .isInvoice(isInvoice)
                .build();
        
/*
         Note: Party and User would typically be set in a service layer
         bill.setParty(party);
         bill.setUser(user);
*/
    
    }
}