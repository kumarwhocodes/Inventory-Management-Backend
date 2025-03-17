package com.minorproject.inventory.service;

import com.minorproject.inventory.dto.reports.DayBookReportDTO;
import com.minorproject.inventory.dto.reports.MoneyReportDTO;
import com.minorproject.inventory.dto.reports.StockSummaryDTO;
import com.minorproject.inventory.dto.reports.TransactionReportDTO;
import com.minorproject.inventory.entity.Inventory;
import com.minorproject.inventory.entity.User;
import com.minorproject.inventory.entity.report.DayBookReport;
import com.minorproject.inventory.entity.report.MoneyReport;
import com.minorproject.inventory.entity.report.StockSummary;
import com.minorproject.inventory.entity.report.TransactionReport;
import com.minorproject.inventory.exception.ResourceNotFoundException;
import com.minorproject.inventory.repository.*;
import com.minorproject.inventory.utils.FirebaseAuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {
    
    private final DayBookReportRepository dayBookReportRepository;
    private final MoneyReportRepository moneyReportRepository;
    private final StockSummaryRepository stockSummaryRepository;
    private final TransactionReportRepository transactionReportRepository;
    private final FirebaseAuthUtil firebaseAuthUtil;
    private final InventoryRepository inventoryRepository;
    
    // DayBook Report Methods
    public List<DayBookReportDTO> fetchDayBookReports(String token, LocalDate startDate, LocalDate endDate) {
        User user = firebaseAuthUtil.getUserFromToken(token);
        
        List<DayBookReport> reports = dayBookReportRepository.findByUserAndDateBetween(user, startDate, endDate);
        return reports.stream()
                .map(DayBookReport::toDayBookReportDTO)
                .collect(Collectors.toList());
    }
    
    public DayBookReportDTO fetchDayBookReportByDate(String token, LocalDate date) {
        User user = firebaseAuthUtil.getUserFromToken(token);
        
        DayBookReport report = dayBookReportRepository.findByUserAndDate(user, date)
                .orElseThrow(() -> new ResourceNotFoundException("DayBook report not found for date: " + date));
        
        return report.toDayBookReportDTO();
    }
    
    // Money Report Methods
    public List<MoneyReportDTO> fetchMoneyReports(String token, LocalDate startDate, LocalDate endDate) {
        //TODO party id coming null
        User user = firebaseAuthUtil.getUserFromToken(token);
        
        List<MoneyReport> reports = moneyReportRepository.findByUserAndDateBetween(user, startDate, endDate);
        return reports.stream()
                .map(this::toMoneyReportDTO)
                .collect(Collectors.toList());
    }
    
    // Stock Summary Methods
    public List<StockSummaryDTO> fetchStockSummaries(String token) {
        //TODO purchase isn't increasing upon bill creation
        User user = firebaseAuthUtil.getUserFromToken(token);
        
        List<StockSummary> summaries = stockSummaryRepository.findByUser(user);
        return summaries.stream()
                .map(this::toStockSummaryDTO)
                .collect(Collectors.toList());
    }
    
    public StockSummaryDTO fetchStockSummaryByInventory(String token, String inventoryId) {
        //TODO purchase isn't increasing upon bill creation
        User user = firebaseAuthUtil.getUserFromToken(token);
        
        // First find the inventory
        Inventory inventory = inventoryRepository.findByIdAndUser(UUID.fromString(inventoryId), user)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found with id: " + inventoryId));
        
        // Then find the stock summary
        StockSummary summary = stockSummaryRepository.findByUserAndInventory(user, inventory)
                .orElseThrow(() -> new ResourceNotFoundException("Stock summary not found for inventory: " + inventoryId));
        
        return toStockSummaryDTO(summary);
    }
    
    // Transaction Report Methods
    public List<TransactionReportDTO> fetchTransactionReports(String token, LocalDate startDate, LocalDate endDate) {
        //TODO party id coming null
        User user = firebaseAuthUtil.getUserFromToken(token);
        
        List<TransactionReport> reports = transactionReportRepository.findByUserAndDateBetween(user, startDate, endDate);
        return reports.stream()
                .map(this::toTransactionReportDTO)
                .collect(Collectors.toList());
    }
    
    // Helper methods to convert entity to DTO
    private MoneyReportDTO toMoneyReportDTO(MoneyReport report) {
        return MoneyReportDTO.builder()
                .id(report.getId() != null ? report.getId().toString() : null)
                .date(report.getDate())
                .partyName(report.getParty() != null ? report.getParty().getName() : null)
                .amount(report.getAmount())
                .isMoneyIn(report.getIsMoneyIn())
                .mode(report.getMode().toString())
                .billId(report.getBill() != null ? report.getBill().getId().toString() : null)
                .build();
    }
    
    private StockSummaryDTO toStockSummaryDTO(StockSummary summary) {
        return StockSummaryDTO.builder()
                .id(summary.getId() != null ? summary.getId().toString() : null)
                .inventoryName(summary.getInventory() != null ? summary.getInventory().getName() : null)
                .inventoryId(summary.getInventory() != null ? summary.getInventory().getId().toString() : null)
                .purchased(summary.getPurchased())
                .sold(summary.getSold())
                .leftStock(summary.getLeftStock())
                .lastUpdated(summary.getLastUpdated())
                .build();
    }
    
    private TransactionReportDTO toTransactionReportDTO(TransactionReport report) {
        return TransactionReportDTO.builder()
                .id(report.getId() != null ? report.getId().toString() : null)
                .date(report.getDate())
                .billId(report.getBill() != null ? report.getBill().getId().toString() : null)
                .partyName(report.getParty() != null ? report.getParty().getName() : null)
                .totalAmount(report.getTotalAmount())
                .paymentMethod(report.getPaymentMethod())
                .isInvoice(report.getIsInvoice())
                .build();
    }
}