package com.minorproject.inventory.repository;

import com.minorproject.inventory.entity.bill.BillItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BillItemRepository extends JpaRepository<BillItem, UUID> {
}
