package com.minorproject.inventory.entity;

import com.minorproject.inventory.dto.CustomerDTO;
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
@Table(name = "customer_db")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String phoneNumber;
    private String billingAddress;
    private Integer postalCode;
    private String deliveryAddress;
    private String gstNumber;
    private LocalDate dob;
    private Boolean whatsappAlerts;
    private Boolean isCustomer;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    public CustomerDTO toCustomerDTO() {
        return CustomerDTO.builder()
                .id(id != null ? id.toString() : null)
                .name(name)
                .phoneNumber(phoneNumber)
                .billingAddress(billingAddress)
                .postalCode(postalCode)
                .deliveryAddress(deliveryAddress)
                .gstNumber(gstNumber)
                .dob(dob)
                .whatsappAlerts(whatsappAlerts)
                .isCustomer(isCustomer)
                .build();
    }
}
