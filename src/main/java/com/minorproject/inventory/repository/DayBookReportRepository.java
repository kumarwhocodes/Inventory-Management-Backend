package com.minorproject.inventory.repository;

import com.minorproject.inventory.entity.User;
import com.minorproject.inventory.entity.report.DayBookReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DayBookReportRepository extends JpaRepository<DayBookReport, UUID> {
    Optional<DayBookReport> findByUserAndDate(User user, LocalDate billDate);
    
    List<DayBookReport> findByUserAndDateBetween(User user, LocalDate startDate, LocalDate endDate);
}
