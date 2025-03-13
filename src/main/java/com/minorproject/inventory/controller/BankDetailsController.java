package com.minorproject.inventory.controller;

import com.minorproject.inventory.dto.BankDetailsDTO;
import com.minorproject.inventory.dto.CustomResponse;
import com.minorproject.inventory.service.BankDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bank")
@RequiredArgsConstructor
public class BankDetailsController {
    
    private final BankDetailsService service;
    
    @GetMapping
    public CustomResponse<BankDetailsDTO> fetchBankDetailsHandler(
            @RequestHeader("Authorization") String token
    ) {
        return new CustomResponse<>(
                HttpStatus.OK,
                "Bank details fetched successfully",
                service.fetchBankDetails(token)
        );
    }
    
    @PutMapping
    public CustomResponse<BankDetailsDTO> updateBankDetails(
            @RequestHeader("Authorization") String token,
            @RequestBody BankDetailsDTO bankDetailsDTO
    ) {
        return new CustomResponse<>(
                HttpStatus.OK,
                "Bank details updated successfully",
                service.updateBankDetails(token, bankDetailsDTO)
        );
    }
}