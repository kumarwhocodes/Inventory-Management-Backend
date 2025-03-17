package com.minorproject.inventory.repository;

import com.minorproject.inventory.entity.User;
import com.minorproject.inventory.entity.bill.Bill;
import com.minorproject.inventory.entity.report.MoneyReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface MoneyReportRepository extends JpaRepository<MoneyReport, UUID> {
    void deleteByBill(Bill bill);
    
    List<MoneyReport> findByUserAndDateBetween(User user, LocalDate startDate, LocalDate endDate);
    
//    List<MoneyReport> findByUserAndIsMoneyIn(User user, boolean b);
}
