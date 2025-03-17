package com.minorproject.inventory.repository;

import com.minorproject.inventory.entity.User;
import com.minorproject.inventory.entity.bill.Bill;
import com.minorproject.inventory.entity.report.TransactionReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TransactionReportRepository extends JpaRepository<TransactionReport, UUID> {
    void deleteByBill(Bill bill);
    
    List<TransactionReport> findByUserAndDateBetween(User user, LocalDate startDate, LocalDate endDate);
    
    List<TransactionReport> findByUserAndIsInvoice(User user, boolean b);
}
