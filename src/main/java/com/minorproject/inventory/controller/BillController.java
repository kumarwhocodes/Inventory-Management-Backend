package com.minorproject.inventory.controller;

import com.minorproject.inventory.dto.CustomResponse;
import com.minorproject.inventory.dto.bills.BillDTO;
import com.minorproject.inventory.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/bills")
@RequiredArgsConstructor
public class BillController {
    
    private final BillService service;
    
    @GetMapping
    public CustomResponse<List<BillDTO>> fetchAllBillsHandler(
            @RequestHeader("Authorization") String token
    ) {
        return new CustomResponse<>(
                HttpStatus.OK,
                "Bills fetched successfully",
                service.fetchAllBills(token)
        );
    }
    
    @GetMapping("/party/{partyId}")
    public CustomResponse<List<BillDTO>> fetchBillsByPartyHandler(
            @RequestHeader("Authorization") String token,
            @PathVariable String partyId
    ) {
        return new CustomResponse<>(
                HttpStatus.OK,
                "Bills fetched successfully for party",
                service.fetchBillsByParty(token, partyId)
        );
    }
    
    @GetMapping("/date-range")
    public CustomResponse<List<BillDTO>> fetchBillsByDateRangeHandler(
            @RequestHeader("Authorization") String token,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        return new CustomResponse<>(
                HttpStatus.OK,
                "Bills fetched successfully for date range",
                service.fetchBillsByDateRange(token, startDate, endDate)
        );
    }
    
    @GetMapping("/{billId}")
    public CustomResponse<BillDTO> fetchBillByIdHandler(
            @RequestHeader("Authorization") String token,
            @PathVariable String billId
    ) {
        return new CustomResponse<>(
                HttpStatus.OK,
                "Bill fetched successfully",
                service.fetchBillById(token, billId)
        );
    }
    
    @PostMapping
    public CustomResponse<BillDTO> createBillHandler(
            @RequestHeader("Authorization") String token,
            @RequestBody BillDTO billDTO
    ) {
        return new CustomResponse<>(
                HttpStatus.CREATED,
                "Bill created successfully",
                service.createBill(token, billDTO)
        );
    }
    
    @DeleteMapping("/{billId}")
    public CustomResponse<Void> deleteBillHandler(
            @RequestHeader("Authorization") String token,
            @PathVariable String billId
    ) {
        service.deleteBill(token, billId);
        return new CustomResponse<>(
                HttpStatus.OK,
                "Bill deleted successfully",
                null
        );
    }
}