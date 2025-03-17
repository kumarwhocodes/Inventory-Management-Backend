package com.minorproject.inventory.controller;

import com.minorproject.inventory.dto.*;
import com.minorproject.inventory.dto.reports.DayBookReportDTO;
import com.minorproject.inventory.dto.reports.MoneyReportDTO;
import com.minorproject.inventory.dto.reports.StockSummaryDTO;
import com.minorproject.inventory.dto.reports.TransactionReportDTO;
import com.minorproject.inventory.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {
    
    private final ReportService service;
    
    // DayBook Report Endpoints
    @GetMapping("/daybook")
    public CustomResponse<List<DayBookReportDTO>> fetchDayBookReportsHandler(
            @RequestHeader("Authorization") String token,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        return new CustomResponse<>(
                HttpStatus.OK,
                "DayBook reports fetched successfully",
                service.fetchDayBookReports(token, startDate, endDate)
        );
    }
    
    @GetMapping("/daybook/{date}")
    public CustomResponse<DayBookReportDTO> fetchDayBookReportByDateHandler(
            @RequestHeader("Authorization") String token,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return new CustomResponse<>(
                HttpStatus.OK,
                "DayBook report fetched successfully",
                service.fetchDayBookReportByDate(token, date)
        );
    }
    
    // Money Report Endpoints
    @GetMapping("/money")
    public CustomResponse<List<MoneyReportDTO>> fetchMoneyReportsHandler(
            @RequestHeader("Authorization") String token,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        return new CustomResponse<>(
                HttpStatus.OK,
                "Money reports fetched successfully",
                service.fetchMoneyReports(token, startDate, endDate)
        );
    }
  
    // Stock Summary Endpoints
    @GetMapping("/stock")
    public CustomResponse<List<StockSummaryDTO>> fetchStockSummariesHandler(
            @RequestHeader("Authorization") String token
    ) {
        return new CustomResponse<>(
                HttpStatus.OK,
                "Stock summaries fetched successfully",
                service.fetchStockSummaries(token)
        );
    }
    
    @GetMapping("/stock/{inventoryId}")
    public CustomResponse<StockSummaryDTO> fetchStockSummaryByInventoryHandler(
            @RequestHeader("Authorization") String token,
            @PathVariable String inventoryId
    ) {
        return new CustomResponse<>(
                HttpStatus.OK,
                "Stock summary fetched successfully",
                service.fetchStockSummaryByInventory(token, inventoryId)
        );
    }
    
    // Transaction Report Endpoints
    @GetMapping("/transactions")
    public CustomResponse<List<TransactionReportDTO>> fetchTransactionReportsHandler(
            @RequestHeader("Authorization") String token,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        return new CustomResponse<>(
                HttpStatus.OK,
                "Transaction reports fetched successfully",
                service.fetchTransactionReports(token, startDate, endDate)
        );
    }
}