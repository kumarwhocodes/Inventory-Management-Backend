package com.minorproject.inventory.service;

import com.minorproject.inventory.dto.bills.BillDTO;
import com.minorproject.inventory.dto.bills.BillItemDTO;
import com.minorproject.inventory.entity.Inventory;
import com.minorproject.inventory.entity.Party;
import com.minorproject.inventory.entity.User;
import com.minorproject.inventory.entity.bill.Bill;
import com.minorproject.inventory.entity.bill.BillItem;
import com.minorproject.inventory.entity.report.DayBookReport;
import com.minorproject.inventory.entity.report.MoneyReport;
import com.minorproject.inventory.entity.report.StockSummary;
import com.minorproject.inventory.entity.report.TransactionReport;
import com.minorproject.inventory.enums.ModeOfPayment;
import com.minorproject.inventory.exception.ResourceNotFoundException;
import com.minorproject.inventory.repository.*;
import com.minorproject.inventory.utils.FirebaseAuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BillService {
    
    private final BillRepository billRepository;
    private final BillItemRepository billItemRepository;
    private final PartyRepository partyRepository;
    private final InventoryRepository inventoryRepository;
    private final FirebaseAuthUtil firebaseAuthUtil;
    private final DayBookReportRepository dayBookReportRepository;
    private final MoneyReportRepository moneyReportRepository;
    private final StockSummaryRepository stockSummaryRepository;
    private final TransactionReportRepository transactionReportRepository;
    
    public List<BillDTO> fetchAllBills(String token) {
        User user = firebaseAuthUtil.getUserFromToken(token);
        
        List<Bill> bills = billRepository.findByUser(user);
        return bills.stream()
                .map(Bill::toBillDTO)
                .collect(Collectors.toList());
    }
    
    public List<BillDTO> fetchBillsByParty(String token, String partyId) {
        User user = firebaseAuthUtil.getUserFromToken(token);
        Party party = partyRepository.findByIdAndUser(UUID.fromString(partyId), user)
                .orElseThrow(() -> new ResourceNotFoundException("Party not found with id: " + partyId));
        
        List<Bill> bills = billRepository.findByUserAndParty(user, party);
        return bills.stream()
                .map(Bill::toBillDTO)
                .collect(Collectors.toList());
    }
    
    public List<BillDTO> fetchBillsByDateRange(String token, LocalDate startDate, LocalDate endDate) {
        User user = firebaseAuthUtil.getUserFromToken(token);
        
        List<Bill> bills = billRepository.findByUserAndBillDateBetween(user, startDate, endDate);
        return bills.stream()
                .map(Bill::toBillDTO)
                .collect(Collectors.toList());
    }
    
    public BillDTO fetchBillById(String token, String billId) {
        User user = firebaseAuthUtil.getUserFromToken(token);
        
        Bill bill = billRepository.findByIdAndUser(UUID.fromString(billId), user)
                .orElseThrow(() -> new ResourceNotFoundException("Bill not found with id: " + billId));
        
        return bill.toBillDTO();
    }
    
    @Transactional
    public BillDTO createBill(String token, BillDTO billDTO) {
        User user = firebaseAuthUtil.getUserFromToken(token);
        
        // Fetch party
        Party party = partyRepository.findByIdAndUser(UUID.fromString(billDTO.getPartyId()), user)
                .orElseThrow(() -> new ResourceNotFoundException("Party not found with id: " + billDTO.getPartyId()));
        
        // Create bill
        Bill bill = Bill.builder()
                .billDate(billDTO.getBillDate() != null ? billDTO.getBillDate() : LocalDate.now())
                .billTime(billDTO.getBillTime() != null ? billDTO.getBillTime() : LocalTime.now())
                .party(party)
                .paymentMethod(billDTO.getPaymentMethod())
                .totalAmount(billDTO.getTotalAmount())
                .isInvoice(billDTO.getIsInvoice())
                .user(user)
                .items(new ArrayList<>())
                .build();
        
        Bill savedBill = billRepository.save(bill);
        
        // Create bill items and update inventory
        if (billDTO.getItems() != null && !billDTO.getItems().isEmpty()) {
            for (BillItemDTO itemDTO : billDTO.getItems()) {
                Inventory inventory = inventoryRepository.findByIdAndUser(UUID.fromString(itemDTO.getInventoryId()), user)
                        .orElseThrow(() -> new ResourceNotFoundException("Inventory item not found with id: " + itemDTO.getInventoryId()));
                
                if (bill.getIsInvoice() && inventory.getQuantity().compareTo(itemDTO.getQuantity()) < 0) {
                    throw new IllegalStateException("Not enough stock available for item: " + inventory.getName());
                }
                
                // Create bill item
                BillItem billItem = BillItem.builder()
                        .inventory(inventory)
                        .quantity(itemDTO.getQuantity())
                        .price(itemDTO.getPrice())
                        .totalPrice(itemDTO.getTotalPrice())
                        .bill(savedBill)
                        .build();
                
                billItemRepository.save(billItem);
                
                // Update inventory quantity
                if (bill.getIsInvoice()) {
                    // Sales invoice - decrease quantity
                    inventory.setQuantity(inventory.getQuantity().subtract(itemDTO.getQuantity()));
                } else {
                    // Purchase invoice - increase quantity
                    inventory.setQuantity(inventory.getQuantity().add(itemDTO.getQuantity()));
                }
                inventoryRepository.save(inventory);
                
                // Update stock summary - pass correct parameters for purchase/sale
                updateStockSummary(user, inventory, itemDTO.getQuantity(), bill.getIsInvoice());
            }
        }
        
        // Create transaction report
        createTransactionReport(user, savedBill, party);
        
        // Update money report
        createMoneyReport(user, savedBill, party);
        
        // Update daybook report
        updateDayBookReport(user, savedBill);
        
        return savedBill.toBillDTO();
    }
    
    @Transactional
    public void deleteBill(String token, String billId) {
        User user = firebaseAuthUtil.getUserFromToken(token);
        
        Bill bill = billRepository.findByIdAndUser(UUID.fromString(billId), user)
                .orElseThrow(() -> new ResourceNotFoundException("Bill not found with id: " + billId));
        
        // Revert inventory quantities
        for (BillItem item : bill.getItems()) {
            Inventory inventory = item.getInventory();
            
            if (bill.getIsInvoice()) {
                // If this was a sales invoice, add quantity back to inventory
                inventory.setQuantity(inventory.getQuantity().add(item.getQuantity()));
            } else {
                // If this was a purchase invoice, subtract quantity from inventory
                inventory.setQuantity(inventory.getQuantity().subtract(item.getQuantity()));
            }
            
            inventoryRepository.save(inventory);
            
            // Revert stock summary
            updateStockSummary(user, inventory, item.getQuantity(), !bill.getIsInvoice());
        }
        
        // Delete related transaction and money reports
        transactionReportRepository.deleteByBill(bill);
        moneyReportRepository.deleteByBill(bill);
        
        // Update daybook report
        revertDayBookReport(user, bill);
        
        // Delete bill (will cascade to bill items)
        billRepository.delete(bill);
    }
    
    private void updateStockSummary(User user, Inventory inventory, BigDecimal quantity, boolean isSale) {
        // Fetch existing stock summary or create a new one if it doesn't exist
        StockSummary stockSummary = stockSummaryRepository.findByUserAndInventory(user, inventory)
                .orElse(StockSummary.builder()
                        .inventory(inventory)
                        .user(user)
                        .purchased(BigDecimal.ZERO)
                        .sold(BigDecimal.ZERO)
                        .leftStock(inventory.getQuantity())
                        .lastUpdated(LocalDate.now())
                        .build());
        
        if (isSale) {
            // For a sale invoice, increase the sold quantity
            stockSummary.setSold(stockSummary.getSold().add(quantity));
        } else {
            // For a purchase invoice, increase the purchased quantity
            stockSummary.setPurchased(stockSummary.getPurchased().add(quantity));
        }
        
        // Always update the left stock to match the current inventory quantity
        stockSummary.setLeftStock(inventory.getQuantity());
        stockSummary.setLastUpdated(LocalDate.now());
        
        // Save the updated stock summary
        stockSummaryRepository.save(stockSummary);
    }
    
    private void createTransactionReport(User user, Bill bill, Party party) {
        TransactionReport transactionReport = TransactionReport.builder()
                .date(bill.getBillDate())
                .bill(bill)
                .party(party)
                .totalAmount(bill.getTotalAmount())
                .paymentMethod(bill.getPaymentMethod())
                .isInvoice(bill.getIsInvoice())
                .user(user)
                .build();
        
        transactionReportRepository.save(transactionReport);
    }
    
    private void createMoneyReport(User user, Bill bill, Party party) {
        MoneyReport moneyReport = MoneyReport.builder()
                .date(bill.getBillDate())
                .party(party)
                .amount(bill.getTotalAmount())
                .isMoneyIn(bill.getIsInvoice()) // If it's a sales invoice, money comes in
                .mode(ModeOfPayment.valueOf(bill.getPaymentMethod()))
                .user(user)
                .bill(bill)
                .build();
        
        moneyReportRepository.save(moneyReport);
    }
    
    private void updateDayBookReport(User user, Bill bill) {
        // Get or create daybook report for this date
        DayBookReport dayBookReport = dayBookReportRepository.findByUserAndDate(user, bill.getBillDate())
                .orElse(DayBookReport.builder()
                        .date(bill.getBillDate())
                        .user(user)
                        .moneyIn(BigDecimal.ZERO)
                        .moneyInCash(BigDecimal.ZERO)
                        .moneyInCheque(BigDecimal.ZERO)
                        .moneyInUPI(BigDecimal.ZERO)
                        .moneyOut(BigDecimal.ZERO)
                        .moneyOutCash(BigDecimal.ZERO)
                        .moneyOutCheque(BigDecimal.ZERO)
                        .moneyOutUPI(BigDecimal.ZERO)
                        .build());
        
        if (bill.getIsInvoice()) {
            // Sales invoice - money in
            dayBookReport.setMoneyIn(dayBookReport.getMoneyIn().add(bill.getTotalAmount()));
            updatePaymentMethodAmount(dayBookReport, true, bill.getPaymentMethod(), bill.getTotalAmount());
        } else {
            // Purchase invoice - money out
            dayBookReport.setMoneyOut(dayBookReport.getMoneyOut().add(bill.getTotalAmount()));
            updatePaymentMethodAmount(dayBookReport, false, bill.getPaymentMethod(), bill.getTotalAmount());
        }
        
        dayBookReportRepository.save(dayBookReport);
    }
    
    private void revertDayBookReport(User user, Bill bill) {
        DayBookReport dayBookReport = dayBookReportRepository.findByUserAndDate(user, bill.getBillDate())
                .orElseThrow(() -> new ResourceNotFoundException("DayBookReport not found for date: " + bill.getBillDate()));
        
        if (bill.getIsInvoice()) {
            // Sales invoice - decrease money in
            dayBookReport.setMoneyIn(dayBookReport.getMoneyIn().subtract(bill.getTotalAmount()));
            updatePaymentMethodAmount(dayBookReport, true, bill.getPaymentMethod(), bill.getTotalAmount().negate());
        } else {
            // Purchase invoice - decrease money out
            dayBookReport.setMoneyOut(dayBookReport.getMoneyOut().subtract(bill.getTotalAmount()));
            updatePaymentMethodAmount(dayBookReport, false, bill.getPaymentMethod(), bill.getTotalAmount().negate());
        }
        
        dayBookReportRepository.save(dayBookReport);
    }
    
    private void updatePaymentMethodAmount(DayBookReport report, boolean isMoneyIn, String paymentMethod, BigDecimal amount) {
        switch (paymentMethod.toUpperCase()) {
            case "CASH":
                if (isMoneyIn) {
                    report.setMoneyInCash(report.getMoneyInCash().add(amount));
                } else {
                    report.setMoneyOutCash(report.getMoneyOutCash().add(amount));
                }
                break;
            case "CHEQUE":
                if (isMoneyIn) {
                    report.setMoneyInCheque(report.getMoneyInCheque().add(amount));
                } else {
                    report.setMoneyOutCheque(report.getMoneyOutCheque().add(amount));
                }
                break;
            case "UPI":
                if (isMoneyIn) {
                    report.setMoneyInUPI(report.getMoneyInUPI().add(amount));
                } else {
                    report.setMoneyOutUPI(report.getMoneyOutUPI().add(amount));
                }
                break;
            default:
                // For other payment methods, just update the total
                break;
        }
    }
}