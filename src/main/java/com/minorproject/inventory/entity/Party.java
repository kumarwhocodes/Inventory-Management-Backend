package com.minorproject.inventory.entity;

import com.minorproject.inventory.dto.PartyDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "party_db")
public class Party {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String phone;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    private String billingAddress;
    private String deliveryAddress;
    private Integer postalCode;
    private String gstNumber;
    private LocalDate dob;
    private Boolean isCustomer;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    public PartyDTO toPartyDTO() {
        return PartyDTO.builder()
                .id(id != null ? id.toString() : null)
                .name(name)
                .phone(phone)
                .category(category != null ? category.getCategoryName() : null)
                .billingAddress(billingAddress)
                .postalCode(postalCode)
                .deliveryAddress(deliveryAddress)
                .gstNumber(gstNumber)
                .dob(dob)
                .isCustomer(isCustomer)
                .build();
    }
}
