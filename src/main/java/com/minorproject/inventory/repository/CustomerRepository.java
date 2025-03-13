package com.minorproject.inventory.repository;

import com.minorproject.inventory.entity.Party;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Party, UUID> {
}
