package com.minorproject.inventory.service;

import com.minorproject.inventory.dto.BankDetailsDTO;
import com.minorproject.inventory.entity.BankDetails;
import com.minorproject.inventory.entity.User;
import com.minorproject.inventory.exception.ResourceNotFoundException;
import com.minorproject.inventory.repository.BankDetailsRepository;
import com.minorproject.inventory.utils.FirebaseAuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BankDetailsService {
    
    private final BankDetailsRepository bankRepo;
    private final FirebaseAuthUtil firebaseAuthUtil;
    
    public BankDetailsDTO fetchBankDetails(String token) {
        User user = firebaseAuthUtil.getUserFromToken(token);
        
        BankDetails bankDetails = bankRepo.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Bank details not found for user"));
        
        return bankDetails.toBankDetailsDTO();
    }
    
    public BankDetailsDTO updateBankDetails(String token, BankDetailsDTO bankDetailsDTO) {
        User user = firebaseAuthUtil.getUserFromToken(token);
        
        Optional<BankDetails> existingBankDetails = bankRepo.findByUser(user);
        
        BankDetails bankDetails;
        if (existingBankDetails.isPresent()) {               // Update existing bank details
            bankDetails = existingBankDetails.get();
            bankDetails.setAccountNumber(bankDetailsDTO.getAccountNumber());
            bankDetails.setIfscCode(bankDetailsDTO.getIfscCode());
            bankDetails.setHolderName(bankDetailsDTO.getHolderName());
            bankDetails.setBankName(bankDetailsDTO.getBankName());
            bankDetails.setUpiId(bankDetailsDTO.getUpiId());
        } else {                                            // Create new bank details
            bankDetails = BankDetails.builder()
                    .accountNumber(bankDetailsDTO.getAccountNumber())
                    .ifscCode(bankDetailsDTO.getIfscCode())
                    .holderName(bankDetailsDTO.getHolderName())
                    .bankName(bankDetailsDTO.getBankName())
                    .upiId(bankDetailsDTO.getUpiId())
                    .user(user)
                    .build();
        }
        
        bankDetails = bankRepo.save(bankDetails);
        return bankDetails.toBankDetailsDTO();
    }
}