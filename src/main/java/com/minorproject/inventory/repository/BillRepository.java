package com.minorproject.inventory.repository;

import com.minorproject.inventory.entity.Party;
import com.minorproject.inventory.entity.User;
import com.minorproject.inventory.entity.bill.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BillRepository extends JpaRepository<Bill, UUID> {
    List<Bill> findByUser(User user);
    
    List<Bill> findByUserAndParty(User user, Party party);
    
    List<Bill> findByUserAndBillDateBetween(User user, LocalDate startDate, LocalDate endDate);
    
    Optional<Bill> findByIdAndUser(UUID uuid, User user);
}
