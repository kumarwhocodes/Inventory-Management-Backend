package com.minorproject.inventory.dto;

import com.minorproject.inventory.entity.Party;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartyDTO {
    private String id;
    private String name;
    private String phone;
    private String category;
    private String billingAddress;
    private String deliveryAddress;
    private Integer postalCode;
    private String gstNumber;
    private LocalDate dob;
    private Boolean isCustomer;
    
    public Party toParty() {
        return Party.builder()
                .id(id != null ? UUID.fromString(id) : null)
                .name(name)
                .phone(phone)
                .billingAddress(billingAddress)
                .postalCode(postalCode)
                .deliveryAddress(deliveryAddress)
                .gstNumber(gstNumber)
                .dob(dob)
                .isCustomer(isCustomer)
                .build();
    }
}
