package com.minorproject.inventory.dto;

import com.minorproject.inventory.entity.BankDetails;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankDetailsDTO {
    private String id;
    private String accountNumber;
    private String ifscCode;
    private String holderName;
    private String bankName;
    
    public BankDetails toBankDetails() {
        return BankDetails.builder()
                .id(id != null ? UUID.fromString(id) : null)
                .accountNumber(accountNumber)
                .ifscCode(ifscCode)
                .holderName(holderName)
                .bankName(bankName)
                .build();
    }
}
