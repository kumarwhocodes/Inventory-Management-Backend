package com.minorproject.inventory.repository;

import com.minorproject.inventory.entity.BankDetails;
import com.minorproject.inventory.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BankDetailsRepository extends JpaRepository<BankDetails, UUID> {
    Optional<BankDetails> findByUser(User user);
}
