package com.minorproject.inventory.entity;

import com.minorproject.inventory.dto.BankDetailsDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bank_details_db")
public class BankDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true, nullable = false)
    private String accountNumber;
    private String ifscCode;
    private String holderName;
    private String bankName;
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    public BankDetailsDTO toBankDetailsDTO() {
        return BankDetailsDTO.builder()
                .id(id != null ? id.toString() : null)
                .accountNumber(accountNumber)
                .ifscCode(ifscCode)
                .holderName(holderName)
                .bankName(bankName)
                .build();
    }
    
}
