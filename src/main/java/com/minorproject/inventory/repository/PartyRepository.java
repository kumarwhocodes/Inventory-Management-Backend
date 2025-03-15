package com.minorproject.inventory.repository;

import com.minorproject.inventory.entity.Party;
import com.minorproject.inventory.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PartyRepository extends JpaRepository<Party, UUID> {
    List<Party> findByUser(User user);
    Optional<Party> findByIdAndUser(UUID id, User user);
}