package com.minorproject.inventory.dto;

import com.minorproject.inventory.entity.BankDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String upiId;
    
    public BankDetails toBankDetails() {
        return BankDetails.builder()
                .id(id != null ? UUID.fromString(id) : null)
                .accountNumber(accountNumber)
                .ifscCode(ifscCode)
                .holderName(holderName)
                .bankName(bankName)
                .upiId(upiId)
                .build();
    }
}
