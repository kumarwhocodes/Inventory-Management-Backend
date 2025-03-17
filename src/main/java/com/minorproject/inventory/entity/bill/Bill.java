package com.minorproject.inventory.entity.bill;

import com.minorproject.inventory.dto.bills.BillDTO;
import com.minorproject.inventory.entity.Party;
import com.minorproject.inventory.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bill_db")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    private LocalDate billDate;
    private LocalTime billTime;
    
    @ManyToOne
    @JoinColumn(name = "party_id", nullable = false)
    private Party party;
    
    private String paymentMethod; // CASH, UPI, CHEQUE, etc.
    private BigDecimal totalAmount;
    
    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BillItem> items = new ArrayList<>();
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    private Boolean isInvoice; // true for sales invoice, false for purchase invoice
    
    public BillDTO toBillDTO() {
        return BillDTO.builder()
                .id(id != null ? id.toString() : null)
                .billDate(billDate)
                .billTime(billTime)
                .partyId(party != null ? party.getId().toString() : null)
                .partyName(party != null ? party.getName() : null)
                .paymentMethod(paymentMethod)
                .totalAmount(totalAmount)
                .isInvoice(isInvoice)
                .items(items != null ? items.stream()
                        .map(BillItem::toBillItemDTO)
                        .collect(Collectors.toList()) : null)
                .build();
    }
}